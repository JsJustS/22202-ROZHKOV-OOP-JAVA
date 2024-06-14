package task5.server.service.engine.ability;

import task5.model.GameModel;
import task5.model.abilityInstance.AbstractAbilityInstanceModel;
import task5.model.abilityInstance.SpawnEntityAbilityInstanceModel;
import task5.model.entity.EntityModel;
import task5.model.entity.EntityType;
import task5.server.SocketServer;
import task5.util.network.s2c.BindPlayerS2CPacket;
import task5.util.network.s2c.EntitySpawnS2CPacket;

public class SpawnEntityAbilityExecutor extends AbstractAbilityExecutor {

    @Override
    public void execute(AbstractAbilityInstanceModel abilityInstance, GameModel model, SocketServer network) {
        super.execute(abilityInstance, model, network);
        if (!(abilityInstance instanceof SpawnEntityAbilityInstanceModel)) {
            return;
        }
        SpawnEntityAbilityInstanceModel spawnAbilityInstance = (SpawnEntityAbilityInstanceModel) abilityInstance;
        EntityModel entity = spawnAbilityInstance.getEntityToSpawn();

        entity.setId(model.getLastEntityId() + 1);
        model.setLastEntityId(entity.getId());
        model.addEntity(entity);

        network.broadcast(
                new EntitySpawnS2CPacket(spawnAbilityInstance.getEntityType(), entity.getId(), entity.getX(), entity.getY())
        );
        if (spawnAbilityInstance.getEntityType() == EntityType.Player) {
            network.send(
                    spawnAbilityInstance.getBoundUUID(),
                    new BindPlayerS2CPacket(entity.getId())
            );
        }
    }
}
