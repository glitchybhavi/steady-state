package model;
/**
* CLASS: RunEvent
* WHAT IS THIS CLASS?: Thread-safe immutable record of one execution event.
*/
public final class RunEvent {
private final long timeMillis = System.currentTimeMillis();
private final EventType type;
private final String stepId;
private final String message;
public RunEvent(EventType type, String stepId, String message) {
this.type = type;
this.stepId = stepId;
this.message = message;
}
public long getTimeMillis() {
return timeMillis;
}
public EventType getType() {
return type;
}
public String getStepId() {
return stepId;
}
public String getMessage() {
return message;
}
}