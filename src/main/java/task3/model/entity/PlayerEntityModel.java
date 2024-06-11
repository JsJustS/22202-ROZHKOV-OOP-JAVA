package task3.model.entity;

import task3.model.abilityInstance.Ability;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerEntityModel extends EntityModel {
    private final Map<Direction, List<String>> spriteSheets = new HashMap<>();
    private int animationTick = 0;
    private int animationStep = 0;
    private int points = 0;
    private int allTimePoints = 0;

    private final int bombsMax = 3;
    private int bombsLeft = bombsMax;
    private final int ticksMaxUntilNextBombGranted = 60;
    private int ticksUntilNextBombGranted = ticksMaxUntilNextBombGranted;

    private final double pointUponDeathCoefficient = 0.5;

    public PlayerEntityModel() {
        setWidth(0.9);
        setHeight(0.9);
        loadSpriteSheets();
        setAbility(Ability.SIMPLE_BOMB);
        setRenderLayer(RenderLayer.PLAYERS);
        setDirection(Direction.DOWN);
    }

    @Override
    public String getSpritePath() {
        return this.spriteSheets.get(this.getDirection()).get(this.animationStep % 2);
    }
    private void loadSpriteSheets() {
        loadSprites(Direction.LEFT, "left");
        loadSprites(Direction.RIGHT, "right");
        loadSprites(Direction.UP, "up");
        loadSprites(Direction.DOWN, "down");
    }

    private void loadSprites(Direction direction, String spriteName) {
        List<String> spriteSheet = new ArrayList<>();
        spriteSheet.add(String.format("img/entity/player/player_%s_1.png", spriteName));
        spriteSheet.add(String.format("img/entity/player/player_%s_2.png", spriteName));
        this.spriteSheets.put(direction, spriteSheet);
    }

    public int getAnimationTick() {
        return animationTick;
    }

    public void setAnimationTick(int animationTick) {
        this.animationTick = animationTick;
    }

    public int getAnimationStep() {
        return animationStep;
    }

    public void setAnimationStep(int animationStep) {
        this.animationStep = animationStep;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getAllTimePoints() {
        return allTimePoints;
    }

    public void setAllTimePoints(int allTimePoints) {
        this.allTimePoints = allTimePoints;
    }

    public int getBombsLeft() {
        return bombsLeft;
    }

    public void setBombsLeft(int bombsLeft) {
        this.bombsLeft = bombsLeft;
    }

    public int getTicksUntilNextBombGranted() {
        return ticksUntilNextBombGranted;
    }

    public void setTicksUntilNextBombGranted(int ticksUntilNextBombGranted) {
        this.ticksUntilNextBombGranted = ticksUntilNextBombGranted;
    }

    public int getBombsMax() {
        return bombsMax;
    }

    public int getTicksMaxUntilNextBombGranted() {
        return ticksMaxUntilNextBombGranted;
    }

    public double getPointUponDeathCoefficient() {
        return pointUponDeathCoefficient;
    }
}
