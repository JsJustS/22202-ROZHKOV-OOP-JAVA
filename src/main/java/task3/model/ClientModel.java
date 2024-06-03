package task3.model;

import task3.engine.block.Block;
import task3.engine.block.BlockRegistry;
import task3.engine.entity.Entity;
import task3.engine.entity.PlayerEntity;
import task3.util.keyboard.KeyBindManager;
import task3.util.pubsub.Publisher;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientModel extends Publisher implements IModel {
    public enum GAMESTATE {
        MENU,
        INGAME
    }

    private final Map<String, KeyBindManager.KeyAction> keyBinds = new HashMap<>();
    private final Map<KeyBindManager.KeyAction, Boolean> keysPressed = new HashMap<>();

    private boolean flagMapReady = false;

    private GAMESTATE gameState;
    private boolean gameStateMark;

    private int fieldWidthInBlocks;
    private int fieldHeightInBlocks;

    private PlayerEntity mainPlayer;
    private final HashSet<Entity> clientEntities = new HashSet<>();
    private final HashSet<Block> blocks = new HashSet<>();

    public void setGameState(GAMESTATE value) {
        gameState = value;
        gameStateMark = true;
        notifySubscribers();
    }

    public void setGameStateDirty(boolean gameStateMark) {
        this.gameStateMark = gameStateMark;
        notifySubscribers();
    }

    public void setKeyBind(String key, KeyBindManager.KeyAction bind) {
        keyBinds.put(key.toUpperCase(), bind);
    }

    public Map<String, KeyBindManager.KeyAction> getKeyBinds() {
        return keyBinds;
    }

    public Set<KeyBindManager.KeyAction> getPressedKeys() {
        return this.keysPressed.keySet().stream().filter(this.keysPressed::get).collect(Collectors.toSet());
    }

    public boolean isKeyPressed(KeyBindManager.KeyAction keyAction) {
        return this.keysPressed.get(keyAction);
    }

    public void setKeyPressed(KeyBindManager.KeyAction keyAction, boolean value) {
        this.keysPressed.put(keyAction, value);
        notifySubscribers();
    }

    public GAMESTATE getGameState() {
        return gameState;
    }

    public boolean isGameStateDirty() {
        return gameStateMark;
    }

    public PlayerEntity getMainPlayer() {
        return mainPlayer;
    }

    public void setMainPlayer(PlayerEntity mainPlayer) {
        this.mainPlayer = mainPlayer;
    }

    public HashSet<Entity> getClientEntities() {
        return clientEntities;
    }

    public void spawnEntity(Entity entity) {
        this.clientEntities.add(entity);
    }

    public void removeEntity(Entity entity) {
        this.clientEntities.remove(entity);
    }
    public void removeEntity(int id) {
        for (Entity entity : clientEntities) {
            if (entity.getId() == id) {
                clientEntities.remove(entity);
                break;
            }
        }
    }

    public Entity getEntity(int id) {
        for (Entity entity : clientEntities) {
            if (entity.getId() == id) {
                return entity;
            }
        }
        return null;
    }

    public void addBlock(int x, int y, int id) {
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

    public HashSet<Block> getBlocks() {
        return blocks;
    }

    public int getFieldWidthInBlocks() {return fieldWidthInBlocks;}
    public int getFieldHeightInBlocks() {return fieldHeightInBlocks;}

    public void setFieldHeightInBlocks(int fieldHeightInBlocks) {
        this.fieldHeightInBlocks = fieldHeightInBlocks;
    }

    public void setFieldWidthInBlocks(int fieldWidthInBlocks) {
        this.fieldWidthInBlocks = fieldWidthInBlocks;
    }

    public boolean isMapReady() {
        return flagMapReady;
    }

    public void setMapReady(boolean flagMapReady) {
        this.flagMapReady = flagMapReady;
    }
}
