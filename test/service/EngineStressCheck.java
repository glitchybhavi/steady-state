package service;

import dao.InMemoryRunDAO;
import model.JobRun;
import model.RunStatus;
import policy.AlphabeticalStrategy;
import policy.FixedDelayRetryPolicy;
import policy.SkipDependentsPolicy;
import ui.SampleJobs;

public class EngineStressCheck {
    public static void main(String[] args) throws Exception {
        JobEngine engine = new JobEngine(
                3,
                new FixedDelayRetryPolicy(200L),
                new SkipDependentsPolicy(),
                new AlphabeticalStrategy(),
                new InMemoryRunDAO());

        for (int i = 1; i <= 20; i++) {
            JobRun run = engine.execute(SampleJobs.coldChainPipeline());
            if (run.getStatus() != RunStatus.SUCCEEDED) {
                throw new AssertionError("Run " + i + " failed: " + run.getStatus());
            }
        }
        System.out.println("All 20 parallel runs succeeded cleanly.");
    }
}

