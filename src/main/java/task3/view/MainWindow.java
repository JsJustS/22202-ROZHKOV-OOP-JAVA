package task3.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task3.controller.ClientController;
import task3.model.ClientModel;
import task3.util.Config;
import task3.view.gameplay.MainGameplayWindow;
import task3.view.menu.MainMenu;
import task3.util.pubsub.ISubscriber;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame implements ISubscriber {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainWindow.class);
    ClientController controller;
    ClientModel model;

    public MainWindow(ClientController controller, ClientModel model, Config cfg) {
        this.controller = controller;
        this.model = model;
        model.subscribe(this);

        this.setSize(new Dimension(cfg.getWinWidth(), cfg.getWinHeight()));
        this.setTitle(cfg.getGameTitle());

        //this.addKeyListener(new KeyboardHandler(this.controller, this.model));

        this.getContentPane().add(new MainMenu(this, controller, model));

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void onNotification() {
        if (model.isGameStateDirty()) {
            changeClientState();
        }
    }

    void changeClientState() {
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
