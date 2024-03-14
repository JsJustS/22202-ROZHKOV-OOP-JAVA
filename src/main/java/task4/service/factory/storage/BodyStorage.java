package task4.service.factory.storage;

import task4.model.World;
import task4.service.factory.part.BodyPart;

public class BodyStorage extends Storage<BodyPart> {
    public BodyStorage(World world, int maxSize) {
        super(world, maxSize);
    }

    @Override
    protected void notifyWorld() {
        super.notifyWorld();
        this.world.setBodyStoredCount(this.stored.size());
    }
}
