package task3.model.entity;

import java.util.ArrayList;
import java.util.List;

public class BombEntityModel extends EntityModel {
    protected int timeToLive = 60;
    protected int power = 3;
    private final EntityModel parent;

    public BombEntityModel(EntityModel parent) {
        setRenderLayer(RenderLayer.BOMBS);
        setHeight(0.75);
        setWidth(0.75);
        this.parent = parent;
        setCollidable(false);
        setAnimationPerTick(5);
    }

    @Override
    public void loadSpriteSheets() {
        List<String> spriteSheet = new ArrayList<>();
        spriteSheet.add("img/entity/bomb/bomb_1.png");
        spriteSheet.add("img/entity/bomb/bomb_2.png");
        this.addSpriteSheet(Direction.DOWN, spriteSheet);
    }

    public EntityModel getParent() {
        return parent;
    }

    public int getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    public int getPower() {
        return power;
    }

    protected void setPower(int power) {
        this.power = power;
    }
}
