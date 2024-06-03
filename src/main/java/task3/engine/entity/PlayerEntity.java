package task3.engine.entity;

import task3.util.ResourceManager;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerEntity extends Entity {
    private final Map<String, List<BufferedImage>> spriteSheets = new HashMap<>();
    private int animationTick;

    public PlayerEntity() {
        this(0, 0);
    }

    public PlayerEntity(double x, double y) {
        this.x = x;
        this.y = y;
        setWidth(0.9);
        setHeight(0.9);

        this.animationTick = 0;
        loadSpriteSheets();
    }

    @Override
    public BufferedImage getSprite() {
        this.animationTick++;
        int spriteIndex = (animationTick % 10 == 0) ? 0: 1;
        if (this.getVelocityX() > 0) {
            if (this.getVelocityX() > this.getVelocityY()) {
                return this.spriteSheets.get("left").get(spriteIndex);
            }
            return this.spriteSheets.get("down").get(spriteIndex);
        } else if (this.getVelocityX() < 0) {
            if (this.getVelocityX() < this.getVelocityY()) {
                return this.spriteSheets.get("right").get(spriteIndex);
            }
            return this.spriteSheets.get("up").get(spriteIndex);
        }
        return this.spriteSheets.get("calm").get(spriteIndex);
    }

    private void loadSpriteSheets() {
        loadSprites("calm");
        loadSprites("left");
        loadSprites("right");
        loadSprites("up");
        loadSprites("down");
    }

    private void loadSprites(String keyName) {
        List<BufferedImage> calm = new ArrayList<>();
        calm.add(ResourceManager.loadImage("img/entity/player/player_"+ keyName +"_1.png"));
        calm.add(ResourceManager.loadImage("img/entity/player/player_"+ keyName +"_2.png"));
        this.spriteSheets.put(keyName, calm);
    }
}
