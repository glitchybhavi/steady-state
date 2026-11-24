package demo;

public class RaceConditionDemo {
    public static void run() throws InterruptedException {
        final UnsafeCounter unsafe = new UnsafeCounter();
        final SafeCounter safe = new SafeCounter();

        Thread[] threads = new Thread[4];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 100000; j++) {
                        unsafe.increment();
                        safe.increment();
                    }
                }
            });
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        System.out.println("Expected: 400000");
        System.out.println("Unsafe counter: " + unsafe.get()
                + " (usually less: lost updates)");
        System.out.println("Safe counter:   " + safe.get());
    }
}

