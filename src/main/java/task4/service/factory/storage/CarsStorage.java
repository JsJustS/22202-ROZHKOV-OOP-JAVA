package task4.service.factory.storage;

import task4.controller.FabricController;
import task4.model.World;
import task4.service.factory.part.Car;

public class CarsStorage extends Storage<Car>{

    public CarsStorage(World world, FabricController controller, int maxSize) {
        super(world, controller, maxSize);
    }

    @Override
    protected void notifyWorld() {
        super.notifyWorld();
        this.controller.execute(FabricController.Operation.UPD_CAR_STORED, this.world, this.stored.size());
    }
}
