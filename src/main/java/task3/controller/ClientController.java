package task3.controller;

import task3.model.GameModel;
import task3.util.Pair;
import task3.util.keyboard.KeyBindManager;

public class ClientController implements IController<ClientController.OP, GameModel> {

    public enum OP {
        SET_SEED,
        ON_KEY_PRESSED,
        ON_KEY_RELEASED,
        CHANGE_GAMESTATE,
        UPDATE_KEYBINDS
    }

    @Override
    public <T> void execute(OP operation, GameModel model, T value) {
        switch (operation) {
            case SET_SEED: {
                long seed = (long) value;
                model.setCurrentSeed(seed);
                break;
            }
            case CHANGE_GAMESTATE: {
                GameModel.GAMESTATE state = (GameModel.GAMESTATE) value;
                model.setGameState(state);
                break;
            }
            case UPDATE_KEYBINDS: {
                Pair<String, KeyBindManager.KeyAction> pair = (Pair<String, KeyBindManager.KeyAction>) value;
                model.setKeyBind(pair.getFirst(), pair.getSecond());
                break;
            }
            case ON_KEY_PRESSED: {
                KeyBindManager.KeyAction keyAction = (KeyBindManager.KeyAction) value;
                model.setKeyPressed(keyAction);
                break;
            }
            case ON_KEY_RELEASED: {
                KeyBindManager.KeyAction keyAction = (KeyBindManager.KeyAction) value;
                model.setKeyReleased(keyAction);
                break;
            }
        }
    }
}
