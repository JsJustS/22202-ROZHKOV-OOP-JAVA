package task3.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task3.controller.ClientController;
import task3.controller.NetworkC2SController;
import task3.engine.ability.DestroyBlockAbilityInstance;
import task3.model.ClientModel;
import task3.util.Config;
import task3.util.Pair;
import task3.util.keyboard.KeyBindManager;
import task3.view.gameplay.MainGameplayWindow;
import task3.view.menu.MainMenu;
import task3.util.pubsub.ISubscriber;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class MainWindow extends JFrame implements ISubscriber {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainWindow.class);
    private final NetworkC2SController network;
    private final ClientController controller;
    private final ClientModel model;

    public MainWindow(ClientController controller, ClientModel model, NetworkC2SController network, Config cfg) {
        this.controller = controller;
        this.model = model;
        this.network = network;
        model.subscribe(this);

        this.setSize(new Dimension(cfg.getWinWidth(), cfg.getWinHeight()));
        this.setTitle(cfg.getGameTitle());

        this.getContentPane().add(new MainMenu(this, controller, model));

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.initKeyBinds(cfg);
    }

    public void initKeyBinds(Config cfg) {
        controller.execute(
                ClientController.OP.UPDATE_KEYBINDS, model,
                new Pair<>(cfg.getAbilityKey(), KeyBindManager.KeyAction.USE_ABILITY)
        );
    }

    @Override
    public void onNotification() {
        if (model.isGameStateDirty()) {
            changeClientState();
        }

        this.handleKeyActions();
    }

    public void handleKeyActions() {
        for (KeyBindManager.KeyAction keyAction : model.getPressedKeys()) {
            switch (keyAction) {
                case USE_ABILITY:
                    //todo: ability manager for client
                    Random random = new Random();
                    int x = random.nextInt(model.getFieldWidthInBlocks());
                    int y = random.nextInt(model.getFieldHeightInBlocks());
                    System.out.println("handleKeyActions " + x + " " + y);
                    network.execute(
                            NetworkC2SController.PacketType.PLAYER_ABILITY_USED,
                            new int[]{
                                    x,
                                    y
                            }
                    );
                    break;
                case MOVE_UP:
                    break;
                case MOVE_DOWN:
                    break;
                case MOVE_RIGHT:
                    break;
                case MOVE_LEFT:
                    break;
                case LEAVE:
                    break;
            }
        }
    }

    private void changeClientState() {
        this.getContentPane().removeAll();
        switch (model.getGameState()) {
            case MENU: {
                this.getContentPane().add(new MainMenu(this, controller, model));
                break;
            }
            case INGAME: {
                this.getContentPane().add(new MainGameplayWindow(this, controller, model));
                break;
            }
            case PAUSE: {
                //todo: this.getContentPane().add(new MainPauseWindow(this, controller, model));
                break;
            }
            default: {
                LOGGER.error("Unsupported GameState change");
            }
        }
        model.setGameStateDirty(false);
        this.revalidate();
        this.repaint();
    }
}
