package task2.command;

import task2.error.BadArgumentCommandException;
import task2.error.RuntimeCommandException;
import task2.util.Context;

import java.util.List;

public abstract class Command {
    protected final String name;
    protected final int argCount;

    protected Command(String name, int argCount) {
        this.name = name;
        this.argCount = argCount;
    }

    public void run(Context ctx) throws RuntimeCommandException {
        // do stuff
    }

    public void loadArgs(List<Object> args) throws BadArgumentCommandException {
        if (args.size() != this.argCount) {
            throw new BadArgumentCommandException("Wrong amount of arguments");
        }
    }

    @Override
    public String toString() {
        return this.name;
    }
}
