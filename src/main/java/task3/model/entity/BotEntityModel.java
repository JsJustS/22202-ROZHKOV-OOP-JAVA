package task3.model.entity;

import task3.model.abilityInstance.Ability;
import task3.util.Pair;

import java.util.*;

public class BotEntityModel extends EntityModel {
    private Stack<Pair<Integer, Integer>> path = new Stack<>();
    private Pair<Integer, Integer> goal;

    public BotEntityModel() {
        setWidth(0.9);
        setHeight(0.9);
        setAbility(Ability.EXPLOSION);
        setRenderLayer(RenderLayer.PLAYERS);
        loadSpriteSheets();
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
        spriteSheet.add(String.format("img/entity/bot/bot_%s_1.png", spriteName));
        spriteSheet.add(String.format("img/entity/bot/bot_%s_2.png", spriteName));
        this.addSpriteSheet(direction, spriteSheet);
    }

    public Stack<Pair<Integer, Integer>> getPath() {
        return path;
    }

    public Pair<Integer, Integer> getGoal() {
        return goal;
    }

    public void setGoal(Pair<Integer, Integer> goal) {
        this.goal = goal;
    }

    public void setPath(Stack<Pair<Integer, Integer>> path) {
        this.path = path;
    }
}
