package step;

public class SlowStep extends AbstractStep {
 private final long sleepMillis;

 public SlowStep(String name, long sleepMillis) {
 super(name);
 this.sleepMillis = sleepMillis;
 }

 @Override
 protected String doWork() throws InterruptedException {
 Thread.sleep(sleepMillis);
 return getName() + " finally finished";
 }
}
