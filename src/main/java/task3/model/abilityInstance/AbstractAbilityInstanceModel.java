package task3.model.abilityInstance;

import task3.model.IModel;
import task3.model.entity.EntityModel;
import task3.service.registry.EntityRegistry;

public abstract class AbstractAbilityInstanceModel implements IModel {
    protected EntityModel parent;
    protected double x;
    protected double y;

    public EntityModel getParent() {
        return parent;
    }

    public <T> void onFinish(T value) {
        EntityRegistry.getService(parent).onFinishAbility(parent, value);
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
