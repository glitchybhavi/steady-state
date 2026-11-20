package step;

public class ComputeStep extends AbstractStep {
 private final int n;

 public ComputeStep(String name, int n) {
 super(name);
 this.n = n;
 }

 @Override
 protected String doWork() {
 long factorial = 1L;
 for (int i = 2; i <= n; i++) {
 factorial *= i;
 }
 return "factorial(" + n + ") = " + factorial;
 }
}
