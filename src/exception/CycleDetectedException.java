package exception;
/**
* CLASS: CycleDetectedException
* WHAT IS THIS CLASS?: Thrown when Kahn's algorithm finds a circular
dependency.
*/
public class CycleDetectedException extends InvalidJobException {
public CycleDetectedException(String message) {
super(message);
}
}