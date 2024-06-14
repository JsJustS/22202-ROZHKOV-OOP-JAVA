package task4;

import task4.controller.UIController;
import task4.model.World;
import task4.service.factory.Factory;
import task4.util.ArgParser;
import task4.util.Config;
import task4.view.Screen;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        ArgParser parser = new ArgParser(args);
        Config cfg = (parser.length() == 0) ? Config.GENERAL : Config.load(parser.get(0).getAsString());

        UIController UIController = new UIController();
        World world = new World();
        SwingUtilities.invokeLater(() -> {
            Screen screen = new Screen(UIController, world);
        });

        Factory factory = new Factory(world, cfg);
        factory.start();
    }
}
