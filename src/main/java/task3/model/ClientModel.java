package task3.model;

import task3.engine.block.Block;
import task3.engine.block.BlockRegistry;
import task3.engine.entity.ClientEntity;
import task3.view.pubsub.Publisher;

import java.util.ArrayList;
import java.util.HashSet;

public class ClientModel extends Publisher implements IModel {
    public enum GAMESTATE {
        MENU,
        PAUSE,
        INGAME
    }

    private GAMESTATE gameState;
    private boolean gameStateMark;

    //todo: get that from network
    private int fieldWidthInBlocks = 10;
    private int fieldHeightInBlocks = 7;

    private HashSet<ClientEntity> clientEntities = new HashSet<>();
    private HashSet<Block> blocks = new HashSet<>();

    public void setGameState(GAMESTATE value) {
        gameState = value;
        gameStateMark = true;
        notifySubscribers();
    }

    public void setGameStateDirty(boolean gameStateMark) {
        this.gameStateMark = gameStateMark;
        notifySubscribers();
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
}
