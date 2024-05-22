package task3;

import task3.util.ArgParser;
import task3.util.Config;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        ArgParser parser = new ArgParser(args);
        Config cfg = (parser.length() == 0) ? Config.GENERAL : Config.load(parser.get(0).getAsString());

        SwingUtilities.invokeLater(() -> {
            Screen screen = new Screen(UIController, world);
        });
    }
}
