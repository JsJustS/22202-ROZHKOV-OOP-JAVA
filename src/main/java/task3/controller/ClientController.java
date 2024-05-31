package task3.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task3.model.ClientModel;

public class ClientController implements IController<ClientController.OP, ClientModel>{
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);

    public enum OP {
        ON_KEY_PRESSED,
        ON_KEY_RELEASED,
        CHANGE_GAMESTATE
    }
    boolean flag = false;

    @Override
    public <T> void execute(OP operation, ClientModel model, T value) {
        LOGGER.info(operation.name() + " | " + value.toString());

        switch (operation) {
            case CHANGE_GAMESTATE: {
                ClientModel.GAMESTATE state = (ClientModel.GAMESTATE) value;
                model.setGameState(state);
                break;
            }
            case ON_KEY_PRESSED: {
                //todo: remove
                if (!flag) {
                    model.addBlock(0, 1, 0);
                    flag = true;
                }
                break;
            }
            case ON_KEY_RELEASED: {
                //todo: remove
                model.removeBlock(0, 1);
                flag = false;
                break;
            }
        }
    }


}
