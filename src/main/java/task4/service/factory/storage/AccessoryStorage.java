package task4.service.factory.storage;

import task4.model.World;
import task4.service.factory.part.AccessoryPart;

public class AccessoryStorage extends Storage<AccessoryPart> {
    public AccessoryStorage(World world, int maxSize) {
        super(world, maxSize);
    }

    @Override
    protected void notifyWorld() {
        super.notifyWorld();
        this.world.setAccessoryStoredCount(this.stored.size());
    }
}
