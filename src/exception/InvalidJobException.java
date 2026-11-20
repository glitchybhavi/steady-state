package exception;
/**
* CLASS: InvalidJobException
* WHAT IS THIS CLASS?: Thrown when a job is empty, duplicate, or missing
steps.
*/
public class InvalidJobException extends SteadyStateException {
public InvalidJobException(String message) {
super(message);
}
}