package task4.service.factory.supplier;

import task4.model.World;
import task4.service.factory.part.MotorPart;
import task4.service.factory.storage.Storage;

public class MotorSupplier extends AbstractSupplier<MotorPart> {
    private final World world;

    public MotorSupplier(Storage<MotorPart> storage, World world) {
        super(storage);
        this.world = world;
    }

    @Override
    protected int getSpeed() {
        return this.world.getCreationSpeed(World.Part.MOTOR);
    }

    @Override
    public MotorPart get() {
        return new MotorPart(this.toString());
    }
}
