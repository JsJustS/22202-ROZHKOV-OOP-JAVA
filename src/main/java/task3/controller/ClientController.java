package task3.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task3.model.ClientModel;
import task3.util.Pair;
import task3.util.keyboard.KeyBindManager;

public class ClientController implements IController<ClientController.OP, ClientModel>{
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);

    public enum OP {
        ON_KEY_PRESSED,
        ON_KEY_RELEASED,
        CHANGE_GAMESTATE,
        UPDATE_KEYBINDS
    }

    @Override
    public <T> void execute(OP operation, ClientModel model, T value) {
        LOGGER.info(operation.name() + " | " + value.toString());

        switch (operation) {
            case CHANGE_GAMESTATE: {
                ClientModel.GAMESTATE state = (ClientModel.GAMESTATE) value;
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
                model.setKeyPressed(keyAction, true);
                break;
            }
            case ON_KEY_RELEASED: {
                KeyBindManager.KeyAction keyAction = (KeyBindManager.KeyAction) value;
                model.setKeyPressed(keyAction, false);
                break;
            }
        }
    }


}
