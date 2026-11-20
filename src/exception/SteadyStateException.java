package exception;
/**
CLASS: SteadyStateException
WHAT IS THIS CLASS?: Root checked exception for all SteadyState errors.
*/
public class SteadyStateException extends Exception {
public SteadyStateException(String message) {
super(message);
}
}