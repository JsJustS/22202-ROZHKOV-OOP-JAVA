package task3;

import task3.controller.ClientController;
import task3.controller.NetworkC2SController;
import task3.controller.NetworkS2CController;
import task3.engine.GameEngine;
import task3.model.ClientModel;
import task3.model.GameModel;
import task3.util.ArgParser;
import task3.util.Config;
import task3.view.MainWindow;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        ArgParser parser = new ArgParser(args);
        Config cfg = (parser.length() == 0) ? Config.GENERAL : Config.load(parser.get(0).getAsString());

        ClientModel clientModel = new ClientModel();
        GameModel gameModel = new GameModel();

        NetworkS2CController networkS2CController = new NetworkS2CController(clientModel);
        NetworkC2SController networkC2SController = new NetworkC2SController(gameModel);

        ClientController clientController = new ClientController(networkC2SController);
        SwingUtilities.invokeLater(() -> {
            MainWindow mainWindow = new MainWindow(clientController, clientModel, cfg);
        });

        GameEngine gameEngine = new GameEngine(cfg, gameModel, networkS2CController);
        gameEngine.resetGame();
        gameEngine.startGame();
    }
}
