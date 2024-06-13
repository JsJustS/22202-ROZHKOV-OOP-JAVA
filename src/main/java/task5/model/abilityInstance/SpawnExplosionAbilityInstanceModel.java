package task5.model.abilityInstance;

import task5.model.entity.EntityModel;

public class SpawnExplosionAbilityInstanceModel extends AbstractAbilityInstanceModel {
    private final int power;
    public SpawnExplosionAbilityInstanceModel(int power, double x, double y, EntityModel parent) {
        this.power = power;
        this.x = x;
        this.y = y;
        this.parent = parent;
    }

    public int getPower() {
        return power;
    }
}
