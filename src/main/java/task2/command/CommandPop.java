package task2.command;

import task2.error.BadArgumentCommandException;
import task2.error.RuntimeCommandException;
import task2.error.RuntimeContextException;
import task2.util.Context;

import java.util.List;

public class CommandPop extends Command {
    public CommandPop() {
        super("POP", 0);
    }

    @Override
    public void run(Context ctx, List<Object> args) throws RuntimeCommandException, BadArgumentCommandException {
        super.run(ctx, args);
        try {
            ctx.pop();
        } catch (RuntimeContextException e) {
            throw new RuntimeCommandException(this.name + " could not pop from stack: " + e.getMessage());
        }
    }
}
