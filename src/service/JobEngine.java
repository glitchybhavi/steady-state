package service;

import dao.RunDAO;
import exception.InvalidJobException;
import graph.DependencyGraph;
import graph.JobValidator;
import listener.RunListener;
import model.EventType;
import model.Job;
import model.JobRun;
import model.RunEvent;
import model.StepDefinition;
import model.StepResult;
import policy.FailurePolicy;
import policy.RetryPolicy;
import policy.SchedulingStrategy;
import thread.Dispatcher;
import thread.RetryTask;
import thread.StepTask;
import thread.TimeoutWatchdog;
import util.IdGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class JobEngine {
    private final int workerCount;
    private final RetryPolicy retryPolicy;
    private final FailurePolicy failurePolicy;
    private final SchedulingStrategy strategy;
    private final RunDAO dao;
    private final List<RunListener> listeners = new ArrayList<>();

    private Job job;
    private RunState state;
    private ReadyQueue readyQueue;
    private ExecutorService workers;
    private ScheduledExecutorService timer;

    public JobEngine(int workerCount, RetryPolicy retryPolicy,
                     FailurePolicy failurePolicy,
                     SchedulingStrategy strategy, RunDAO dao) {
        this.workerCount = workerCount;
        this.retryPolicy = retryPolicy;
        this.failurePolicy = failurePolicy;
        this.strategy = strategy;
        this.dao = dao;
    }

    public void addListener(RunListener listener) {
        listeners.add(listener);
    }

    public JobRun execute(Job jobToRun)
            throws InvalidJobException, InterruptedException {
        DependencyGraph graph = new JobValidator().validate(jobToRun);
        this.job = jobToRun;

        JobRun run = new JobRun(IdGenerator.nextRunId(), jobToRun);
        state = new RunState(run, graph);
        readyQueue = new ReadyQueue(strategy);
        workers = Executors.newFixedThreadPool(workerCount);
        timer = Executors.newScheduledThreadPool(1);

        run.markStarted();
        fire(EventType.RUN_STARTED, "-",
                run.getRunId() + " started, policy: " + failurePolicy.getName());

        Dispatcher dispatcher = new Dispatcher(this, readyQueue);
        dispatcher.start();

        for (String id : state.initialReadySteps()) {
            readyQueue.put(job.getStep(id));
        }

        state.awaitFinished();
        readyQueue.close();
        dispatcher.join();

        workers.shutdown();
        workers.awaitTermination(10, TimeUnit.SECONDS);
        timer.shutdownNow();

        run.finish(state.computeFinalStatus());
        dao.save(run);
        fire(EventType.RUN_FINISHED, "-",
                run.getRunId() + " ended as " + run.getStatus());
        return run;
    }

    public void startStep(StepDefinition def) {
        if (state.tryMarkRunning(def.getId())) {
            workers.submit(new StepTask(this, def));
        }
    }

    public ScheduledFuture<?> scheduleWatchdog(
            TimeoutWatchdog watchdog, int seconds) {
        return timer.schedule(watchdog, seconds, TimeUnit.SECONDS);
    }

    public void onAttemptFinished(
            StepDefinition def, StepResult result, Exception error) {
        String id = def.getId();
        if (error == null && result.isSuccess()) {
            List<String> nowReady = state.markSucceeded(id);
            fire(EventType.STEP_SUCCEEDED, id, result.getMessage());
            for (String next : nowReady) {
                readyQueue.put(job.getStep(next));
            }
            return;
        }

        String reason = (error != null) ? error.getMessage() : result.getMessage();
        int attemptsMade = state.attemptsOf(id);
        if (retryPolicy.shouldRetry(attemptsMade, def.getMaxRetries())) {
            long delay = retryPolicy.delayMillis(attemptsMade);
            state.markRetryPending(id, reason);
            fire(EventType.STEP_RETRY_SCHEDULED, id,
                    "attempt " + attemptsMade + " failed (" + reason
                            + "), retry in " + delay + " ms");
            timer.schedule(new RetryTask(readyQueue, def),
                    delay, TimeUnit.MILLISECONDS);
        } else {
            List<String> skipped = state.markFailed(id, reason, failurePolicy);
            fire(EventType.STEP_FAILED, id,
                    reason + " after " + attemptsMade + " attempt(s)");
            for (String s : skipped) {
                fire(EventType.STEP_SKIPPED, s,
                        "skipped because " + id + " failed");
            }
        }
    }

    public void fire(EventType type, String stepId, String message) {
        RunEvent event = new RunEvent(type, stepId, message);
        for (RunListener listener : listeners) {
            listener.onEvent(event);
        }
    }
}

