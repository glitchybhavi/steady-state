package step;

import exception.StepExecutionException;
import java.util.Random;

public class FaultyStep extends AbstractStep {
 private final double failureChance;
 private final Random random = new Random();

 public FaultyStep(String name, double failureChance) {
 super(name);
 this.failureChance = failureChance;
 }

 @Override
 protected String doWork() throws StepExecutionException {
 if (random.nextDouble() < failureChance) {
 throw new StepExecutionException(getName() + " failed randomly");
 }
 return getName() + " succeeded";
 }
}

