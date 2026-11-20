package exception;
/**
* CLASS: StepExecutionException
* WHAT IS THIS CLASS?: Thrown when a step fails while executing its work.
*/
public class StepExecutionException extends SteadyStateException {
public StepExecutionException(String message) {
super(message);
}
}