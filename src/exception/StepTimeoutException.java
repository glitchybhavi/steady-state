package exception;
/**
CLASS: StepTimeoutException
WHAT IS THIS CLASS?: Thrown when a step runs longer than its timeout limit.
*/
public class StepTimeoutException extends SteadyStateException {
public StepTimeoutException(String message) {
super(message);
}
}