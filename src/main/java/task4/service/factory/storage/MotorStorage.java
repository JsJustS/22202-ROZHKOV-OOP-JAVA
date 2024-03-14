package task4.service.factory.storage;

import task4.model.World;
import task4.service.factory.part.MotorPart;

public class MotorStorage extends Storage<MotorPart>{

    public MotorStorage(World world, int maxSize) {
        super(world, maxSize);
    }

    @Override
    protected void notifyWorld() {
        super.notifyWorld();
        this.world.setMotorStoredCount(this.stored.size());
    }
}
