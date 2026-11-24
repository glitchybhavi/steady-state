/**  
Execute these commands in terminal inside root folder to run this test:  

Windows Powershell:

javac -d out (Get-ChildItem src, test -Recurse -Filter *.java).FullName
java -cp out service.EngineStressCheck

Git Bash/Mac/Linux

javac -d out $(find src test -name "*.java")
java -cp out service.EngineStressCheck
*/
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

        boolean logging = true;
        
        int n = 20;
        if (logging) {
        engine.addListener(new listener.ConsoleRunListener());
        }

        for (int i = 1; i <= n; i++) {
            if (i == 1) {
                System.out.println("Starting " +  n + " parallel runs:");
            }
            System.out.println("Running " + i);
            
            JobRun run = engine.execute(SampleJobs.coldChainPipeline());
            if (run.getStatus() != RunStatus.SUCCEEDED) {
                throw new AssertionError("Run " + i + " failed: " + run.getStatus());
            }


            if (run.getStatus() == RunStatus.SUCCEEDED){
                System.out.println("Success!");
            }
        }
        System.out.println("All 20 parallel runs succeeded cleanly.");
    }
}

