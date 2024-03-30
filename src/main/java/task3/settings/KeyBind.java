package task3.settings;

import task3.keyboard.Key;

public class KeyBind extends AbstractSetting<Key> {
    private final Runnable action;

    public KeyBind(Runnable action) {
        this.action = action;
        this.setValue(null);
    }

    public KeyBind(Runnable action, Key key) {
        this.action = action;
        this.setValue(key);
    }

    // todo: please i beg you please do this later please
    public boolean isPressed() {
        return false;
    }
}
