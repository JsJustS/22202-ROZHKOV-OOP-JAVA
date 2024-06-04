package task3.engine.entity;

import task3.model.GameModel;
import task3.util.ResourceManager;

public class ExplosionEntity extends Entity {
    private int timeToLive;

    public ExplosionEntity() {
        this(0, 0);
    }

    public ExplosionEntity(double x, double y) {
        setWidth(1);
        setHeight(1);
        this.timeToLive = 10; // 1 sec
        this.x = x;
        this.y = y;
        this.isCollidable = false;
        this.sprite = ResourceManager.loadImage("img/entity/bomb/explosion.png");
    }

    @Override
    public void tick(GameModel model) {
        super.tick(model);
        this.timeToLive--;
        if (this.timeToLive <= 0) {
            this.kill();
        }
    }
}
