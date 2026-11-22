package report;

import model.JobRun;
import model.StepRun;

/**
 * CLASS: RunSummary
 * WHAT IS THIS CLASS?: Counts succeeded, failed, skipped steps and total attempts.
 */
public class RunSummary {
    private final int total;
    private final int succeeded;
    private final int failed;
    private final int skipped;
    private final int totalAttempts;
    private final long durationMillis;

    private RunSummary(int total, int succeeded, int failed,
                       int skipped, int attempts, long durationMillis) {
        this.total = total;
        this.succeeded = succeeded;
        this.failed = failed;
        this.skipped = skipped;
        this.totalAttempts = attempts;
        this.durationMillis = durationMillis;
    }

    public static RunSummary of(JobRun run) {
        int ok = 0;
        int bad = 0;
        int skip = 0;
        int attempts = 0;
        for (StepRun stepRun : run.getStepRuns()) {
            attempts += stepRun.getAttempts();
            switch (stepRun.getStatus()) {
                case SUCCEEDED:
                    ok++;
                    break;
                case FAILED:
                    bad++;
                    break;
                case SKIPPED:
                    skip++;
                    break;
                default:
                    break;
            }
        }
        return new RunSummary(run.getStepRuns().size(), ok, bad, skip,
                attempts, run.getEndedAt() - run.getStartedAt());
    }

    public int getTotal() {
        return total;
    }

    public int getSucceeded() {
        return succeeded;
    }

    public int getFailed() {
        return failed;
    }

    public int getSkipped() {
        return skipped;
    }

    public int getTotalAttempts() {
        return totalAttempts;
    }

    public long getDurationMillis() {
        return durationMillis;
    }
}
