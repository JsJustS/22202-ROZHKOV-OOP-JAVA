package task1.util;

import java.util.HashSet;

/**
 * Implementation of Validator for task-1
 * */
public class DigitValidator implements IValidator {
    private final int len;

    public DigitValidator(int len) {
        this.len = len;
    }

    @Override
    public boolean isOk(Object input) {
        if (!(input instanceof String)) return false;
        String string = (String) input;
        if (string.length() != this.len) return false;

        HashSet<Character> chars = new HashSet<>(len);
        for (char c : string.toCharArray()) {
            if (!Character.isDigit(c) || chars.contains(c) || chars.size() >= this.len) {
                return false;
            }
            chars.add(c);
        }

        return true;
    }
}
