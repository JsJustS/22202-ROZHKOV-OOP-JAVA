package task3.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class Config {
    public static final Config GENERAL;

    private String gameTitle = "BomberMan | Java Edition";
    private int winWidth;
    private int winHeight;

    private long seed;
    private int fieldWidth;
    private int fieldHeight;

    private String abilityKey;

    static {
        GENERAL = new ConfigBuilder()
                .withWinWidth(1280).withWinHeight(720)
                .withSeed(0)
                .withFieldWidth(10).withFieldHeight(7)
                .withAbilityKey("Enter")
                .build();
    }

    private Config() {}

    public String getGameTitle() {
        return gameTitle;
    }
    public String getAbilityKey() {
        return abilityKey;
    }

    public int getWinWidth() {
        return winWidth;
    }

    public int getWinHeight() {
        return winHeight;
    }

    public long getSeed() {
        return seed;
    }

    public int getFieldWidth() {
        return fieldWidth;
    }

    public int getFieldHeight() {
        return fieldHeight;
    }

    public static Config load(String filepath) {
        ConfigBuilder builder = new ConfigBuilder();

        InputStream stream;
        try {
            stream = new FileInputStream(filepath);
        } catch (FileNotFoundException e) {
            return GENERAL;
        }

        Scanner scanner = new Scanner(stream);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            if (line.trim().startsWith("#") || line.trim().length() == 0) continue;

            String[] pair = line.split("\\s*=\\s*", 2);
            if (pair.length < 2) {
                continue;
            }

            try {
                switch (pair[0]) {
                    case "window_width":
                        builder = builder.withWinWidth(Integer.parseInt(pair[1]));
                        break;
                    case "window_height":
                        builder = builder.withWinHeight(Integer.parseInt(pair[1]));
                        break;
                    case "seed":
                        builder = builder.withSeed(Long.parseLong(pair[1]));
                        break;
                    case "field_width":
                        builder = builder.withFieldWidth(Integer.parseInt(pair[1]));
                        break;
                    case "field_height":
                        builder = builder.withFieldHeight(Integer.parseInt(pair[1]));
                        break;
                    case "keybind_ability":
                        builder = builder.withAbilityKey(pair[1]);
                        break;
                }
            } catch (NumberFormatException e) {
                //todo: log e.getMessage()
            }
        }

        return builder.build();
    }

    public static class ConfigBuilder {
        private final Config config = new Config();

        public ConfigBuilder withWinWidth(int value) {
            config.winWidth = value;
            return this;
        }

        public ConfigBuilder withWinHeight(int value) {
            config.winHeight = value;
            return this;
        }

        public ConfigBuilder withSeed(long value) {
            config.seed = value;
            return this;
        }

        public ConfigBuilder withFieldWidth(int value) {
            config.fieldWidth = value;
            return this;
        }

        public ConfigBuilder withFieldHeight(int value) {
            config.fieldHeight = value;
            return this;
        }

        public ConfigBuilder withAbilityKey(String key) {
            config.abilityKey = key;
            return this;
        }

        public Config build() {
            return config;
        }
    }
}
