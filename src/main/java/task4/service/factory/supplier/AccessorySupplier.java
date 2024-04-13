package task4.service.factory.supplier;

import task4.controller.FabricController;
import task4.model.World;
import task4.service.factory.part.AccessoryPart;
import task4.service.factory.storage.Storage;

public class AccessorySupplier extends AbstractSupplier<AccessoryPart> {
    private final World world;
    private final FabricController controller;

    public AccessorySupplier(Storage<AccessoryPart> storage, World world, FabricController controller) {
        super(storage);
        this.world = world;
        this.controller = controller;
    }

    @Override
    protected int getSpeed() {
        return this.world.getCreationSpeed(World.Part.ACCESSORY);
    }

    @Override
    public AccessoryPart get() {
        this.controller.execute(FabricController.Operation.UPD_ACCESSORY_CRAFTED, this.world, 1);
        return new AccessoryPart(Thread.currentThread().getName());
    }
}
