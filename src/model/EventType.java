package model;
/**
* ENUM: EventType
* WHAT IS THIS CLASS?: Categorizes lifecycle events emitted during a job run.
*/
public enum EventType {
RUN_STARTED,
STEP_STARTED,
STEP_SUCCEEDED,
STEP_RETRY_SCHEDULED,
STEP_FAILED,
STEP_SKIPPED,
RUN_FINISHED
}