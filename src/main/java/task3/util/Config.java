package task3.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class Config {
    public static final Config GENERAL;

    // private int value = 0;

    static {
        GENERAL = new ConfigBuilder()

                .build();
    }

    private Config() {}

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
//                    case "value":
//                        builder = builder.withValue(Integer.parseInt(pair[1]));
//                        break;
                }
            } catch (NumberFormatException e) {
                //todo: log e.getMessage()
            }
        }

        return builder.build();
    }

//    public int getValue() {
//        return value;
//    }

    public static class ConfigBuilder {
        private final Config config = new Config();

//        public ConfigBuilder withValue(int value) {
//            config.value = value;
//            return this;
//        }

        public Config build() {
            return config;
        }
    }
}
