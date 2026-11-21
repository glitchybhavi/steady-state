package model;
/**
* CLASS: StepResult
* WHAT IS THIS CLASS?: Immutable outcome object returned by Step.execute().
*/
public final class StepResult {
private final boolean success;
private final String message;
private final long durationMillis;
private StepResult(boolean success, String message, long durationMillis) {
this.success = success;
this.message = message;
this.durationMillis = durationMillis;
}
public static StepResult success(String message, long ms) {
return new StepResult(true, message, ms);
}
public static StepResult failure(String message, long ms) {
return new StepResult(false, message, ms);
}
public boolean isSuccess() {
return success;
}
public String getMessage() {
return message;
}
public long getDurationMillis() {
return durationMillis;
}
}