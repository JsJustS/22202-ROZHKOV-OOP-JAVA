package task3.engine.ability;

import task3.controller.NetworkS2CController;
import task3.engine.block.Block;
import task3.engine.entity.BombEntity;
import task3.engine.entity.Entity;
import task3.engine.entity.EntityRegistry;
import task3.engine.entity.ExplosionEntity;
import task3.model.GameModel;

public class ExplosionAbilityInstance extends AbstractAbilityInstance {
    private final int power;
    private final int x;
    private final int y;
    public ExplosionAbilityInstance(int power, int x, int y, Entity parent) {
        super(x, y, parent);
        this.power = power;
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute(GameModel model, NetworkS2CController networkController) {
        super.execute(model, networkController);
        for (int direction = 0; direction < 4; ++direction) {
            int x = this.x;
            int y = this.y;
            int powerLeft = this.power;
            while (powerLeft > 0) {
                Block block = model.getBlock(x, y);
                if (block != null) {
                    if (block.getBlastResistance() > powerLeft) break;
                    powerLeft -= block.getBlastResistance();
                    model.removeBlock(x, y);
                    block.onExplosion(model);
                    networkController.execute(
                            NetworkS2CController.PacketType.BLOCK_REMOVED,
                            new int[]{x, y}
                    );
                } else {
                    powerLeft--;
                }

                if (direction != 0 || x != this.x) {
                    ExplosionEntity explosion = new ExplosionEntity(x, y);
                    explosion.setId(model.getLastEntityId()+1);
                    model.setLastEntityId(explosion.getId());
                    model.spawnEntity(explosion);

                    networkController.execute(
                            NetworkS2CController.PacketType.ENTITY_SPAWNED,
                            new double[]{EntityRegistry.Entities.EXPLOSION.ordinal(), x+0.5, y+0.5, explosion.getId()}
                    );
                }

                switch (direction) {
                    case 0: x++; break;
                    case 1: y++; break;
                    case 2: x--; break;
                    case 3: y--; break;
                }
            }
        }
    }
}
