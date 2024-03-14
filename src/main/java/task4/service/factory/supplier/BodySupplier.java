package task4.service.factory.supplier;

import task4.model.World;
import task4.service.factory.part.BodyPart;
import task4.service.factory.storage.Storage;

public class BodySupplier extends AbstractSupplier<BodyPart>{
    private final World world;

    public BodySupplier(Storage<BodyPart> storage, World world) {
        super(storage);
        this.world = world;
    }

    @Override
    protected int getSpeed() {
        return this.world.getCreationSpeed(World.Part.BODY);
    }

    @Override
    public BodyPart get() {
        return new BodyPart(this.toString());
    }
}
