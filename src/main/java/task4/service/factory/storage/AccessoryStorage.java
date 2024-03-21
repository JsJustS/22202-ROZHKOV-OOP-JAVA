package task4.service.factory.storage;

import task4.controller.FabricController;
import task4.model.World;
import task4.service.factory.part.AccessoryPart;

public class AccessoryStorage extends Storage<AccessoryPart> {
    public AccessoryStorage(World world, FabricController controller, int maxSize) {
        super(world, controller, maxSize);
    }

    @Override
    protected void notifyWorld() {
        super.notifyWorld();
        this.controller.execute(FabricController.Operation.UPD_ACCESSORY_STORED, this.world, this.stored.size());
    }
}
