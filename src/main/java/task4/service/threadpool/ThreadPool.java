package task4.service.threadpool;

public class ThreadPool {
    private final int threadCount;
    public ThreadPool(int threadCount, String name) {
        this.threadCount = threadCount;
    }

    /**
     * Adds provided task to all threads in the pool
     * */
    public void addTaskForAll(Runnable task) {

    }

    /**
     * Adds task to queue for threads in pool to execute
     * */
    public void addTask(Runnable task) {

    }

    private void createThread() {

    }
}
