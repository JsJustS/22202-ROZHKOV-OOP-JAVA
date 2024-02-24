package task2.command;

import task2.error.BadArgumentCommandException;
import task2.error.RuntimeCommandException;
import task2.error.RuntimeContextException;
import task2.util.Context;

import java.util.List;

public class CommandSquareRoot extends Command {
    public CommandSquareRoot() {
        super("SQRT", 0);
    }

    @Override
    public void run(Context ctx, List<Object> args) throws RuntimeCommandException, BadArgumentCommandException {
        super.run(ctx, args);
        try {
            if (ctx.peek() < 0) {
                throw new ArithmeticException("Value below zero.");
            }

            double value = ctx.pop();
            ctx.push(Math.sqrt(value));
        } catch (ArithmeticException | RuntimeContextException e) {
            throw new RuntimeCommandException(this.name + " could not perform operation: " + e.getMessage());
        }
    }
}
