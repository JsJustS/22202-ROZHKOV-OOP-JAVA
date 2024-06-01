package task3.engine.ability;

import task3.controller.NetworkS2CController;
import task3.engine.entity.Entity;
import task3.model.GameModel;

public abstract class AbstractAbilityInstance {
    protected Entity parent;
    protected double x;
    protected double y;

    public AbstractAbilityInstance(int x, int y, Entity parent) {

    }

    public void execute(GameModel model, NetworkS2CController networkController) {

    }
}
