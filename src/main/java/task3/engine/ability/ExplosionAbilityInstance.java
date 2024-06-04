package task3.engine.ability;

import task3.controller.NetworkS2CController;
import task3.engine.block.Block;
import task3.engine.entity.*;
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
        int collectedPoints = 0;
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
                    collectedPoints += block.getPoints();

                    networkController.execute(
                            NetworkS2CController.PacketType.BLOCK_REMOVED,
                            new int[]{x, y}
                    );
                } else {
                    for (Entity entity : model.getEntities()) {
                        if (entity instanceof ExplosionEntity) {
                            continue;
                        }
                        if (x > entity.getX() + entity.getHitboxWidth()/2 || entity.getX() - entity.getHitboxWidth()/2 > x + 1) {
                            continue;
                        }
                        if (y > entity.getY() + entity.getHitboxHeight()/2 || entity.getY() - entity.getHitboxHeight()/2 > y + 1) {
                            continue;
                        }
                        entity.damage(this.parent);
                    }
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
        this.parent.onFinishAbility(collectedPoints);

        if (!(this.parent instanceof BombEntity)) {
            return;
        }
        Entity entity = ((BombEntity)this.parent).getParent();
        if (entity instanceof PlayerEntity) {
            networkController.execute(
                    NetworkS2CController.PacketType.PLAYER_STATUS,
                    new int[]{entity.getId(), ((PlayerEntity)entity).getPoints()}
            );
        }
    }
}
