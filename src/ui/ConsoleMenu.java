package ui;

import dao.RunDAO;
import demo.RaceConditionDemo;
import exception.InvalidJobException;
import model.Job;
import model.JobRun;
import report.ReportFormatter;
import service.JobEngine;
import java.util.List;
import java.util.Scanner;

/**
 * CLASS: ConsoleMenu
 * WHAT IS THIS CLASS?: Interactive console UI with input validation loops.
 */
public class ConsoleMenu {
    private final Scanner in = new Scanner(System.in);
    private final JobEngine engine;
    private final RunDAO dao;
    private final ReportFormatter formatter;

    public ConsoleMenu(JobEngine engine, RunDAO dao,
                       ReportFormatter formatter) {
        this.engine = engine;
        this.dao = dao;
        this.formatter = formatter;
    }

    public void start() {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Choose: ");
            try {
                switch (choice) {
                    case 1:
                        runJob(SampleJobs.coldChainPipeline());
                        break;
                    case 2:
                        runJob(SampleJobs.flakyPipeline());
                        break;
                    case 3:
                        runJob(SampleJobs.hangingPipeline());
                        break;
                    case 4:
                        runJob(SampleJobs.cyclicJob());
                        break;
                    case 5:
                        listRuns();
                        break;
                    case 6:
                        showReport();
                        break;
                    case 7:
                        RaceConditionDemo.run();
                        break;
                    case 8:
                        running = false;
                        break;
                    default:
                        System.out.println("Please choose a number from 1 to 8.");
                }
            } catch (InvalidJobException e) {
                System.out.println("Job rejected: " + e.getMessage());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                running = false;
            }
        }
    }

    private void printMenu() {
        System.out.println("\n=== STEADYSTATE ORCHESTRATOR (PHASE 1) ===");
        System.out.println("1. Run Cold-Chain Pipeline (Parallel DAG)");
        System.out.println("2. Run Flaky Pipeline (Retries)");
        System.out.println("3. Run Hanging Pipeline (Timeout & Skip)");
        System.out.println("4. Test Cyclic Job Rejection (Kahn's Check)");
        System.out.println("5. List Past Runs");
        System.out.println("6. View Run Report");
        System.out.println("7. Run Race Condition Demo");
        System.out.println("8. Exit");
    }

    private void runJob(Job job)
            throws InvalidJobException, InterruptedException {
        JobRun run = engine.execute(job);
        System.out.println(formatter.format(run));
    }

    private void listRuns() {
        List<JobRun> all = dao.findAll();
        if (all.isEmpty()) {
            System.out.println("No runs recorded yet.");
            return;
        }
        for (JobRun r : all) {
            System.out.println(r.getRunId() + " | "
                    + r.getJob().getName() + " | " + r.getStatus());
        }
    }

    private void showReport() {
        System.out.print("Enter Run ID (e.g., RUN-1): ");
        String id = in.nextLine().trim();
        JobRun run = dao.findById(id);
        if (run == null) {
            System.out.println("Run not found: " + id);
        } else {
            System.out.println(formatter.format(run));
        }
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(in.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please type a number.");
            }
        }
    }
}
