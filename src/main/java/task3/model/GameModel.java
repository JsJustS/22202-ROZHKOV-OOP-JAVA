package task3.model;

import task3.model.abilityInstance.AbstractAbilityInstanceModel;
import task3.model.entity.EntityModel;
import task3.model.entity.PlayerEntityModel;
import task3.util.keyboard.KeyBindManager;
import task3.util.pubsub.Publisher;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class GameModel extends Publisher implements IModel {
    private int fieldWidthInBlocks;
    private int fieldHeightInBlocks;
    private byte[][] botMap;
    private int pointsForWin;
    private long currentSeed;

    public byte[][] getBotMap() {
        return botMap;
    }

    public void setBotMap(byte[][] botMap) {
        this.botMap = botMap;
    }

    private boolean gameIsRunning;

    private int lastEntityId;
    private final Set<EntityModel> entities = new CopyOnWriteArraySet<>();
    private final Set<AbstractAbilityInstanceModel> abilityInstances = new CopyOnWriteArraySet<>();

    private boolean flagMapReady = false;

    public PlayerEntityModel getMainPlayer() {
        return mainPlayer;
    }

    public void setMainPlayer(PlayerEntityModel mainPlayer) {
        this.mainPlayer = mainPlayer;
    }

    private PlayerEntityModel mainPlayer = null;
    private boolean hasPlayerJoined;

    public void setPlayerJoined(boolean value) {
        this.hasPlayerJoined = value;
        notifySubscribers();
    }

    public long getCurrentSeed() {
        return currentSeed;
    }

    public void setCurrentSeed(long currentSeed) {
        this.currentSeed = currentSeed;
    }

    public boolean hasPlayer() {
        return hasPlayerJoined;
    }

    public boolean isMapReady() {
        return flagMapReady;
    }

    public void setMapReady(boolean flagMapReady) {
        this.flagMapReady = flagMapReady;
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

    public int getFieldWidthInBlocks() {
        return fieldWidthInBlocks;
    }

    public int getLastEntityId() {
        return lastEntityId;
    }

    public void setLastEntityId(int value) {
        this.lastEntityId = value;
    }
    public Set<EntityModel> getEntities() {
        return entities;
    }

    public void setFieldHeightInBlocks(int fieldHeightInBlocks) {
        this.fieldHeightInBlocks = fieldHeightInBlocks;
    }

    public void setFieldWidthInBlocks(int fieldWidthInBlocks) {
        this.fieldWidthInBlocks = fieldWidthInBlocks;
    }


    public void addEntity(EntityModel entity) {
        this.entities.add(entity);
    }

    public void removeEntity(EntityModel entity) {
        this.entities.remove(entity);
    }

    public void addAbilityInstance(AbstractAbilityInstanceModel abilityInstance) {
        abilityInstances.add(abilityInstance);
    }

    public void clearAbilityInstances() {
        abilityInstances.clear();
    }

    public void clearEntities() {
        entities.clear();
    }

    public Set<AbstractAbilityInstanceModel> getAbilityInstances() {
        return abilityInstances;
    }

    public void removeAbilityInstance(AbstractAbilityInstanceModel abilityInstance) {
        abilityInstances.remove(abilityInstance);
    }


    public enum GAMESTATE {
        MENU,
        INGAME
    }
    private GAMESTATE gameState;
    private boolean gameStateMark;

    private final Map<String, KeyBindManager.KeyAction> keyBinds = new HashMap<>();
    private final Set<KeyBindManager.KeyAction> keysPressed = new HashSet<>();
    private final Set<KeyBindManager.KeyAction> keysReleased = new HashSet<>();

    public void setGameState(GAMESTATE value) {
        gameState = value;
        this.setGameStateDirty(true);
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

    public void setKeyBind(String key, KeyBindManager.KeyAction bind) {
        keyBinds.put(key.toUpperCase(), bind);
    }

    public Map<String, KeyBindManager.KeyAction> getKeyBinds() {
        return keyBinds;
    }

    public Set<KeyBindManager.KeyAction> getPressedKeys() {
        return this.keysPressed;
    }

    public Set<KeyBindManager.KeyAction> getReleasedKeys() {
        return this.keysReleased;
    }

    public boolean isKeyPressed(KeyBindManager.KeyAction keyAction) {
        return this.keysPressed.contains(keyAction);
    }

    public boolean isKeyReleased(KeyBindManager.KeyAction keyAction) {
        return this.keysReleased.contains(keyAction);
    }

    public void setKeyPressed(KeyBindManager.KeyAction keyAction) {
        this.keysPressed.add(keyAction);
        notifySubscribers();
    }

    public void setKeyReleased(KeyBindManager.KeyAction keyAction) {
        this.keysReleased.add(keyAction);
        notifySubscribers();
    }

    public void clearPressedKeys() {
        this.keysPressed.clear();
    }

    public void clearReleasedKeys() {
        this.keysReleased.clear();
    }

    public int getPointsForWin() {
        return pointsForWin;
    }

    public void setPointsForWin(int pointsForWin) {
        this.pointsForWin = pointsForWin;
    }
}
