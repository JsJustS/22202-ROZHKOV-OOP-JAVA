package task3.model.abilityInstance;

import task3.model.entity.EntityModel;

public class SpawnExplosionAbilityInstanceModel extends AbstractAbilityInstanceModel {
    private int power;
    public SpawnExplosionAbilityInstanceModel(int power, double x, double y, EntityModel parent) {
        this.power = power;
        this.x = x;
        this.y = y;
        this.parent = parent;
    }
}
