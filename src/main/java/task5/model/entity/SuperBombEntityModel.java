package task5.model.entity;

import java.util.ArrayList;
import java.util.List;

public class SuperBombEntityModel extends BombEntityModel {
    public SuperBombEntityModel() {
        super();
        this.setPower(9);
        this.entityType = EntityType.SuperBomb;
    }

    @Override
    public void loadSpriteSheets() {
        List<String> spriteSheet = new ArrayList<>();
        spriteSheet.add("task5/img/entity/bomb/super_bomb_1.png");
        spriteSheet.add("task5/img/entity/bomb/super_bomb_2.png");
        this.addSpriteSheet(Direction.DOWN, spriteSheet);
    }
}
