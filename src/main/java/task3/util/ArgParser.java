package task3.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArgParser {
    private final List<String> ARGS = new ArrayList<>();

    public ArgParser(String[] strings) {
        ARGS.addAll(Arrays.asList(strings));
    }

    public boolean has(String arg) {
        return ARGS.contains(arg);
    }

    public boolean isFlag(String arg) {
        return arg.startsWith("-");
    }

    public int length() {
        return ARGS.size();
    }

    public Argument get(int index) {
        return new Argument(ARGS.get(index));
    }

    public List<Argument> getAfter(String arg) {
        if (!this.has(arg)) throw new ArgumentParsingException(String.format("\"%s\" is not present", arg));

        int index = 0;
        for (String stored : this.ARGS) {
            if (!stored.equals(arg)) {
                index++;
            } else {
                break;
            }
        }

        ArrayList<Argument> args = new ArrayList<>();
        index++;
        while (index < this.ARGS.size() && !this.isFlag(this.get(index).getAsString())) {
            args.add(this.get(index++));
        }

        return args;
    }

    public class Argument {
        private final String value;
        Argument(String value) {
            this.value = value;
        }

        public String getAsString() {return this.value;}
        public int getAsInt() {
            try {
                return Integer.parseInt(this.value);
            } catch (NumberFormatException e) {
                throw new ArgumentParsingException(
                        String.format("\"%s\" has not been parsed due to an exception", this.value), e
                );
            }
        }
        public boolean getAsBool() {
            return Boolean.parseBoolean(this.value);
        }
    }

    public static class ArgumentParsingException extends RuntimeException {
        public ArgumentParsingException() {
            super();
        }
        public ArgumentParsingException(String message) {
            super(message);
        }
        public ArgumentParsingException(Throwable cause) {
            super(cause);
        }
        public ArgumentParsingException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
