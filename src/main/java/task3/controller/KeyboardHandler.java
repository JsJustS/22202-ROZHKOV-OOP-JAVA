package task3.controller;

import task3.model.ClientModel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

/**
 * Intermediate class between SWING KeyListener and custom client controller
 * */
public class KeyboardHandler implements KeyListener {
    private final ClientController clientController;
    private final ClientModel clientModel;
    private final HashMap<String, Boolean> PRESSED_KEYS;

    public KeyboardHandler(ClientController clientController, ClientModel clientModel) {
        this.clientController = clientController;
        this.clientModel = clientModel;
        this.PRESSED_KEYS = new HashMap<>();
    }

    public boolean isPressed(String keyText) {
        return PRESSED_KEYS.getOrDefault(keyText.toLowerCase(), false);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //clientController.execute(ClientController.OP.ON_KEY_TYPED, e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        clientController.execute(ClientController.OP.ON_KEY_PRESSED, clientModel, e);
        PRESSED_KEYS.put(KeyEvent.getKeyText(e.getKeyCode()), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        clientController.execute(ClientController.OP.ON_KEY_RELEASED, clientModel, e);
        PRESSED_KEYS.put(KeyEvent.getKeyText(e.getKeyCode()), false);
    }
}
