package policy;

import graph.DependencyGraph;
import java.util.ArrayList;
import java.util.List;

public class SkipDependentsPolicy implements FailurePolicy {
 @Override
 public String getName() {
 return "skip-dependents";
 }

 @Override
 public List<String> chooseStepsToSkip(String failedStepId, DependencyGraph graph) {
 return new ArrayList<>(graph.transitiveDependentsOf(failedStepId));
 }
}
