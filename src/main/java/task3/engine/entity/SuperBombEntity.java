package task3.engine.entity;

import task3.model.GameModel;
import task3.util.ResourceManager;

public class SuperBombEntity extends BombEntity {
    private final int modifier = 3;

    public SuperBombEntity() {
        this(0, 0, null, null);
    }

    public SuperBombEntity(double x, double y, GameModel gameModel, Entity parent) {
        super(x, y, gameModel, parent);
        this.power = this.power * this.modifier;
        this.sprite = ResourceManager.loadImage("img/entity/bomb/super_bomb.png");
    }
}
