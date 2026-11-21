package model;
import exception.InvalidJobException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
/**
* CLASS: Job
* WHAT IS THIS CLASS?: Named collection of StepDefinitions preserving
insertion order.
*/
public class Job {
private final String name;
private final Map<String, StepDefinition> steps = new LinkedHashMap<>();
public Job(String name) {
this.name = name;
}
public void addStep(StepDefinition step) throws InvalidJobException {
if (steps.containsKey(step.getId())) {
throw new InvalidJobException("duplicate step id: " +
step.getId());
}
steps.put(step.getId(), step);
}
public StepDefinition getStep(String id) {
return steps.get(id);
}
public Collection<StepDefinition> getSteps() {
return steps.values();
}
public int size() {
return steps.size();
}
public String getName() {
return name;
}
}