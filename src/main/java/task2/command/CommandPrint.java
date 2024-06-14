package task2.command;

import task2.error.BadArgumentCommandException;
import task2.error.RuntimeCommandException;
import task2.error.RuntimeContextException;
import task2.util.Context;

import java.util.List;

public class CommandPrint extends Command {
    public CommandPrint() {
        super("PRINT", 0);
    }

    @Override
    public void run(Context ctx, List<Object> args) throws RuntimeCommandException, BadArgumentCommandException {
        super.run(ctx, args);
        try {
            System.out.println(ctx.peek());
        } catch (RuntimeContextException e) {
            throw new RuntimeCommandException(this.name + " could not print: " + e.getMessage());
        }
    }
}
