package task4.service.factory.storage;

import task4.model.World;
import task4.service.factory.part.Car;

public class CarsStorage extends Storage<Car>{

    public CarsStorage(World world, int maxSize) {
        super(world, maxSize);
    }

    @Override
    protected void notifyWorld() {
        super.notifyWorld();
        this.world.setCarsStoredCount(this.stored.size());
    }
}
