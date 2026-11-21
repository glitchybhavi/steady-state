package model;

import step.Step;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * CLASS: StepDefinition
 * WHAT IS THIS CLASS?: Blueprint for one step: id, task, dependencies, retries, timeout.
 */
public class StepDefinition {
    private final String id;
    private final Step task;
    private final List<String> dependsOn;
    private final int maxRetries;
    private final int timeoutSeconds;

    public StepDefinition(String id, Step task, List<String> dependsOn,
                          int maxRetries, int timeoutSeconds) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("step id is empty");
        }
        if (task == null) {
            throw new IllegalArgumentException("task is null");
        }
        if (maxRetries < 0) {
            throw new IllegalArgumentException("maxRetries < 0");
        }
        if (timeoutSeconds <= 0) {
            throw new IllegalArgumentException("timeout must be positive");
        }
        this.id = id.trim();
        this.task = task;
        this.dependsOn = new ArrayList<>(dependsOn);
        this.maxRetries = maxRetries;
        this.timeoutSeconds = timeoutSeconds;
    }

    public String getId() {
        return id;
    }

    public Step getTask() {
        return task;
    }

    public List<String> getDependsOn() {
        return Collections.unmodifiableList(dependsOn);
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    public int getTimeoutSeconds() {
        return timeoutSeconds;
    }
}