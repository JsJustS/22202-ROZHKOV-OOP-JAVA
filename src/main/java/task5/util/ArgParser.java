package task5.util;

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

    public int length() {
        return ARGS.size();
    }

    public Argument get(int index) {
        return new Argument(ARGS.get(index));
    }

    public class Argument {
        private final String value;
        Argument(String value) {
            this.value = value;
        }

        public String getAsString() {return this.value;}
        public int getAsInt() {return Integer.parseInt(this.value);}
    }
}