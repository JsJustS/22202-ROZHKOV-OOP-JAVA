package task4;

import task4.util.ArgParser;
import task4.util.Config;

public class Main {
    public static void main(String[] args) {
        ArgParser parser = new ArgParser(args);
        Config cfg = (parser.length() == 0) ? Config.GENERAL : Config.load(parser.get(0).getAsString());

    }
}
