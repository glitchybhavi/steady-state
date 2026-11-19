package policy;
/**
* INTERFACE: RetryPolicy
* WHAT IS THIS CLASS?: Decides if a failed step retries and computes wait
delay.
*/
public interface RetryPolicy {
boolean shouldRetry(int attemptsMade, int maxRetries);
long delayMillis(int attemptsMade);
}