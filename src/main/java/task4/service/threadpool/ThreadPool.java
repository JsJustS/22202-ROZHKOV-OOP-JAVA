package task4.service.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool {
    private final int threadCount;
    private final List<WorkerThread> threads;
    private final Queue<Runnable> taskQueue;

    public ThreadPool(int threadCount, String name) {
        this.threadCount = threadCount;
        this.taskQueue = new LinkedBlockingQueue<>();

        this.threads = new ArrayList<>();
        for (int i = 1; i <= threadCount; ++i) {
            WorkerThread worker = new WorkerThread(this);
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

    public int getQueueSize() {
        return this.taskQueue.size();
    }

    private Runnable getTask() {
        return this.taskQueue.poll();
    }

    /**
     * Adds task to queue for threads in pool to execute
     * */
    public void addTask(Runnable task) {
        this.taskQueue.add(task);
    }

    private class WorkerThread extends Thread {

        private boolean isRunning;
        private final ThreadPool parentPool;

        public WorkerThread(ThreadPool parent) {
            this.parentPool = parent;
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
                Runnable task = this.parentPool.getTask();
                if (task != null) {
                    task.run();
                }
            }
        }

        public void stopThread() {
            this.isRunning = false;
        }
    }
}
