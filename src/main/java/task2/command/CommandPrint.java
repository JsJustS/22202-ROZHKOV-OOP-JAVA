package task2.command;

import task2.error.RuntimeCommandException;
import task2.error.RuntimeContextException;
import task2.util.Context;

public class CommandPrint extends Command {
    public CommandPrint() {
        super("PRINT", 0);
    }

    @Override
    public void run(Context ctx) throws RuntimeCommandException {
        super.run(ctx);
        try {
            System.out.println(ctx.peek());
        } catch (RuntimeContextException e) {
            throw new RuntimeCommandException(this.name + " could not print: " + e.getMessage());
        }
    }
}
