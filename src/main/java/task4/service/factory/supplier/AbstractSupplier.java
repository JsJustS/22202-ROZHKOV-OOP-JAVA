package task4.service.factory.supplier;

import task4.service.factory.part.Identifiable;
import task4.service.factory.storage.Storage;

import java.util.function.Supplier;

public abstract class AbstractSupplier <T extends Identifiable> implements Runnable, Supplier<T> {

    private final Storage<T> storage;

    public AbstractSupplier(Storage<T> storage) {
        this.storage = storage;
    }

    @Override
    public T get() {
        return null;
    }

    @Override
    public void run() {
        while (Thread.currentThread().isAlive() && !Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(5100 - this.getSpeed());
                this.supplyStorage();
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    protected int getSpeed() {
        return 0;
    }

    protected void supplyStorage() throws InterruptedException {
        synchronized (this.storage) {
            while (this.storage.isFull()) {
                this.storage.wait();
            }

            this.storage.store(this.get());
            this.storage.notifyAll();
        }
    }
}
