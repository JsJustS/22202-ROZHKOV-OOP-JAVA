package task4.service.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task4.model.World;
import task4.service.factory.storage.Storage;

public class Dealer<T> implements Runnable {
    private final World world;
    private final Storage<T> storage;

    private static final Logger LOGGER = LoggerFactory.getLogger(Dealer.class);
    private final boolean shouldLog;

    public Dealer(World world, Storage<T> storage, boolean log) {
        this.world = world;
        this.storage = storage;
        this.shouldLog = log;
    }

    @Override
    public void run() {
        while (Thread.currentThread().isAlive() && !Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(world.getDealersSpeed());
                this.buy();
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    private void buy() {
        synchronized (this.storage) {
            while (this.storage.isEmpty()) {
                try {
                    this.storage.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            // buy goodies
            T goodies = this.storage.grabFirst();
            if (this.shouldLog) LOGGER.info(
                    String.format("%1$d: Dealer %2$s: %3$s",
                            System.currentTimeMillis(), Thread.currentThread().getName(), goodies.toString())
            );
        }
    }
}
