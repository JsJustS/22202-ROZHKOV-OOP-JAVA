package task4.service.factory.storage;

import task4.controller.FabricController;
import task4.model.World;
import task4.service.factory.part.BodyPart;

public class BodyStorage extends Storage<BodyPart> {
    public BodyStorage(World world, FabricController controller, int maxSize) {
        super(world, controller, maxSize);
    }

    @Override
    protected void notifyWorld() {
        super.notifyWorld();
        this.controller.execute(FabricController.Operation.UPD_BODY_STORED, this.world, this.stored.size());
    }
}
