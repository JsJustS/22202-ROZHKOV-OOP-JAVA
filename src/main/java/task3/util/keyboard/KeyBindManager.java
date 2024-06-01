package task3.util.keyboard;

import task3.controller.ClientController;
import task3.model.ClientModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Intermediate class between SWING KeyStrokes and custom client controller
 * */
public class KeyBindManager {
    private final ClientController clientController;
    private final ClientModel clientModel;
    private final JPanel parent;

    public enum KeyAction {
        USE_ABILITY,
        SWAP_ABILITY,
        MOVE_UP,
        MOVE_DOWN,
        MOVE_RIGHT,
        MOVE_LEFT,
        LEAVE
    }

    public KeyBindManager(JPanel parent, ClientController clientController, ClientModel clientModel) {
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
            System.out.println(e.getActionCommand());
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
            setKeyBind(KeyStroke.getKeyStroke(keyName.toUpperCase()).getKeyCode());
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
