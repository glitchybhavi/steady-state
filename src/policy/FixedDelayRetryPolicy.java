package policy;

public class FixedDelayRetryPolicy implements RetryPolicy {
 private final long delay;

 public FixedDelayRetryPolicy(long delayMillis) {
 this.delay = delayMillis;
 }

 @Override
 public boolean shouldRetry(int attemptsMade, int maxRetries) {
 return attemptsMade <= maxRetries;
 }

 @Override
 public long delayMillis(int attemptsMade) {
 return delay;
 }
}
