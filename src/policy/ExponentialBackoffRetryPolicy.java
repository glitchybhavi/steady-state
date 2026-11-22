package policy;

public class ExponentialBackoffRetryPolicy implements RetryPolicy {
 private final long baseMillis;
 private final long capMillis;

 public ExponentialBackoffRetryPolicy(long baseMillis, long capMillis) {
 this.baseMillis = baseMillis;
 this.capMillis = capMillis;
 }

 @Override
 public boolean shouldRetry(int attemptsMade, int maxRetries) {
 return attemptsMade <= maxRetries;
 }

 @Override
 public long delayMillis(int attemptsMade) {
 long delay = baseMillis;
 for (int i = 1; i < attemptsMade; i++) {
 delay *= 2;
 if (delay >= capMillis) {
 return capMillis;
 }
 }
 return Math.min(delay, capMillis);
 }
}
