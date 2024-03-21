package task4.service.factory.supplier;

import task4.controller.FabricController;
import task4.model.World;
import task4.service.factory.part.BodyPart;
import task4.service.factory.storage.Storage;

public class BodySupplier extends AbstractSupplier<BodyPart>{
    private final World world;
    private final FabricController controller;

    public BodySupplier(Storage<BodyPart> storage, World world, FabricController controller) {
        super(storage);
        this.world = world;
        this.controller = controller;
    }

    @Override
    protected int getSpeed() {
        return this.world.getCreationSpeed(World.Part.BODY);
    }

    @Override
    public BodyPart get() {
        this.controller.execute(FabricController.Operation.UPD_BODY_CRAFTED, this.world, 1);
        return new BodyPart(this.toString());
    }
}
