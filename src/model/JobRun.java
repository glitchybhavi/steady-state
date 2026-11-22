package model;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
/**
* CLASS: JobRun
* WHAT IS THIS CLASS?: Holds the run id, job reference, status, and all
StepRuns.
*/
public class JobRun {
private final String runId;
private final Job job;
private RunStatus status = RunStatus.CREATED;
private final Map<String, StepRun> stepRuns = new LinkedHashMap<>();
private long startedAt = 0L;
private long endedAt = 0L;
public JobRun(String runId, Job job) {
this.runId = runId;
this.job = job;
for (StepDefinition d : job.getSteps()) {
stepRuns.put(d.getId(), new StepRun(d.getId()));
}
}
public void markStarted() {
status = RunStatus.RUNNING;
startedAt = System.currentTimeMillis();
}
public void finish(RunStatus finalStatus) {
status = finalStatus;
endedAt = System.currentTimeMillis();
}
public StepRun getStepRun(String id) {
return stepRuns.get(id);
}
public Collection<StepRun> getStepRuns() {
return stepRuns.values();
}
public String getRunId() {
return runId;
}
public Job getJob() {
return job;
}
public RunStatus getStatus() {
return status;
}
public long getStartedAt() {
return startedAt;
}
public long getEndedAt() {
return endedAt;
}
}