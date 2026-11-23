package graph;
import exception.CycleDetectedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
/**
* CLASS: CycleDetector
* WHAT IS THIS CLASS?: Rejects circular dependencies when steps never reach 0
in-degree.
*/
public class CycleDetector {
public List<String> topologicalOrder(DependencyGraph graph)
throws CycleDetectedException {
Map<String, Integer> waitingFor = new HashMap<>();
Queue<String> free = new LinkedList<>();
for (String id : graph.allSteps()) {
int count = graph.dependenciesOf(id).size();
waitingFor.put(id, count);
if (count == 0) {
free.add(id);
}
}
List<String> order = new ArrayList<>();
while (!free.isEmpty()) {
String current = free.poll();
order.add(current);
for (String next : graph.dependentsOf(current)) {
int left = waitingFor.get(next) - 1;
waitingFor.put(next, left);
if (left == 0) {
free.add(next);
}
}
}
if (order.size() != graph.size()) {
List<String> stuck = new ArrayList<>(graph.allSteps());
stuck.removeAll(order);
throw new CycleDetectedException("circular dependency among: " +
stuck);
}
return order;
}
}