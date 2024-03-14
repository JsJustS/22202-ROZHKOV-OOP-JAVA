package task4.service.factory.storage;

import task4.model.World;

import java.util.ArrayList;
import java.util.List;

public abstract class Storage <T> {
    protected final List<T> stored;
    protected final World world;
    private final int maxSize;

    public Storage() {
        this(null);
    }

    public Storage(World world) {
        this(world, 0);
    }

    public Storage(World world, int maxSize) {
        this.stored = new ArrayList<T>(maxSize);
        this.world = world;
        this.maxSize = maxSize;
    }

    public boolean isFull() {
        return this.stored.size() == this.maxSize;
    }

    public boolean isEmpty() {
        return this.stored.isEmpty();
    }

    public void store(T e) {
        if (this.stored.size() + 1 >= maxSize) throw new RuntimeException("No storage space left.");
        stored.add(e);
        this.notifyWorld();
    }

    public T grabFirst() {
        try {
            return this.grab(0);
        } catch (IndexOutOfBoundsException ignored) {
            throw new RuntimeException("This storage is empty");
        }
    }

    public T grab(int index) {
        if (index < 0 || this.stored.size() <= index) throw new IndexOutOfBoundsException();
        T obj = this.stored.get(index);
        this.stored.remove(obj);
        this.notifyWorld();
        return obj;
    }

    protected void notifyWorld() {
        if (this.world == null) throw new RuntimeException("World does not exist.");
    }
}
