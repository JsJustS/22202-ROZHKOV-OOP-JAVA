package task5;

import task5.client.controller.ClientController;
import task5.model.GameModel;
import task5.server.service.engine.GameEngine;
import task5.util.ArgParser;
import task5.util.Config;
import task5.client.view.MainWindow;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        ArgParser parser = new ArgParser(args);
        Config cfg = Config.GENERAL;
        if (parser.has("-c")) {
            ArgParser.Argument path = parser.getAfter("-c");
            if (path != null) {
                cfg = Config.load(path.getAsString());
            }
        }

        if (parser.has("-s")) {
            int port = 25500;

            ArgParser.Argument portArg = parser.getAfter("-s");
            if (portArg != null) {
                port = portArg.getAsInt();
            }

            createServer(cfg, port);
        } else {
            createClient(cfg);
        }
    }

    private static void createClient(Config cfg) {
        GameModel gameModel = new GameModel();
        ClientController clientController = new ClientController();
        SwingUtilities.invokeLater(() -> {
            MainWindow mainWindow = new MainWindow(clientController, gameModel, cfg);
        });
    }

    private static void createServer(Config cfg, int port) {
        GameModel gameModel = new GameModel();
        GameEngine gameEngine = new GameEngine(cfg, gameModel);
        gameEngine.start();
    }
}
