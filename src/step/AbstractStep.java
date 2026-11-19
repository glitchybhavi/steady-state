package step;

import model.StepResult;

public abstract class AbstractStep implements Step {
 private final String name;

 protected AbstractStep(String name) {
 this.name = name;
 }

 @Override
 public String getName() {
 return name;
 }

 @Override
 public final StepResult execute() throws Exception {
 long start = System.currentTimeMillis();
 String message = doWork();
 return StepResult.success(message, System.currentTimeMillis() - start);
 }

 protected abstract String doWork() throws Exception;
}
