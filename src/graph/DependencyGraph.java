package graph;
import model.Job;
import model.StepDefinition;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
/**
* CLASS: DependencyGraph
* WHAT IS THIS CLASS?: Stores forward/reverse DAG edges and finds downstream
steps.
*/
public class DependencyGraph {
private final Map<String, List<String>> dependencies = new
LinkedHashMap<>();
private final Map<String, List<String>> dependents = new LinkedHashMap<>();
public DependencyGraph(Job job) {
for (StepDefinition d : job.getSteps()) {
dependencies.put(d.getId(), new ArrayList<>(d.getDependsOn()));
dependents.put(d.getId(), new ArrayList<>());
}
for (StepDefinition d : job.getSteps()) {
for (String prerequisite : d.getDependsOn()) {
List<String> list = dependents.get(prerequisite);
if (list != null) {
list.add(d.getId());
}
}
}
}
public List<String> dependenciesOf(String id) {
return dependencies.get(id);
}
public List<String> dependentsOf(String id) {
return dependents.get(id);
}
public Set<String> allSteps() {
return dependencies.keySet();
}
public int size() {
return dependencies.size();
}
public Set<String> transitiveDependentsOf(String id) {
Set<String> found = new LinkedHashSet<>();
Queue<String> toVisit = new LinkedList<>();
toVisit.add(id);
while (!toVisit.isEmpty()) {
String current = toVisit.poll();
for (String next : dependentsOf(current)) {
if (found.add(next)) {
toVisit.add(next);
}
}
}
return found;
}
}