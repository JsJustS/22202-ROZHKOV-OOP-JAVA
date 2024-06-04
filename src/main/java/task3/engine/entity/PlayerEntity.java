package task3.engine.entity;

import task3.model.GameModel;
import task3.util.ResourceManager;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerEntity extends Entity {

    private final Map<String, List<BufferedImage>> spriteSheets = new HashMap<>();
    private int animationTick;
    private int animationStep;
    private int points;
    private int allTimePoints;

    private final double pointUponDeathCoefficient = 0.5;

    public PlayerEntity() {
        this(0, 0);
    }

    public PlayerEntity(double x, double y) {
        this.x = x;
        this.y = y;
        setWidth(0.9);
        setHeight(0.9);
        points = 0;
        allTimePoints = 0;

        this.animationTick = 0;
        loadSpriteSheets();
    }

    @Override
    public void tickMovement(GameModel model) {
        if (this.isMoving() && this.getVelocity() == 0) {
            this.setVelocity(this.getSpeed());
        }
        super.tickMovement(model);
    }

    @Override
    public <T> void onFinishAbility(T data) {
        super.onFinishAbility(data);
        if (data instanceof Integer) {
            this.addPoints((int)data);
        }
    }

    @Override
    public void damage(Entity source) {
        super.damage(source);
        this.setPoints(this.getPoints() - 100);
        if (this.points >= 0) return;

        if (source instanceof BombEntity) {
            BombEntity bomb = (BombEntity) source;
            if (bomb.getParent() instanceof PlayerEntity) {
                PlayerEntity playerSource = (PlayerEntity) bomb.getParent();
                if (!playerSource.equals(this)) {
                    playerSource.addPoints((int)(this.allTimePoints * this.pointUponDeathCoefficient));
                }
            }
        }
        this.kill();
    }

    @Override
    public BufferedImage getSprite() {
        this.animationTick++;
        if (animationTick % 20 == 0) {
            this.animationStep++;
        }
        switch (this.getDirection()) {
            case UP: return this.spriteSheets.get("up").get(this.animationStep % 2);
            case LEFT: return this.spriteSheets.get("left").get(this.animationStep % 2);
            case DOWN: return this.spriteSheets.get("down").get(this.animationStep % 2);
            case RIGHT: return this.spriteSheets.get("right").get(this.animationStep % 2);
        }
        return this.spriteSheets.get("up").get(this.animationStep % 2);
    }

    private void loadSpriteSheets() {
        loadSprites("left");
        loadSprites("right");
        loadSprites("up");
        loadSprites("down");
    }

    private void loadSprites(String spriteName) {
        List<BufferedImage> spriteSheet = new ArrayList<>();
        spriteSheet.add(ResourceManager.loadImage("img/entity/player/player_"+ spriteName +"_1.png"));
        spriteSheet.add(ResourceManager.loadImage("img/entity/player/player_"+ spriteName +"_2.png"));
        this.spriteSheets.put(spriteName, spriteSheet);
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void addPoints(int points) {
        this.points += points;
        this.allTimePoints += points;
    }
}
