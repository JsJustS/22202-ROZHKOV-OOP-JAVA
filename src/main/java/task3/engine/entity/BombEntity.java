package task3.engine.entity;

import task3.engine.ability.ExplosionAbilityInstance;
import task3.model.GameModel;
import task3.util.ResourceManager;

public class BombEntity extends Entity {
    protected int timeToLive;
    protected int power;
    protected GameModel gameModel;

    public BombEntity() {
        this(0, 0, null);
    }

    public BombEntity(double x, double y, GameModel gameModel) {
        super();
        setHeight(0.75);
        setWidth(0.75);
        this.gameModel = gameModel;
        this.timeToLive = 60; // 3 seconds
        this.power = 3; // block where spawned + 2 radius
        this.x = ((int)x)+0.5d;
        this.y = ((int)y)+0.5d;

        this.sprite = ResourceManager.loadImage("img/entity/bomb/bomb.png");
    }

    public void setGameModel(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    @Override
    public void tick(GameModel model) {
        super.tick(model);
        this.timeToLive--;
        if (this.timeToLive <= 0) {
            this.explode();
        }
    }

    public void explode() {
        gameModel.addAbilityInstance(
                new ExplosionAbilityInstance(this.power, (int)this.x, (int)this.y, this)
        );
        this.kill();
    }
}
