package task5.client.controller;

import task5.controller.IController;
import task5.model.GameModel;
import task5.util.Pair;
import task5.client.keyboard.KeyBindManager;

public class ClientController implements IController<ClientController.OP, GameModel> {

    public enum OP {
        SET_IP,
        ON_KEY_PRESSED,
        ON_KEY_RELEASED,
        CHANGE_GAMESTATE,
        UPDATE_KEYBINDS
    }

    @Override
    public <T> void execute(OP operation, GameModel clientModel, T value) {
        switch (operation) {
            case SET_IP: {
                Pair<String, Integer> address = (Pair<String, Integer>) value;
                clientModel.setHostName(address.getFirst());
                clientModel.setPort(address.getSecond());
                break;
            }
            case CHANGE_GAMESTATE: {
                GameModel.GAMESTATE state = (GameModel.GAMESTATE) value;
                clientModel.setGameState(state);
                break;
            }
            case UPDATE_KEYBINDS: {
                Pair<String, KeyBindManager.KeyAction> pair = (Pair<String, KeyBindManager.KeyAction>) value;
                clientModel.setKeyBind(pair.getFirst(), pair.getSecond());
                break;
            }
            case ON_KEY_PRESSED: {
                KeyBindManager.KeyAction keyAction = (KeyBindManager.KeyAction) value;
                clientModel.setKeyPressed(keyAction);
                break;
            }
            case ON_KEY_RELEASED: {
                KeyBindManager.KeyAction keyAction = (KeyBindManager.KeyAction) value;
                clientModel.setKeyReleased(keyAction);
                break;
            }
        }
    }
}
