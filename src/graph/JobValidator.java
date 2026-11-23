package graph;
import exception.InvalidJobException;
import model.Job;
import model.StepDefinition;
/**
* CLASS: JobValidator
* WHAT IS THIS CLASS?: Rejects empty jobs, self-loops, missing steps, and
cycles.
*/
public class JobValidator {
public DependencyGraph validate(Job job) throws InvalidJobException {
if (job.size() == 0) {
throw new InvalidJobException("job has no steps");
}
for (StepDefinition d : job.getSteps()) {
for (String dep : d.getDependsOn()) {
if (dep.equals(d.getId())) {
throw new InvalidJobException(d.getId() + " depends on itself");
}
if (job.getStep(dep) == null) {
throw new InvalidJobException(
d.getId() + " depends on missing step " + dep);
}
}
}
DependencyGraph graph = new DependencyGraph(job);
new CycleDetector().topologicalOrder(graph);
return graph;
}
}