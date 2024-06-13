package task5.service.engine.ability;

import task5.model.GameModel;
import task5.model.abilityInstance.AbstractAbilityInstanceModel;
import task5.model.abilityInstance.SpawnExplosionAbilityInstanceModel;
import task5.model.entity.EntityModel;
import task5.model.entity.ExplosionEntityModel;
import task5.model.entity.blockentity.BlockEntityModel;
import task5.service.engine.entity.EntityService;

public class SpawnExplosionAbilityExecutor extends AbstractAbilityExecutor {
    @Override
    public void execute(AbstractAbilityInstanceModel abilityInstance, GameModel model) {
        super.execute(abilityInstance, model);
        if (!(abilityInstance instanceof SpawnExplosionAbilityInstanceModel)) {
            return;
        }
        SpawnExplosionAbilityInstanceModel explosionAbility = (SpawnExplosionAbilityInstanceModel) abilityInstance;
        for (int direction = 0; direction < 4; ++direction) {
            int x = (int) explosionAbility.getX();
            int y = (int) explosionAbility.getY();
            int powerLeft = explosionAbility.getPower();
            while (powerLeft > 0) {
                BlockEntityModel block = EntityService.getBlockEntityByCoordinates(x, y, model);
                if (block != null) {
                    if (block.getBlastResistance() > powerLeft) break;
                    powerLeft -= block.getBlastResistance();
                    block.setAttacker(explosionAbility.getParent());
                    block.setReceivedDamage(1);
                } else {
                    for (EntityModel entity : model.getEntities()) {
                        if (entity instanceof ExplosionEntityModel) {
                            continue;
                        }
                        if (entity instanceof BlockEntityModel) {
                            continue;
                        }
                        if (x > entity.getX() + entity.getHitboxWidth()/2 || entity.getX() - entity.getHitboxWidth()/2 > x + 1) {
                            continue;
                        }
                        if (y > entity.getY() + entity.getHitboxHeight()/2 || entity.getY() - entity.getHitboxHeight()/2 > y + 1) {
                            continue;
                        }
                        entity.setAttacker(explosionAbility.getParent());
                        entity.setReceivedDamage(entity.getReceivedDamage()+1);
                    }
                    powerLeft--;
                }

                if (direction == 0 || x != (int)explosionAbility.getX() || y != (int)explosionAbility.getY()) {
                    ExplosionEntityModel explosion = new ExplosionEntityModel();
                    explosion.setX(x+.5);
                    explosion.setY(y+.5);
                    explosion.setId(model.getLastEntityId()+1);
                    model.setLastEntityId(explosion.getId());
                    model.addEntity(explosion);
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
