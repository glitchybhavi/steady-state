package policy;
import graph.DependencyGraph;
import java.util.List;
/**
* INTERFACE: FailurePolicy
* WHAT IS THIS CLASS?: Chooses which PENDING steps become SKIPPED on failure.
*/
public interface FailurePolicy {
String getName();
List<String> chooseStepsToSkip(String failedStepId, DependencyGraph graph);
}