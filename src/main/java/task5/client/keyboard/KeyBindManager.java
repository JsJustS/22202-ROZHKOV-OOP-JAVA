package task5.client.keyboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task5.client.controller.ClientController;
import task5.model.GameModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Intermediate class between SWING KeyStrokes and custom client controller
 * */
public class KeyBindManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(KeyBindManager.class);
    private final ClientController clientController;
    private final GameModel clientModel;
    private final JPanel parent;

    public enum KeyAction {
        USE_ABILITY,
        CHANGE_ABILITY,
        MOVE_UP,
        MOVE_DOWN,
        MOVE_RIGHT,
        MOVE_LEFT,
        LEAVE
    }

    public KeyBindManager(JPanel parent, ClientController clientController, GameModel clientModel) {
        this.parent = parent;
        this.clientController = clientController;
        this.clientModel = clientModel;
        this.setKeyBindings();
    }

    private class KeyBindAction extends AbstractAction {
        public KeyBindAction(String actionCommand) {
            putValue(ACTION_COMMAND_KEY, actionCommand);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.endsWith("_pressed")) {
                KeyBindManager.this.clientController.execute(
                        ClientController.OP.ON_KEY_PRESSED,
                        KeyBindManager.this.clientModel,
                        clientModel.getKeyBinds().get(command.substring(0, command.indexOf("_pressed")).toUpperCase())
                );
            } else {
                KeyBindManager.this.clientController.execute(
                        ClientController.OP.ON_KEY_RELEASED,
                        KeyBindManager.this.clientModel,
                        clientModel.getKeyBinds().get(command.substring(0, command.indexOf("_released")).toUpperCase())
                );
            }
        }
    }

    private void setKeyBindings() {
        for (String keyName : clientModel.getKeyBinds().keySet()) {
            KeyStroke stroke = KeyStroke.getKeyStroke(keyName.toUpperCase());
            if (stroke != null) {
                setKeyBind(stroke.getKeyCode());
            } else {
                LOGGER.warn("Could not set keybind for " + keyName.toUpperCase());
            }
        }
    }

    public void setKeyBind(int keyCode) {
        ActionMap actionMap = parent.getActionMap();
        int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
        InputMap inputMap = parent.getInputMap(condition);

        String vkName = KeyEvent.getKeyText(keyCode);
        inputMap.put(KeyStroke.getKeyStroke(keyCode, 0, false), vkName+"_pressed");
        inputMap.put(KeyStroke.getKeyStroke(keyCode, 0, true), vkName+"_released");
        actionMap.put(vkName+"_pressed", new KeyBindAction(vkName+"_pressed"));
        actionMap.put(vkName+"_released", new KeyBindAction(vkName+"_released"));
    }
}
