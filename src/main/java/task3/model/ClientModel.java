package task3.model;

import task3.engine.block.Block;
import task3.engine.block.BlockRegistry;
import task3.engine.entity.ClientEntity;
import task3.util.pubsub.Publisher;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ClientModel extends Publisher implements IModel {
    public enum GAMESTATE {
        MENU,
        PAUSE,
        INGAME
    }

    private final HashMap<String, Runnable> keyBinds = new HashMap<>();

    private boolean flagMapReady = false;

    private GAMESTATE gameState;
    private boolean gameStateMark;

    //todo: get that from network
    private int fieldWidthInBlocks;
    private int fieldHeightInBlocks;

    private final HashSet<ClientEntity> clientEntities = new HashSet<>();
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

    public void setKeyBind(String key, Runnable bind) {
        keyBinds.put(key, bind);
    }

    public Map<String, Runnable> getKeyBinds() {
        return keyBinds;
    }

    public GAMESTATE getGameState() {
        return gameState;
    }

    public boolean isGameStateDirty() {
        return gameStateMark;
    }

    public void addBlock(int x, int y, int id) {
        Block block = BlockRegistry.getBlockById(x, y, id);
        if (block == null) return;
        blocks.add(block);
    }

    public void removeBlock(int x, int y) {
        for (Block block : blocks) {
            if (block.getX() == x && block.getY() == block.getY()) {
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
