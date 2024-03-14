package task4.service.factory.supplier;

import task4.model.World;
import task4.service.factory.part.AccessoryPart;
import task4.service.factory.storage.Storage;

public class AccessorySupplier extends AbstractSupplier<AccessoryPart> {
    private final World world;

    public AccessorySupplier(Storage<AccessoryPart> storage, World world) {
        super(storage);
        this.world = world;
    }

    @Override
    protected int getSpeed() {
        return this.world.getCreationSpeed(World.Part.ACCESSORY);
    }

    @Override
    public AccessoryPart get() {
        return new AccessoryPart(this.toString(), Thread.currentThread().getName());
    }
}
