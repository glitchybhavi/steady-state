package report;
import model.JobRun;
/**
* INTERFACE: ReportFormatter
* WHAT IS THIS CLASS?: Formats a completed JobRun into a readable text report.
*/
public interface ReportFormatter {
String format(JobRun run);
}