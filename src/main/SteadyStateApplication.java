package main;

import dao.InMemoryRunDAO;
import dao.RunDAO;
import listener.ConsoleRunListener;
import policy.AlphabeticalStrategy;
import policy.FixedDelayRetryPolicy;
import policy.SkipDependentsPolicy;
import report.PlainTextReportFormatter;
import service.JobEngine;
import ui.ConsoleMenu;

/**
 * CLASS: SteadyStateApplication
 * WHAT IS THIS CLASS?: Wires concrete storage, policies, and formatter into JobEngine.
 */
public class SteadyStateApplication {
    public static void main(String[] args) {
        RunDAO dao = new InMemoryRunDAO();
        JobEngine engine = new JobEngine(
                3,
                new FixedDelayRetryPolicy(500L),
                new SkipDependentsPolicy(),
                new AlphabeticalStrategy(),
                dao);
        engine.addListener(new ConsoleRunListener());
        new ConsoleMenu(engine, dao, new PlainTextReportFormatter()).start();
    }
}
