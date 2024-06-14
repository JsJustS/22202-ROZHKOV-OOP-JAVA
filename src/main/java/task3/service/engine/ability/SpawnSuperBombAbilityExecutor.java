package task3.service.engine.ability;

import task3.model.GameModel;
import task3.model.abilityInstance.AbstractAbilityInstanceModel;
import task3.model.entity.SuperBombEntityModel;

public class SpawnSuperBombAbilityExecutor extends AbstractAbilityExecutor {
    @Override
    public void execute(AbstractAbilityInstanceModel abilityInstance, GameModel model) {
        super.execute(abilityInstance, model);

        SuperBombEntityModel bomb = new SuperBombEntityModel(abilityInstance.getParent());
        bomb.setX(abilityInstance.getX());
        bomb.setY(abilityInstance.getY());
        bomb.setId(model.getLastEntityId() + 1);
        model.setLastEntityId(bomb.getId());
        model.addEntity(bomb);
    }
}
