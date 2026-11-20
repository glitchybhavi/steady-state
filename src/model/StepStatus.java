package model;
/**
* ENUM: StepStatus
* WHAT IS THIS CLASS?: Defines allowed step states and blocks illegal moves.
*/
public enum StepStatus {
PENDING,
RUNNING,
SUCCEEDED,
FAILED,
SKIPPED;
public boolean isTerminal() {
return this == SUCCEEDED || this == FAILED || this == SKIPPED;
}
public boolean canMoveTo(StepStatus next) {
switch (this) {
case PENDING:
return next == RUNNING || next == SKIPPED;
case RUNNING:
return next == SUCCEEDED || next == FAILED || next == PENDING;
default:
return false;
}
}
}