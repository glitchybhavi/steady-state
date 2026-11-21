package listener;

import model.RunEvent;

/**
 * CLASS: ConsoleRunListener
 * WHAT IS THIS CLASS?: Prints timestamped run events cleanly across worker threads.
 */
public class ConsoleRunListener implements RunListener {
    private long firstEventTime = -1L;

    @Override
    public synchronized void onEvent(RunEvent e) {
        if (firstEventTime < 0) {
            firstEventTime = e.getTimeMillis();
        }
        System.out.println(String.format("[+%5d ms] %-20s %-18s %s",
                e.getTimeMillis() - firstEventTime,
                e.getType(),
                e.getStepId(),
                e.getMessage()));
    }
}
