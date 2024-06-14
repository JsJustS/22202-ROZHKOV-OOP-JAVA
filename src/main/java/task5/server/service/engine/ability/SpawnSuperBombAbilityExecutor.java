package task5.server.service.engine.ability;

import task5.model.GameModel;
import task5.model.abilityInstance.AbstractAbilityInstanceModel;
import task5.model.abilityInstance.SpawnEntityAbilityInstanceModel;
import task5.model.entity.EntityType;
import task5.model.entity.SuperBombEntityModel;
import task5.server.SocketServer;

public class SpawnSuperBombAbilityExecutor extends AbstractAbilityExecutor {
    @Override
    public void execute(AbstractAbilityInstanceModel abilityInstance, GameModel model, SocketServer network) {
        super.execute(abilityInstance, model, network);

        SuperBombEntityModel bomb = new SuperBombEntityModel(abilityInstance.getParent());
        bomb.setX(abilityInstance.getX());
        bomb.setY(abilityInstance.getY());
        /*bomb.setId(model.getLastEntityId() + 1);
        model.setLastEntityId(bomb.getId());
        model.addEntity(bomb);*/
        model.addAbilityInstance(
                new SpawnEntityAbilityInstanceModel(bomb, EntityType.SuperBomb)
        );
    }
}
