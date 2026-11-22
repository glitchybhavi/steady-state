package thread;

import model.StepDefinition;
import service.JobEngine;
import service.ReadyQueue;

public class Dispatcher extends Thread {
    private final JobEngine engine;
    private final ReadyQueue queue;

    public Dispatcher(JobEngine engine, ReadyQueue queue) {
        super("dispatcher");
        this.engine = engine;
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            StepDefinition next;
            while ((next = queue.take()) != null) {
                engine.startStep(next);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

