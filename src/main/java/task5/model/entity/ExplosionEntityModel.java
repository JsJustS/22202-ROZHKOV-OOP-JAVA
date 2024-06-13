package task5.model.entity;

import java.util.ArrayList;
import java.util.List;

public class ExplosionEntityModel extends EntityModel {
    protected int timeToLive = 10;

    public ExplosionEntityModel() {
        loadSpriteSheets();
        setAnimationPerTick(2);
        setCollidable(false);
        setWidth(1);
        setHeight(1);
    }

    @Override
    public void loadSpriteSheets() {
        List<String> spriteSheet = new ArrayList<>();
        spriteSheet.add("task5/img/entity/bomb/explosion_1.png");
        spriteSheet.add("task5/img/entity/bomb/explosion_2.png");
        spriteSheet.add("task5/img/entity/bomb/explosion_3.png");
        spriteSheet.add("task5/img/entity/bomb/explosion_4.png");
        spriteSheet.add("task5/img/entity/bomb/explosion_5.png");
        this.addSpriteSheet(direction, spriteSheet);
    }

    public int getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }
}
