package step;
import model.StepResult;
/**
* INTERFACE: Step
* WHAT IS THIS CLASS?: Pure contract for any executable unit of work.
*/
public interface Step {
String getName();
StepResult execute() throws Exception;
}