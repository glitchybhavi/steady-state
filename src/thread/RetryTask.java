package thread;

import model.StepDefinition;
import service.ReadyQueue;

public class RetryTask implements Runnable {
    private final ReadyQueue queue;
    private final StepDefinition def;

    public RetryTask(ReadyQueue queue, StepDefinition def) {
        this.queue = queue;
        this.def = def;
    }

    @Override
    public void run() {
        queue.put(def);
    }
}

