package task3.engine.entity;

import task3.controller.NetworkS2CController;
import task3.engine.ability.SpawnBombAbilityInstance;
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

    private int bombsLeft;
    private int ticksUntilNextBombGranted;
    private final int bombsMax = 3;
    private final int ticksMaxUntilNextBombGranted = 60;

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
        bombsLeft = bombsMax;
        ticksUntilNextBombGranted = ticksMaxUntilNextBombGranted;

        this.animationTick = 0;
        loadSpriteSheets();
    }

    public void useAbility(GameModel model) {
        if (this.bombsLeft <= 0) {
            return;
        }

        for (Entity entity : model.getEntities()) {
            if (entity.equals(this)) {
                continue;
            }
            if ((int)this.getX() > entity.getX() + entity.getHitboxWidth()/2 || entity.getX() - entity.getHitboxWidth()/2 > (int)this.getX() + 1) {
                continue;
            }
            if ((int)this.getY() > entity.getY() + entity.getHitboxHeight()/2 || entity.getY() - entity.getHitboxHeight()/2 > (int)this.getY() + 1) {
                continue;
            }
            return;
        }
        model.addAbilityInstance(
                new SpawnBombAbilityInstance((int)this.getX(), (int)this.getY(), this)
        );
        this.bombsLeft--;
    }

    @Override
    public void tick(GameModel model, NetworkS2CController network) {
        super.tick(model, network);
        if (this.bombsLeft < this.bombsMax) {
            if (this.ticksUntilNextBombGranted > 0) {
                this.ticksUntilNextBombGranted--;
            } else {
                this.ticksUntilNextBombGranted = this.ticksMaxUntilNextBombGranted;
                this.bombsLeft++;
                network.execute(
                        NetworkS2CController.PacketType.PLAYER_STATUS,
                        new int[]{this.getId(), this.getPoints(), this.getBombsLeft()}
                );
            }
        }
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

    public int getBombsLeft() {
        return bombsLeft;
    }

    public void setBombsLeft(int bombsLeft) {
        this.bombsLeft = bombsLeft;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void addPoints(int points) {
        this.points += points;
        this.allTimePoints += points;
    }
}
