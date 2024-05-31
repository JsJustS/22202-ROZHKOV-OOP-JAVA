package task3.model;

import task3.view.pubsub.Publisher;

public class ClientModel extends Publisher implements IModel {
    public enum GAMESTATE {
        MENU,
        PAUSE,
        INGAME
    }

    private GAMESTATE gameState;
    private boolean gameStateMark;

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
}
