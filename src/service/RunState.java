package service;

import graph.DependencyGraph;
import model.JobRun;
import model.RunStatus;
import model.StepRun;
import model.StepStatus;
import policy.FailurePolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RunState {
    private final JobRun run;
    private final DependencyGraph graph;
    private final Map<String, Integer> unmetCount = new HashMap<>();
    private int terminalCount = 0;

    public RunState(JobRun run, DependencyGraph graph) {
        this.run = run;
        this.graph = graph;
        for (String id : graph.allSteps()) {
            unmetCount.put(id, graph.dependenciesOf(id).size());
        }
    }

    public synchronized List<String> initialReadySteps() {
        List<String> ready = new ArrayList<>();
        for (String id : graph.allSteps()) {
            if (unmetCount.get(id) == 0) {
                ready.add(id);
            }
        }
        return ready;
    }

    public synchronized boolean tryMarkRunning(String id) {
        StepRun stepRun = run.getStepRun(id);
        if (stepRun.getStatus() != StepStatus.PENDING) {
            return false;
        }
        stepRun.markStarted();
        return true;
    }

    public synchronized List<String> markSucceeded(String id) {
        run.getStepRun(id).markSucceeded();
        terminalCount++;

        List<String> nowReady = new ArrayList<>();
        for (String dependent : graph.dependentsOf(id)) {
            int left = unmetCount.get(dependent) - 1;
            unmetCount.put(dependent, left);
            if (left == 0
                    && run.getStepRun(dependent).getStatus() == StepStatus.PENDING) {
                nowReady.add(dependent);
            }
        }

        if (terminalCount == graph.size()) {
            notifyAll();
        }
        return nowReady;
    }

    public synchronized int attemptsOf(String id) {
        return run.getStepRun(id).getAttempts();
    }

    public synchronized void markRetryPending(String id, String error) {
        run.getStepRun(id).markRetryPending(error);
    }

    public synchronized List<String> markFailed(
            String id, String error, FailurePolicy policy) {
        run.getStepRun(id).markFailed(error);
        terminalCount++;

        List<String> skipped = new ArrayList<>();
        for (String other : policy.chooseStepsToSkip(id, graph)) {
            StepRun stepRun = run.getStepRun(other);
            if (stepRun.getStatus() == StepStatus.PENDING) {
                stepRun.markSkipped();
                terminalCount++;
                skipped.add(other);
            }
        }

        if (terminalCount == graph.size()) {
            notifyAll();
        }
        return skipped;
    }

    public synchronized void awaitFinished() throws InterruptedException {
        while (terminalCount < graph.size()) {
            wait();
        }
    }

    public synchronized RunStatus computeFinalStatus() {
        int succeeded = 0;
        for (StepRun stepRun : run.getStepRuns()) {
            if (stepRun.getStatus() == StepStatus.SUCCEEDED) {
                succeeded++;
            }
        }
        if (succeeded == graph.size()) {
            return RunStatus.SUCCEEDED;
        }
        if (succeeded == 0) {
            return RunStatus.FAILED;
        }
        return RunStatus.PARTIALLY_FAILED;
    }
}

