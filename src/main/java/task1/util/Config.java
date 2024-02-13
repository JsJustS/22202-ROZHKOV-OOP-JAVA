package task1.util;

public class Config {
    // Base config for native use
    public static Config GENERAL;
    private final Long _seed;
    private final int _length;

    public Config(Long seed, int length) {
        this._seed = seed;
        this._length = length;
    }

    public Long seed() {
        return this._seed;
    }

    public int length() {
        return this._length;
    }

    static {
        GENERAL = new Config(null, 4);
    }
}
