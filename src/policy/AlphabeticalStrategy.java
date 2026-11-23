package policy;

import model.StepDefinition;

public class AlphabeticalStrategy implements SchedulingStrategy {
 @Override
 public int compare(StepDefinition a, StepDefinition b) {
 return a.getId().compareTo(b.getId());
 }

 @Override
 public String getName() {
 return "alphabetical";
 }
}
