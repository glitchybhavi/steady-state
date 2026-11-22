package policy;

import graph.DependencyGraph;
import java.util.ArrayList;
import java.util.List;

public class FailFastPolicy implements FailurePolicy {
 @Override
 public String getName() {
 return "fail-fast";
 }

 @Override
 public List<String> chooseStepsToSkip(String failedStepId, DependencyGraph graph) {
 List<String> everyone = new ArrayList<>(graph.allSteps());
 everyone.remove(failedStepId);
 return everyone;
 }
}
