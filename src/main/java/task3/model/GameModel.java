package task3.model;

import task3.engine.ability.AbstractAbilityInstance;
import task3.engine.block.Block;
import task3.engine.block.BlockRegistry;
import task3.engine.entity.Entity;
import task3.util.pubsub.Publisher;

import java.util.HashSet;

public class GameModel extends Publisher implements IModel {
    private int fieldWidthInBlock;
    private int fieldHeightInBlocks;
    private boolean gameIsRunning;

    private final HashSet<Entity> entities = new HashSet<>();
    private final HashSet<Block> blocks = new HashSet<>();
    private final HashSet<AbstractAbilityInstance> abilityInstances = new HashSet<>();

    public void setGameRunning(boolean value) {
        this.gameIsRunning = value;
    }

    public boolean isGameRunning() {
        return this.gameIsRunning;
    }

    public int getFieldHeightInBlocks() {
        return fieldHeightInBlocks;
    }

    public int getFieldWidthInBlock() {
        return fieldWidthInBlock;
    }

    public HashSet<Entity> getEntities() {
        return entities;
    }

    public HashSet<Block> getBlocks() {
        return blocks;
    }

    public void setFieldHeightInBlocks(int fieldHeightInBlocks) {
        this.fieldHeightInBlocks = fieldHeightInBlocks;
    }

    public void setFieldWidthInBlock(int fieldWidthInBlock) {
        this.fieldWidthInBlock = fieldWidthInBlock;
    }

    public void addBlock(int x, int y, BlockRegistry.Blocks id) {
        Block block = BlockRegistry.getBlockById(x, y, id);
        if (block == null) return;
        blocks.add(block);
    }

    public void removeBlock(int x, int y) {
        for (Block block : blocks) {
            if (block.getX() == x && y == block.getY()) {
                blocks.remove(block);
                break;
            }
        }
    }

    public void addAbilityInstance(AbstractAbilityInstance abilityInstance) {
        abilityInstances.add(abilityInstance);
    }

    public void clearAbilityInstances() {
        abilityInstances.clear();
    }

    public HashSet<AbstractAbilityInstance> getAbilityInstances() {
        return abilityInstances;
    }
}
