package task3.engine.ability;

import task3.controller.NetworkS2CController;
import task3.engine.entity.SuperBombEntity;
import task3.engine.entity.Entity;
import task3.engine.entity.EntityRegistry;
import task3.engine.entity.PlayerEntity;
import task3.model.GameModel;

public class SpawnSuperBombAbilityInstance extends SpawnBombAbilityInstance {
    public SpawnSuperBombAbilityInstance(int x, int y, Entity parent) {
        super(x, y, parent);
    }

    @Override
    public void execute(GameModel model, NetworkS2CController networkController) {
        SuperBombEntity bomb = new SuperBombEntity(this.x, this.y, model, parent);
        bomb.setId(model.getLastEntityId()+1);
        model.setLastEntityId(bomb.getId());
        model.spawnEntity(bomb);

        networkController.execute(
                NetworkS2CController.PacketType.ENTITY_SPAWNED,
                new double[]{EntityRegistry.Entities.SUPER_BOMB.ordinal(), x+0.5, y+0.5, bomb.getId()}
        );
        if (this.parent instanceof PlayerEntity) {
            ((PlayerEntity) this.parent).markDirty(true);
        }
    }
}
