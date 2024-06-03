package task3.engine.ability;

import task3.controller.NetworkS2CController;
import task3.engine.entity.BombEntity;
import task3.engine.entity.Entity;
import task3.model.GameModel;

public class SpawnBombAbilityInstance extends AbstractAbilityInstance {
    private final int x;
    private final int y;
    public SpawnBombAbilityInstance(int x, int y, Entity parent) {
        super(x, y, parent);
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute(GameModel model, NetworkS2CController networkController) {
        super.execute(model, networkController);
        model.spawnEntity(
                new BombEntity(x, y, model)
        );
//        networkController.execute(
//                NetworkS2CController.PacketType.BLOCK_REMOVED,
//                new int[]{(int)this.x, (int)this.y}
//        );
    }
}
