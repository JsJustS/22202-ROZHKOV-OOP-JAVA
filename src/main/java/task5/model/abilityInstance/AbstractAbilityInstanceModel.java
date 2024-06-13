package task5.model.abilityInstance;

import task5.model.IModel;
import task5.model.entity.EntityModel;
import task5.service.registry.EntityRegistry;

public abstract class AbstractAbilityInstanceModel implements IModel {
    protected EntityModel parent;
    protected double x;
    protected double y;

    public EntityModel getParent() {
        return parent;
    }

    public void setParent(EntityModel parent) {
        this.parent = parent;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
