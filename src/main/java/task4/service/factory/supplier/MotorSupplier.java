package task4.service.factory.supplier;

import task4.controller.FabricController;
import task4.model.World;
import task4.service.factory.part.MotorPart;
import task4.service.factory.storage.Storage;

public class MotorSupplier extends AbstractSupplier<MotorPart> {
    private final World world;
    private final FabricController controller;

    public MotorSupplier(Storage<MotorPart> storage, World world, FabricController controller) {
        super(storage);
        this.world = world;
        this.controller = controller;
    }

    @Override
    protected int getSpeed() {
        return this.world.getCreationSpeed(World.Part.MOTOR);
    }

    @Override
    public MotorPart get() {
        this.controller.execute(FabricController.Operation.UPD_MOTOR_CRAFTED, this.world, 1);
        return new MotorPart(Thread.currentThread().getName());
    }
}
