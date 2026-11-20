package step;

public class PrintStep extends AbstractStep {
 private final long workMillis;

 public PrintStep(String name, long workMillis) {
 super(name);
 this.workMillis = workMillis;
 }

 @Override
 protected String doWork() throws InterruptedException {
 Thread.sleep(workMillis);
 return getName() + " finished in " + workMillis + " ms";
 }
}
