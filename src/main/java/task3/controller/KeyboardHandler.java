package task3.controller;

import task3.model.ClientModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

/**
 * Intermediate class between SWING KeyStrokes and custom client controller
 * */
public class KeyboardHandler {
    private final ClientController clientController;
    private final ClientModel clientModel;
    private final HashMap<String, Boolean> PRESSED_KEYS;
    private final JPanel parent;

    public KeyboardHandler(JPanel parent, ClientController clientController, ClientModel clientModel) {
        this.parent = parent;
        this.clientController = clientController;
        this.clientModel = clientModel;
        this.PRESSED_KEYS = new HashMap<>();
        this.setKeyBindings();
    }

    public boolean isPressed(String keyText) {
        return PRESSED_KEYS.getOrDefault(keyText.toLowerCase(), false);
    }

    private class KeyAction extends AbstractAction {
        public KeyAction(String actionCommand) {
            putValue(ACTION_COMMAND_KEY, actionCommand);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            System.out.println(e.getActionCommand());
            if (command.endsWith("_pressed")) {
                KeyboardHandler.this.clientController.execute(
                        ClientController.OP.ON_KEY_PRESSED,
                        KeyboardHandler.this.clientModel,
                        command.substring(0, command.indexOf("_pressed"))
                );
            } else {
                KeyboardHandler.this.clientController.execute(
                        ClientController.OP.ON_KEY_RELEASED,
                        KeyboardHandler.this.clientModel,
                        command.substring(0, command.indexOf("_released"))
                );
            }
        }
    }

    private void setKeyBindings() {
        ActionMap actionMap = parent.getActionMap();
        int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
        InputMap inputMap = parent.getInputMap(condition);

        addKeyBind(KeyEvent.VK_ESCAPE, inputMap, actionMap);
    }

    private void addKeyBind(int keyCode, InputMap inputMap, ActionMap actionMap) {
        String vkName = KeyEvent.getKeyText(keyCode);
        inputMap.put(KeyStroke.getKeyStroke(keyCode, 0, false), vkName+"_pressed");
        inputMap.put(KeyStroke.getKeyStroke(keyCode, 0, true), vkName+"_released");
        actionMap.put(vkName+"_pressed", new KeyAction(vkName+"_pressed"));
        actionMap.put(vkName+"_released", new KeyAction(vkName+"_released"));
    }
}
