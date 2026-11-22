package model;
/**
* CLASS: StepRun
* WHAT IS THIS CLASS?: Live execution record of one step in one JobRun.
*/
public class StepRun {
private final String stepId;
private StepStatus status = StepStatus.PENDING;
private int attempts = 0;
private long startedAt = 0L;
private long endedAt = 0L;
private String lastError = null;
public StepRun(String stepId) {
this.stepId = stepId;
}
private void moveTo(StepStatus next) {
if (!status.canMoveTo(next)) {
throw new IllegalStateException(
stepId + ": " + status + " -> " + next + " is not allowed");
}
status = next;
}
public void markStarted() {
moveTo(StepStatus.RUNNING);
attempts++;
if (startedAt == 0L) {
startedAt = System.currentTimeMillis();
}
}
public void markSucceeded() {
moveTo(StepStatus.SUCCEEDED);
endedAt = System.currentTimeMillis();
}
public void markFailed(String error) {
moveTo(StepStatus.FAILED);
lastError = error;
endedAt = System.currentTimeMillis();
}
public void markRetryPending(String error) {
moveTo(StepStatus.PENDING);
lastError = error;
}
public void markSkipped() {
moveTo(StepStatus.SKIPPED);
endedAt = System.currentTimeMillis();
}
public String getStepId() {
return stepId;
}
public StepStatus getStatus() {
return status;
}
public int getAttempts() {
return attempts;
}
public long getStartedAt() {
return startedAt;
}
public long getEndedAt() {
return endedAt;
}
public String getLastError() {
return lastError;
}
}