package task3.engine.entity;

import task3.engine.ability.ExplosionAbilityInstance;
import task3.engine.block.Block;
import task3.model.GameModel;

public class BombEntity extends Entity {
    protected int timeToLive;
    protected int power;
    protected GameModel gameModel;

    public BombEntity(int x, int y, GameModel gameModel) {
        super(0.5, 0.5);
        this.gameModel = gameModel;
        this.timeToLive = 60; // 3 seconds
        this.power = 6; // block where spawned + 5 radius
        this.x = x;
        this.y = y;
    }

    @Override
    public void tick() {
        super.tick();
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
