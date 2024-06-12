package task3.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task3.controller.ClientController;
import task3.controller.PlayerController;
import task3.model.GameModel;
import task3.model.abilityInstance.Ability;
import task3.model.entity.Direction;
import task3.util.Config;
import task3.util.Pair;
import task3.util.keyboard.KeyBindManager;
import task3.view.gameplay.MainGameplayWindow;
import task3.view.menu.MainMenu;
import task3.util.pubsub.ISubscriber;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class MainWindow extends JFrame implements ISubscriber {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainWindow.class);
    private final ClientController controller;
    private final GameModel model;
    private final PlayerController playerController;

    public MainWindow(ClientController controller, GameModel model, Config cfg) {
        this.controller = controller;
        this.model = model;
        model.subscribe(this);

        this.setSize(new Dimension(cfg.getWinWidth(), cfg.getWinHeight()));
        this.setTitle(cfg.getGameTitle());

        this.getContentPane().add(new MainMenu(this, controller, model));

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.initKeyBinds(cfg);
        this.playerController = new PlayerController();
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
        controller.execute(
                ClientController.OP.UPDATE_KEYBINDS, model,
                new Pair<>(cfg.getLeaveKey(), KeyBindManager.KeyAction.LEAVE)
        );
    }

    @Override
    public void onNotification() {
        if (model.isGameStateDirty()) {
            model.setGameStateDirty(false);
            changeClientState();
        }

        this.handleKeyActions();
    }

    public void handleKeyActions() {
        Set<KeyBindManager.KeyAction> keysToHandle = new HashSet<>(model.getPressedKeys());
        model.clearPressedKeys();
        for (KeyBindManager.KeyAction keyAction : keysToHandle) {
            switch (keyAction) {
                case USE_ABILITY:
                    playerController.execute(
                            PlayerController.OP.USE_ABILITY, model,
                            model.getMainPlayer().getId()
                    );
                    break;
                case CHANGE_ABILITY:
                    int abilityOrdinal = (model.getMainPlayer().getAbility() == Ability.SIMPLE_BOMB) ?
                            Ability.SUPER_BOMB.ordinal() :
                            Ability.SIMPLE_BOMB.ordinal();
                    playerController.execute(
                            PlayerController.OP.CHANGE_ABILITY, model,
                            new int[]{model.getMainPlayer().getId(), abilityOrdinal}
                    );
                    break;
                case MOVE_UP:
                    playerController.execute(
                            PlayerController.OP.MOVE, model,
                            new int[]{model.getMainPlayer().getId(), Direction.UP.ordinal()}
                    );
                    break;
                case MOVE_DOWN:
                    playerController.execute(
                            PlayerController.OP.MOVE, model,
                            new int[]{model.getMainPlayer().getId(), Direction.DOWN.ordinal()}
                    );
                    break;
                case MOVE_RIGHT:
                    playerController.execute(
                            PlayerController.OP.MOVE, model,
                            new int[]{model.getMainPlayer().getId(), Direction.RIGHT.ordinal()}
                    );
                    break;
                case MOVE_LEFT:
                    playerController.execute(
                            PlayerController.OP.MOVE, model,
                            new int[]{model.getMainPlayer().getId(), Direction.LEFT.ordinal()}
                    );
                    break;
                case LEAVE:
                    controller.execute(
                            ClientController.OP.CHANGE_GAMESTATE, model,
                            GameModel.GAMESTATE.MENU
                    );
                    break;
                default: {
                    LOGGER.error(String.format("Unsupported KeyAction \"%s:pressed\"", keyAction.name()));
                }
            }
        }

        keysToHandle = new HashSet<>(model.getReleasedKeys());
        model.clearReleasedKeys();
        for (KeyBindManager.KeyAction keyAction : keysToHandle) {
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
                    playerController.execute(
                            PlayerController.OP.MOVE, model,
                            new int[]{model.getMainPlayer().getId(), -1}
                    );
                    break;
                default: {
                    LOGGER.error(String.format("Unsupported KeyAction \"%s:released\"", keyAction.name()));
                }
            }
        }
    }

    private void changeClientState() {
        this.getContentPane().removeAll();
        switch (model.getGameState()) {
            case MENU: {
                this.getContentPane().removeAll();
                this.getContentPane().add(new MainMenu(this, controller, model));
                playerController.execute(
                        PlayerController.OP.LEAVE, model,
                        null
                );
                break;
            }
            case INGAME: {
                this.getContentPane().removeAll();
                this.getContentPane().add(new MainGameplayWindow(this, controller, model));
                playerController.execute(
                        PlayerController.OP.JOIN, model,
                        null
                );
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
