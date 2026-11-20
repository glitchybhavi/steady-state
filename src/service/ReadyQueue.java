package service;

import model.StepDefinition;
import policy.SchedulingStrategy;
import java.util.PriorityQueue;

public class ReadyQueue {
    private final PriorityQueue<StepDefinition> queue;
    private boolean closed = false;

    public ReadyQueue(SchedulingStrategy strategy) {
        queue = new PriorityQueue<>(11, strategy);
    }

    public synchronized void put(StepDefinition step) {
        if (closed) {
            return;
        }
        queue.add(step);
        notifyAll();
    }

    public synchronized StepDefinition take() throws InterruptedException {
        while (queue.isEmpty() && !closed) {
            wait();
        }
        return queue.poll();
    }

    public synchronized void close() {
        closed = true;
        notifyAll();
    }
}

