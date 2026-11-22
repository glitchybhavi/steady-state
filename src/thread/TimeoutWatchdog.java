package thread;

public class TimeoutWatchdog implements Runnable {
    private final StepTask task;
    private final Thread workerThread;

    public TimeoutWatchdog(StepTask task, Thread workerThread) {
        this.task = task;
        this.workerThread = workerThread;
    }

    @Override
    public void run() {
        task.markTimedOut();
        workerThread.interrupt();
    }
}

