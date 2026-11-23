package policy;

import graph.DependencyGraph;
import model.Job;
import model.StepDefinition;
import step.PrintStep;
import java.util.Collections;
import java.util.List;

public class PolicyCheck {
 public static void main(String[] args) throws Exception {
 RetryPolicy backoff = new ExponentialBackoffRetryPolicy(500L, 4000L);
 for (int attempt = 1; attempt <= 5; attempt++) {
 System.out.println("Attempt " + attempt + " delay: "
 + backoff.delayMillis(attempt) + " ms");
 }

 Job job = new Job("policy-test");
 job.addStep(new StepDefinition("A", new PrintStep("A", 100),
 Collections.emptyList(), 0, 5));
 job.addStep(new StepDefinition("B", new PrintStep("B", 100),
 Collections.singletonList("A"), 0, 5));
 job.addStep(new StepDefinition("C", new PrintStep("C", 100),
 Collections.singletonList("B"), 0, 5));

 DependencyGraph graph = new DependencyGraph(job);
 List<String> skipped = new SkipDependentsPolicy()
 .chooseStepsToSkip("A", graph);
 System.out.println("Skipped after A fails: " + skipped);
 }
}
