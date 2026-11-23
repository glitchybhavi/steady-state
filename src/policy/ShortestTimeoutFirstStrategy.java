package policy;

import model.StepDefinition;

public class ShortestTimeoutFirstStrategy implements SchedulingStrategy {
 @Override
 public int compare(StepDefinition a, StepDefinition b) {
 int byTimeout = Integer.compare(
 a.getTimeoutSeconds(), b.getTimeoutSeconds());
 return byTimeout != 0 ? byTimeout : a.getId().compareTo(b.getId());
 }

 @Override
 public String getName() {
 return "shortest-timeout-first";
 }
}
