package task3.engine.ability;

import task3.controller.NetworkS2CController;
import task3.engine.entity.BombEntity;
import task3.engine.entity.Entity;
import task3.engine.entity.EntityRegistry;
import task3.engine.entity.PlayerEntity;
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
        BombEntity bomb = new BombEntity(x, y, model, parent);
        bomb.setId(model.getLastEntityId()+1);
        model.setLastEntityId(bomb.getId());
        model.spawnEntity(bomb);

        networkController.execute(
                NetworkS2CController.PacketType.ENTITY_SPAWNED,
                new double[]{EntityRegistry.Entities.BOMB.ordinal(), x+0.5, y+0.5, bomb.getId()}
        );
        if (this.parent instanceof PlayerEntity) {
            networkController.execute(
                    NetworkS2CController.PacketType.PLAYER_STATUS,
                    new int[]{this.parent.getId(), ((PlayerEntity)this.parent).getPoints(), ((PlayerEntity)this.parent).getBombsLeft()}
            );
        }
    }
}
