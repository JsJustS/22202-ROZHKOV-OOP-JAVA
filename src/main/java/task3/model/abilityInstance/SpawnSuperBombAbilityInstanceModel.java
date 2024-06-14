package task3.model.abilityInstance;

import task3.model.entity.EntityModel;

public class SpawnSuperBombAbilityInstanceModel extends AbstractAbilityInstanceModel {
    public SpawnSuperBombAbilityInstanceModel(double x, double y, EntityModel parent) {
        this.x = x;
        this.y = y;
        this.parent = parent;
    }
}
