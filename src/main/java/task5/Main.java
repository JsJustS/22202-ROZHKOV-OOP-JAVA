package task3;

import task3.controller.ClientController;
import task3.model.GameModel;
import task3.service.engine.GameEngine;
import task3.util.ArgParser;
import task3.util.Config;
import task3.view.MainWindow;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        ArgParser parser = new ArgParser(args);
        Config cfg = (parser.length() == 0) ? Config.GENERAL : Config.load(parser.get(0).getAsString());

        GameModel gameModel = new GameModel();
        ClientController clientController = new ClientController();

        SwingUtilities.invokeLater(() -> {
            MainWindow mainWindow = new MainWindow(clientController, gameModel, cfg);
        });

        GameEngine gameEngine = new GameEngine(cfg, gameModel);
        gameEngine.start();
    }
}
