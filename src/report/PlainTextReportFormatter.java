package report;

import model.JobRun;
import model.StepRun;

/**
 * CLASS: PlainTextReportFormatter
 * WHAT IS THIS CLASS?: Formats a JobRun into an aligned console table.
 */
public class PlainTextReportFormatter implements ReportFormatter {
    @Override
    public String format(JobRun run) {
        RunSummary summary = RunSummary.of(run);
        StringBuilder sb = new StringBuilder();
        sb.append("==== REPORT: ").append(run.getJob().getName())
                .append(" (").append(run.getRunId()).append(") ====")
                .append(System.lineSeparator());
        sb.append(String.format("%-20s %-10s %-9s %s%n",
                "STEP", "STATUS", "ATTEMPTS", "LAST ERROR"));
        for (StepRun stepRun : run.getStepRuns()) {
            String error = (stepRun.getLastError() == null)
                    ? "-" : stepRun.getLastError();
            sb.append(String.format("%-20s %-10s %-9d %s%n",
                    stepRun.getStepId(), stepRun.getStatus(),
                    stepRun.getAttempts(), error));
        }
        sb.append("Result: ").append(run.getStatus())
                .append(" | succeeded ").append(summary.getSucceeded())
                .append(", failed ").append(summary.getFailed())
                .append(", skipped ").append(summary.getSkipped())
                .append(" | attempts ").append(summary.getTotalAttempts())
                .append(" | ").append(summary.getDurationMillis()).append(" ms");
        return sb.toString();
    }
}
