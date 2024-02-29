package task4;

import task4.controller.Controller;
import task4.model.World;
import task4.util.ArgParser;
import task4.util.Config;
import task4.view.Screen;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        ArgParser parser = new ArgParser(args);
        Config cfg = (parser.length() == 0) ? Config.GENERAL : Config.load(parser.get(0).getAsString());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Controller controller = new Controller();
                World world = new World();
                // view
                Screen screen = new Screen(controller, world);
            }
        });
    }
}
