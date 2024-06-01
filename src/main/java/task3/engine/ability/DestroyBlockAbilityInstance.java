package task3.engine.ability;

import task3.controller.NetworkS2CController;
import task3.engine.entity.Entity;
import task3.model.GameModel;

public class DestroyBlockAbilityInstance extends AbstractAbilityInstance {
    public DestroyBlockAbilityInstance(int x, int y, Entity parent) {
        super(x, y, parent);
    }

    @Override
    public void execute(GameModel model, NetworkS2CController networkController) {
        super.execute(model, networkController);
        model.removeBlock((int)this.x, (int)this.y);
        networkController.execute(
                NetworkS2CController.PacketType.BLOCK_REMOVED,
                new int[]{(int)this.x, (int)this.y}
        );
    }
}
