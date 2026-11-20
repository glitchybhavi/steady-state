package dao;

import model.JobRun;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * CLASS: InMemoryRunDAO
 * WHAT IS THIS CLASS?: Phase 1 in-memory implementation of RunDAO.
 */
public class InMemoryRunDAO implements RunDAO {
    private final Map<String, JobRun> runs = new LinkedHashMap<>();

    @Override
    public synchronized void save(JobRun run) {
        runs.put(run.getRunId(), run);
    }

    @Override
    public synchronized JobRun findById(String runId) {
        return runs.get(runId);
    }

    @Override
    public synchronized List<JobRun> findAll() {
        return new ArrayList<>(runs.values());
    }
}
