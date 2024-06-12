package task3.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class Config {
    private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);
    public static final Config GENERAL;

    private String gameTitle = "BomberMan | Java Edition";
    private int winWidth;
    private int winHeight;

    private double difficultyModifier;
    private long seed;
    private int fieldWidth;
    private int fieldHeight;

    private String abilityKey;
    private String moveUpKey;
    private String moveDownKey;
    private String moveLeftKey;
    private String moveRightKey;
    private String changeAbilityKey;
    private String leaveKey;

    private int bots;

    static {
        GENERAL = new ConfigBuilder()
                .withWinWidth(1000).withWinHeight(1000)
                .withDifficultyModifier(0.5)
                .withSeed(0)
                .withFieldWidth(15).withFieldHeight(15)
                .withBots(3)
                .withAbilityKey("Space")
                .withMoveUpKey("W")
                .withMoveLeftKey("A")
                .withMoveDownKey("S")
                .withMoveRightKey("D")
                .withChangeAbilityKey("E")
                .withLeaveKey("Escape")
                .build();
    }

    private Config() {}

    public String getGameTitle() {
        return gameTitle;
    }
    public String getAbilityKey() {
        return abilityKey;
    }
    public String getMoveUpKey() {
        return moveUpKey;
    }
    public String getMoveDownKey() {
        return moveDownKey;
    }
    public String getMoveLeftKey() {
        return moveLeftKey;
    }
    public String getMoveRightKey() {
        return moveRightKey;
    }
    public String getChangeAbilityKey() {
        return changeAbilityKey;
    }
    public String getLeaveKey() {
        return leaveKey;
    }

    public int getBots() {
        return bots;
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

    public double getDifficultyModifier() {
        return difficultyModifier;
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
                    case "difficulty_modifier":
                        builder = builder.withDifficultyModifier(Double.parseDouble(pair[1]));
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
                    case "keybind_move_up":
                        builder = builder.withMoveUpKey(pair[1]);
                        break;
                    case "keybind_move_left":
                        builder = builder.withMoveLeftKey(pair[1]);
                        break;
                    case "keybind_move_down":
                        builder = builder.withMoveDownKey(pair[1]);
                        break;
                    case "keybind_move_right":
                        builder = builder.withMoveRightKey(pair[1]);
                        break;
                    case "keybind_change_ability":
                        builder = builder.withChangeAbilityKey(pair[1]);
                        break;
                    case "keybind_leave":
                        builder = builder.withLeaveKey(pair[1]);
                        break;
                    case "bots":
                        builder = builder.withBots(Integer.parseInt(pair[1]));
                        break;
                }
            } catch (NumberFormatException e) {
                LOGGER.error(e.toString());
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

        public ConfigBuilder withDifficultyModifier(double value) {
            config.difficultyModifier = value;
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

        public ConfigBuilder withBots(int value) {
            config.bots = value;
            return this;
        }

        public ConfigBuilder withAbilityKey(String key) {
            config.abilityKey = key;
            return this;
        }

        public ConfigBuilder withMoveUpKey(String key) {
            config.moveUpKey = key;
            return this;
        }

        public ConfigBuilder withMoveLeftKey(String key) {
            config.moveLeftKey = key;
            return this;
        }

        public ConfigBuilder withMoveDownKey(String key) {
            config.moveDownKey = key;
            return this;
        }

        public ConfigBuilder withMoveRightKey(String key) {
            config.moveRightKey = key;
            return this;
        }

        public ConfigBuilder withChangeAbilityKey(String key) {
            config.changeAbilityKey = key;
            return this;
        }

        public ConfigBuilder withLeaveKey(String key) {
            config.leaveKey = key;
            return this;
        }

        public Config build() {
            return config;
        }
    }
}
