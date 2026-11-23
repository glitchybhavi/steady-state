package ui;

import exception.InvalidJobException;
import model.Job;
import model.StepDefinition;
import step.ComputeStep;
import step.FaultyStep;
import step.PrintStep;
import step.SlowStep;
import step.Step;
import java.util.Arrays;

/**
 * CLASS: SampleJobs
 * WHAT IS THIS CLASS?: Builds cold-chain, flaky, hanging, and cyclic sample jobs.
 */
public class SampleJobs {

    private static StepDefinition step(String id, Step task, int retries,
                                       int timeoutSeconds, String... deps) {
        return new StepDefinition(
                id, task, Arrays.asList(deps), retries, timeoutSeconds);
    }

    public static Job coldChainPipeline() throws InvalidJobException {
        Job job = new Job("cold-chain-pipeline");
        job.addStep(step("generate_readings",
                new PrintStep("generate_readings", 800), 2, 10));
        job.addStep(step("clean_data",
                new PrintStep("clean_data", 1200), 2, 10, "generate_readings"));
        job.addStep(step("compute_stats",
                new ComputeStep("compute_stats", 15), 2, 10, "generate_readings"));
        job.addStep(step("detect_anomalies",
                new PrintStep("detect_anomalies", 900), 3, 15,
                "clean_data", "compute_stats"));
        job.addStep(step("write_report",
                new PrintStep("write_report", 500), 1, 10, "detect_anomalies"));
        return job;
    }

    public static Job flakyPipeline() throws InvalidJobException {
        Job job = new Job("flaky-pipeline");
        job.addStep(step("fetch", new FaultyStep("fetch", 0.6), 3, 5));
        job.addStep(step("transform",
                new FaultyStep("transform", 0.4), 3, 5, "fetch"));
        job.addStep(step("publish",
                new PrintStep("publish", 300), 1, 5, "transform"));
        return job;
    }

    public static Job hangingPipeline() throws InvalidJobException {
        Job job = new Job("hanging-pipeline");
        job.addStep(step("prepare", new PrintStep("prepare", 300), 0, 5));
        job.addStep(step("slow_export",
                new SlowStep("slow_export", 6000), 1, 2, "prepare"));
        job.addStep(step("notify",
                new PrintStep("notify", 200), 0, 5, "slow_export"));
        return job;
    }

    public static Job cyclicJob() throws InvalidJobException {
        Job job = new Job("cyclic-job");
        job.addStep(step("a", new PrintStep("a", 100), 0, 5, "b"));
        job.addStep(step("b", new PrintStep("b", 100), 0, 5, "a"));
        return job;
    }
}
