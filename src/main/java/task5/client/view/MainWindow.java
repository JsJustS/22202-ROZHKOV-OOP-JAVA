package task5.client.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task5.client.SocketClient;
import task5.client.controller.ClientController;
import task5.model.GameModel;
import task5.model.abilityInstance.Ability;
import task5.model.entity.Direction;
import task5.util.Config;
import task5.util.Pair;
import task5.client.keyboard.KeyBindManager;
import task5.client.view.gameplay.MainGameplayWindow;
import task5.client.view.menu.MainMenu;
import task5.util.network.c2s.*;
import task5.util.pubsub.ISubscriber;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class MainWindow extends JFrame implements ISubscriber {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainWindow.class);
    private final ClientController controller;
    private final GameModel clientModel;
    //private final PlayerController playerController;
    private final SocketClient clientNetwork;

    public MainWindow(ClientController controller, GameModel clientModel, Config cfg) {
        this.controller = controller;
        this.clientModel = clientModel;
        clientModel.subscribe(this);

        this.setSize(new Dimension(cfg.getWinWidth(), cfg.getWinHeight()));
        this.setTitle(cfg.getGameTitle());

        clientModel.setCurrentSeed(cfg.getSeed());
        this.getContentPane().add(new MainMenu(this, controller, clientModel));

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.initKeyBinds(cfg);
        //this.playerController = new PlayerController();
        this.clientNetwork = new SocketClient(clientModel);
    }

    public void initKeyBinds(Config cfg) {
        controller.execute(
                ClientController.OP.UPDATE_KEYBINDS, clientModel,
                new Pair<>(cfg.getAbilityKey(), KeyBindManager.KeyAction.USE_ABILITY)
        );
        controller.execute(
                ClientController.OP.UPDATE_KEYBINDS, clientModel,
                new Pair<>(cfg.getMoveUpKey(), KeyBindManager.KeyAction.MOVE_UP)
        );
        controller.execute(
                ClientController.OP.UPDATE_KEYBINDS, clientModel,
                new Pair<>(cfg.getMoveDownKey(), KeyBindManager.KeyAction.MOVE_DOWN)
        );
        controller.execute(
                ClientController.OP.UPDATE_KEYBINDS, clientModel,
                new Pair<>(cfg.getMoveLeftKey(), KeyBindManager.KeyAction.MOVE_LEFT)
        );
        controller.execute(
                ClientController.OP.UPDATE_KEYBINDS, clientModel,
                new Pair<>(cfg.getMoveRightKey(), KeyBindManager.KeyAction.MOVE_RIGHT)
        );
        controller.execute(
                ClientController.OP.UPDATE_KEYBINDS, clientModel,
                new Pair<>(cfg.getChangeAbilityKey(), KeyBindManager.KeyAction.CHANGE_ABILITY)
        );
        controller.execute(
                ClientController.OP.UPDATE_KEYBINDS, clientModel,
                new Pair<>(cfg.getLeaveKey(), KeyBindManager.KeyAction.LEAVE)
        );
    }

    @Override
    public void onNotification() {
        if (clientModel.isGameStateDirty()) {
            clientModel.setGameStateDirty(false);
            changeClientState();
        }

        this.handleKeyActions();
    }

    public void handleKeyActions() {
        Set<KeyBindManager.KeyAction> keysToHandle = new HashSet<>(clientModel.getPressedKeys());
        clientModel.clearPressedKeys();
        for (KeyBindManager.KeyAction keyAction : keysToHandle) {
            switch (keyAction) {
                case USE_ABILITY:
                    /*playerController.execute(
                            PlayerController.OP.USE_ABILITY, clientModel,
                            clientModel.getMainPlayer().getId()
                    );*/
                    clientNetwork.sendPacket(
                            new PlayerAbilityUseC2SPacket(clientModel.getMainPlayer().getId())
                    );
                    break;
                case CHANGE_ABILITY:
                    int abilityOrdinal = (clientModel.getMainPlayer().getAbility() == Ability.SIMPLE_BOMB) ?
                            Ability.SUPER_BOMB.ordinal() :
                            Ability.SIMPLE_BOMB.ordinal();
                    /*playerController.execute(
                            PlayerController.OP.CHANGE_ABILITY, clientModel,
                            new int[]{clientModel.getMainPlayer().getId(), abilityOrdinal}
                    );*/
                    clientNetwork.sendPacket(
                            new PlayerAbilityChangeC2SPacket(clientModel.getMainPlayer().getId(), abilityOrdinal)
                    );
                    break;
                case MOVE_UP:
                    /*playerController.execute(
                            PlayerController.OP.MOVE, clientModel,
                            new int[]{clientModel.getMainPlayer().getId(), Direction.UP.ordinal()}
                    );*/
                    clientNetwork.sendPacket(
                            new PlayerMoveC2SPacket(clientModel.getMainPlayer().getId(), Direction.UP, true)
                    );
                    break;
                case MOVE_DOWN:
                    /*playerController.execute(
                            PlayerController.OP.MOVE, clientModel,
                            new int[]{clientModel.getMainPlayer().getId(), Direction.DOWN.ordinal()}
                    );*/
                    clientNetwork.sendPacket(
                            new PlayerMoveC2SPacket(clientModel.getMainPlayer().getId(), Direction.DOWN, true)
                    );
                    break;
                case MOVE_RIGHT:
                    /*playerController.execute(
                            PlayerController.OP.MOVE, clientModel,
                            new int[]{clientModel.getMainPlayer().getId(), Direction.RIGHT.ordinal()}
                    );*/
                    clientNetwork.sendPacket(
                            new PlayerMoveC2SPacket(clientModel.getMainPlayer().getId(), Direction.RIGHT, true)
                    );
                    break;
                case MOVE_LEFT:
                    /*playerController.execute(
                            PlayerController.OP.MOVE, clientModel,
                            new int[]{clientModel.getMainPlayer().getId(), Direction.LEFT.ordinal()}
                    );*/
                    clientNetwork.sendPacket(
                            new PlayerMoveC2SPacket(clientModel.getMainPlayer().getId(), Direction.LEFT, true)
                    );
                    break;
                case LEAVE:
                    controller.execute(
                            ClientController.OP.CHANGE_GAMESTATE, clientModel,
                            GameModel.GAMESTATE.MENU
                    );
                    break;
                default: {
                    LOGGER.error(String.format("Unsupported KeyAction \"%s:pressed\"", keyAction.name()));
                }
            }
        }

        keysToHandle = new HashSet<>(clientModel.getReleasedKeys());
        clientModel.clearReleasedKeys();
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
                    /*playerController.execute(
                            PlayerController.OP.MOVE, clientModel,
                            new int[]{clientModel.getMainPlayer().getId(), -1}
                    );*/
                    clientNetwork.sendPacket(
                            new PlayerMoveC2SPacket(clientModel.getMainPlayer().getId(), Direction.DOWN, false)
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
        switch (clientModel.getGameState()) {
            case MENU: {
                this.clientNetwork.disconnect();
                this.getContentPane().removeAll();
                this.getContentPane().add(new MainMenu(this, controller, clientModel));
                /*playerController.execute(
                        PlayerController.OP.LEAVE, clientModel,
                        null
                );*/
                break;
            }
            case INGAME: {
                try {
                    this.clientNetwork.connect();
                    this.clientNetwork.startS2CHandler();
                    this.getContentPane().removeAll();
                    this.getContentPane().add(new MainGameplayWindow(this, controller, clientModel));
                } catch (IOException e) {
                    LOGGER.error("Could not connect to server ("+ clientModel.getHostAddress() + ":" + clientModel.getPort() +") : " + e.getMessage());
                    clientModel.setGameState(GameModel.GAMESTATE.MENU);
                }
                /*playerController.execute(
                        PlayerController.OP.JOIN, clientModel,
                        null
                );*/
                break;
            }
            default: {
                LOGGER.error("Unsupported GameState change");
            }
        }
        clientModel.setGameStateDirty(false);
        this.revalidate();
        this.repaint();
    }
}
