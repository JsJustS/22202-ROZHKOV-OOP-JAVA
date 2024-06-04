package task3.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task3.controller.ClientController;
import task3.controller.NetworkC2SController;
import task3.engine.entity.PlayerEntity;
import task3.model.ClientModel;
import task3.util.Config;
import task3.util.Pair;
import task3.util.keyboard.KeyBindManager;
import task3.view.gameplay.MainGameplayWindow;
import task3.view.menu.MainMenu;
import task3.util.pubsub.ISubscriber;

import javax.swing.*;
import java.awt.*;

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
        controller.execute(
                ClientController.OP.UPDATE_KEYBINDS, model,
                new Pair<>(cfg.getMoveUpKey(), KeyBindManager.KeyAction.MOVE_UP)
        );
        controller.execute(
                ClientController.OP.UPDATE_KEYBINDS, model,
                new Pair<>(cfg.getMoveDownKey(), KeyBindManager.KeyAction.MOVE_DOWN)
        );
        controller.execute(
                ClientController.OP.UPDATE_KEYBINDS, model,
                new Pair<>(cfg.getMoveLeftKey(), KeyBindManager.KeyAction.MOVE_LEFT)
        );
        controller.execute(
                ClientController.OP.UPDATE_KEYBINDS, model,
                new Pair<>(cfg.getMoveRightKey(), KeyBindManager.KeyAction.MOVE_RIGHT)
        );
        controller.execute(
                ClientController.OP.UPDATE_KEYBINDS, model,
                new Pair<>(cfg.getChangeAbilityKey(), KeyBindManager.KeyAction.CHANGE_ABILITY)
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
                    network.execute(
                            NetworkC2SController.PacketType.PLAYER_ABILITY_USED,
                            new int[]{model.getMainPlayer().getId(), model.getChosenAbility().ordinal()}
                    );
                    break;
                case CHANGE_ABILITY:
                    model.setChosenAbility(
                            PlayerEntity.Abilities.values()[
                                    (model.getChosenAbility().ordinal() + 1) % PlayerEntity.Abilities.values().length
                                    ]
                    );
                    break;
                case MOVE_UP:
                    network.execute(
                            NetworkC2SController.PacketType.PLAYER_MOVED,
                            new int[]{model.getMainPlayer().getId(), PlayerEntity.Direction.UP.ordinal()+1}
                    );
                    break;
                case MOVE_DOWN:
                    network.execute(
                            NetworkC2SController.PacketType.PLAYER_MOVED,
                            new int[]{model.getMainPlayer().getId(), PlayerEntity.Direction.DOWN.ordinal()+1}
                    );
                    break;
                case MOVE_RIGHT:
                    network.execute(
                            NetworkC2SController.PacketType.PLAYER_MOVED,
                            new int[]{model.getMainPlayer().getId(), PlayerEntity.Direction.RIGHT.ordinal()+1}
                    );
                    break;
                case MOVE_LEFT:
                    network.execute(
                            NetworkC2SController.PacketType.PLAYER_MOVED,
                            new int[]{model.getMainPlayer().getId(), PlayerEntity.Direction.LEFT.ordinal()+1}
                    );
                    break;
                case LEAVE:
                    network.execute(
                            NetworkC2SController.PacketType.PLAYER_LEFT,
                            model.getMainPlayer().getId()
                    );
                    break;
            }
        }
        model.clearPressedKeys();

        for (KeyBindManager.KeyAction keyAction : model.getReleasedKeys()) {
            switch (keyAction) {
                case USE_ABILITY:
                    break;
                case CHANGE_ABILITY:
                    break;
                case LEAVE:
                    break;
                case MOVE_UP:
                case MOVE_DOWN:
                case MOVE_RIGHT:
                case MOVE_LEFT:
                    network.execute(
                            NetworkC2SController.PacketType.PLAYER_MOVED,
                            new int[]{model.getMainPlayer().getId(), 0}
                    );
                    break;
            }
        }
        model.clearReleasedKeys();
    }

    private void changeClientState() {
        this.getContentPane().removeAll();
        switch (model.getGameState()) {
            case MENU: {
                this.getContentPane().add(new MainMenu(this, controller, model));
                this.network.execute(NetworkC2SController.PacketType.PLAYER_LEFT, null);
                break;
            }
            case INGAME: {
                this.getContentPane().add(new MainGameplayWindow(this, controller, model));
                this.network.execute(NetworkC2SController.PacketType.PLAYER_JOINED, null);
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
