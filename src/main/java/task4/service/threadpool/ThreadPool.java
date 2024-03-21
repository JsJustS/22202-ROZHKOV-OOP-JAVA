package task4.service.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool {
    private final int threadCount;
    private final List<WorkerThread> threads;

    public ThreadPool(int threadCount, String name) {
        this.threadCount = threadCount;

        this.threads = new ArrayList<>();
        for (int i = 1; i <= threadCount; ++i) {
            WorkerThread worker = new WorkerThread();
            worker.setName(name + "-" + i);
            this.threads.add(worker);
        }
    }

    public void start() {
        for (WorkerThread worker : this.threads) {
            worker.start();
        }
    }

    /**
     * Sets "isRunning" flag to False.
     * Does not guarantee that all threads will be stopped immediately!
     * All threads will finish their tasks and then stop doing new ones.
     * If thread is doing some infinite task, it will not stop.
     * */
    public void stop() {
        for (WorkerThread worker : this.threads) {
            worker.stopThread();
        }
    }

    public void kill() {
        for (WorkerThread worker : this.threads) {
            if (worker.isAlive()) {
                worker.interrupt();
            }
        }
    }

    public int getThreadCount() {
        return this.threadCount;
    }

    /**
     * Adds provided task to all threads in the pool
     * */
    public void addTaskForAll(Runnable task) {
        for (WorkerThread worker : this.threads) {
            worker.addTask(task);
        }
    }

    /**
     * Adds task to queue for threads in pool to execute
     * */
    public void addTask(Runnable task) {
        int min = Integer.MAX_VALUE;
        WorkerThread laziest = this.threads.get(0);
        for (WorkerThread worker : this.threads) {
            int busyness = worker.getBusyness();
            if (busyness < min) {
                min = busyness;
                laziest = worker;
            }
        }
        laziest.addTask(task);
    }

    private class WorkerThread extends Thread {
        private final Queue<Runnable> taskQueue;
        private boolean isRunning;

        public WorkerThread() {
            this.taskQueue = new LinkedBlockingQueue<>();
            this.isRunning = false;
        }

        @Override
        public void start() {
            super.start();
            this.isRunning = true;
        }

        @Override
        public void run() {
            while (this.isRunning) {
                Runnable task = this.taskQueue.poll();
                if (task != null) {
                    task.run();
                }
            }
        }

        public void addTask(Runnable task) {
            this.taskQueue.add(task);
        }

        public int getBusyness() {
            return this.taskQueue.size();
        }

        public void stopThread() {
            this.isRunning = false;
        }
    }
}
