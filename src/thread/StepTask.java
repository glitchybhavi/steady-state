package thread;

import exception.StepTimeoutException;
import model.EventType;
import model.StepDefinition;
import model.StepResult;
import service.JobEngine;
import java.util.concurrent.ScheduledFuture;

public class StepTask implements Runnable {
    private final JobEngine engine;
    private final StepDefinition def;
    private volatile boolean timedOut = false;

    public StepTask(JobEngine engine, StepDefinition def) {
        this.engine = engine;
        this.def = def;
    }

    public void markTimedOut() {
        timedOut = true;
    }

    @Override
    public void run() {
        engine.fire(EventType.STEP_STARTED, def.getId(),
                "running on " + Thread.currentThread().getName());
        ScheduledFuture<?> watchdog = engine.scheduleWatchdog(
                new TimeoutWatchdog(this, Thread.currentThread()),
                def.getTimeoutSeconds());

        StepResult result = null;
        Exception error = null;
        try {
            result = def.getTask().execute();
        } catch (Exception e) {
            error = e;
        } finally {
            watchdog.cancel(false);
        }

        if (timedOut) {
            result = null;
            error = new StepTimeoutException(
                    def.getId() + " timed out after " + def.getTimeoutSeconds() + "s");
            Thread.interrupted();
        }

        engine.onAttemptFinished(def, result, error);
    }
}

