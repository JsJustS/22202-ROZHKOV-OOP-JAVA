package task5.model.entity;

import task5.model.abilityInstance.Ability;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerEntityModel extends EntityModel {
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
    public void loadSpriteSheets() {
        loadSprites(Direction.LEFT, "left");
        loadSprites(Direction.RIGHT, "right");
        loadSprites(Direction.UP, "up");
        loadSprites(Direction.DOWN, "down");
    }

    private void loadSprites(Direction direction, String spriteName) {
        List<String> spriteSheet = new ArrayList<>();
        spriteSheet.add(String.format("task5/img/entity/player/player_%s_1.png", spriteName));
        spriteSheet.add(String.format("task5/img/entity/player/player_%s_2.png", spriteName));
        this.addSpriteSheet(direction, spriteSheet);
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
