package task5.model.abilityInstance;

import task5.model.entity.EntityModel;

public class SpawnBombAbilityInstanceModel extends AbstractAbilityInstanceModel {

    public SpawnBombAbilityInstanceModel(double x, double y, EntityModel parent) {
        this.x = x;
        this.y = y;
        this.parent = parent;
    }
}
