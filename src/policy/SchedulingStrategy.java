package policy;
import model.StepDefinition;
import java.util.Comparator;
/**
* INTERFACE: SchedulingStrategy
* WHAT IS THIS CLASS?: Orders ready steps inside ReadyQueue's PriorityQueue.
*/
public interface SchedulingStrategy extends Comparator<StepDefinition> {
String getName();
}