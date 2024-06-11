package task3.model.entity;

import task3.model.abilityInstance.Ability;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BotEntityModel extends EntityModel {
    private final Map<Direction, List<String>> spriteSheets = new HashMap<>();
    private int animationTick = 0;
    private int animationStep = 0;

    public BotEntityModel() {
        setWidth(0.9);
        setHeight(0.9);
        setAbility(Ability.EXPLOSION);
        setRenderLayer(RenderLayer.PLAYERS);
        loadSpriteSheets();
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
        spriteSheet.add(String.format("img/entity/bot/bot_%s_1.png", spriteName));
        spriteSheet.add(String.format("img/entity/bot/bot_%s_2.png", spriteName));
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
}
