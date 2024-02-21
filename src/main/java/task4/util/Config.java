package task4.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class Config {
    public static final Config GENERAL;

    private int storageBodySize = 0;
    private int storageMotorSize = 0;
    private int storageAccessorySize = 0;
    private int storageAutoSize = 0;
    private int accessorySuppliersCount = 0;
    private int workersCount = 0;
    private int dealersCount = 0;
    private boolean isLogging = false;


    static {
        GENERAL = new ConfigBuilder()
                .withStorageBodySize(100)
                .withStorageMotorSize(100)
                .withStorageAccessorySize(100)
                .withStorageAutoSize(100)
                .withAccessorySuppliersCount(5)
                .withWorkersCount(10)
                .withDealersCount(20)
                .withLogging(true)
                .build();
    }

    private Config() {}

    public static Config load(String filepath) {
        ConfigBuilder builder = new ConfigBuilder();

        InputStream stream;
        try {
            stream = new FileInputStream(filepath);
        } catch (FileNotFoundException e) {
            // todo: log file not found
            return GENERAL;
        }

        Scanner scanner = new Scanner(stream);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            if (line.trim().startsWith("#") || line.trim().length() == 0) {continue;}

            String[] pair = line.split("\\s*=\\s*", 2);
            if (pair.length < 2) {
                //todo: log error
                continue;
            }

            try {
                switch (pair[0]) {
                    case "storageBodySize":
                        builder = builder.withStorageBodySize(Integer.parseInt(pair[1]));
                        break;
                    case "storageMotorSize":
                        builder = builder.withStorageMotorSize(Integer.parseInt(pair[1]));
                        break;
                    case "storageAccessorySize":
                        builder = builder.withStorageAccessorySize(Integer.parseInt(pair[1]));
                        break;
                    case "storageAutoSize":
                        builder = builder.withStorageAutoSize(Integer.parseInt(pair[1]));
                        break;
                    case "accessorySuppliersCount":
                        builder = builder.withAccessorySuppliersCount(Integer.parseInt(pair[1]));
                        break;
                    case "workersCount":
                        builder = builder.withWorkersCount(Integer.parseInt(pair[1]));
                        break;
                    case "dealersCount":
                        builder = builder.withDealersCount(Integer.parseInt(pair[1]));
                        break;
                    case "isLogging":
                        builder = builder.withLogging(Boolean.parseBoolean(pair[1]));
                        break;
                }
            } catch (NumberFormatException e) {
                //todo: log e.getMessage()
            }
        }
        return builder.build();
    }

    public int getStorageBodySize() {
        return storageBodySize;
    }

    public int getStorageMotorSize() {
        return storageMotorSize;
    }

    public int getStorageAccessorySize() {
        return storageAccessorySize;
    }

    public int getStorageAutoSize() {
        return storageAutoSize;
    }

    public int getAccessorySuppliersCount() {
        return accessorySuppliersCount;
    }

    public int getWorkersCount() {
        return workersCount;
    }

    public int getDealersCount() {
        return dealersCount;
    }

    public boolean isLogging() {
        return isLogging;
    }

    public static class ConfigBuilder {
        private final Config config = new Config();

        public ConfigBuilder withStorageBodySize(int size) {
            config.storageBodySize = size;
            return this;
        }

        public ConfigBuilder withStorageMotorSize(int size) {
            config.storageMotorSize = size;
            return this;
        }

        public ConfigBuilder withStorageAccessorySize(int size) {
            config.storageAccessorySize = size;
            return this;
        }

        public ConfigBuilder withStorageAutoSize(int size) {
            config.storageAutoSize = size;
            return this;
        }

        public ConfigBuilder withAccessorySuppliersCount(int count) {
            config.accessorySuppliersCount = count;
            return this;
        }

        public ConfigBuilder withWorkersCount(int count) {
            config.workersCount = count;
            return this;
        }

        public ConfigBuilder withDealersCount(int count) {
            config.dealersCount = count;
            return this;
        }

        public ConfigBuilder withLogging(boolean flag) {
            config.isLogging = flag;
            return this;
        }
        public Config build() {
            return config;
        }
    }
}
