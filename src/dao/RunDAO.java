package dao;
import model.JobRun;
import java.util.List;
/**
* INTERFACE: RunDAO
* WHAT IS THIS CLASS?: Abstracts JobRun storage so Phase 2 can swap in MySQL.
*/
public interface RunDAO {
void save(JobRun run);
JobRun findById(String runId);
List<JobRun> findAll();
}