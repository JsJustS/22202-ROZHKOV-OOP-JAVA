package task4.service.factory.storage;

import task4.controller.FabricController;
import task4.model.World;
import task4.service.factory.part.MotorPart;

public class MotorStorage extends Storage<MotorPart>{

    public MotorStorage(World world, FabricController controller, int maxSize) {
        super(world, controller, maxSize);
    }

    @Override
    protected void notifyWorld() {
        super.notifyWorld();
        this.controller.execute(FabricController.Operation.UPD_MOTOR_STORED, this.world, this.stored.size());
    }
}
