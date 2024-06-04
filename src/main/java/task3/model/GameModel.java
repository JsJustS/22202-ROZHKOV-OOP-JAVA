package task3.model;

import task3.engine.ability.AbstractAbilityInstance;
import task3.engine.block.Block;
import task3.engine.block.BlockRegistry;
import task3.engine.entity.Entity;
import task3.util.pubsub.Publisher;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class GameModel extends Publisher implements IModel {
    private int fieldWidthInBlock;
    private int fieldHeightInBlocks;
    private boolean gameIsRunning;

    private int lastEntityId;
    private final Set<Entity> entities = new CopyOnWriteArraySet<>();
    private final Set<Block> blocks = new CopyOnWriteArraySet<>();
    private final Set<AbstractAbilityInstance> abilityInstances = new CopyOnWriteArraySet<>();

    private boolean hasPlayerJoined;

    public void setHasPlayer(boolean value) {
        this.hasPlayerJoined = value;
        notifySubscribers();
    }

    public boolean hasPlayer() {
        return this.hasPlayerJoined;
    }

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

    public int getLastEntityId() {
        return lastEntityId;
    }

    public void setLastEntityId(int value) {
        this.lastEntityId = value;
    }
    public Set<Entity> getEntities() {
        return entities;
    }

    public Set<Block> getBlocks() {
        return blocks;
    }
    public Block getBlock(int x, int y) {
        for (Block block : blocks) {
            if (block.getX() == x && block.getY() == y) {
                return block;
            }
        }
        return null;
    }

    public void setFieldHeightInBlocks(int fieldHeightInBlocks) {
        this.fieldHeightInBlocks = fieldHeightInBlocks;
    }

    public void setFieldWidthInBlock(int fieldWidthInBlock) {
        this.fieldWidthInBlock = fieldWidthInBlock;
    }

    public Entity getEntity(int id) {
        for (Entity entity : entities) {
            if (entity.getId() == id) {
                return entity;
            }
        }
        return null;
    }

    public void spawnEntity(Entity entity) {
        this.entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        this.entities.remove(entity);
    }
    public void removeEntity(int id) {
        for (Entity entity : entities) {
            if (entity.getId() == id) {
                entities.remove(entity);
                break;
            }
        }
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

    public Set<AbstractAbilityInstance> getAbilityInstances() {
        return abilityInstances;
    }

    public void removeAbilityInstance(AbstractAbilityInstance abilityInstance) {
        abilityInstances.remove(abilityInstance);
    }
}
