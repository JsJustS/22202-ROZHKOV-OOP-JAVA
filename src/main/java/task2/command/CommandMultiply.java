package task2.command;

import task2.error.BadArgumentCommandException;
import task2.error.RuntimeCommandException;
import task2.util.Context;

import java.util.List;

public class CommandMultiply extends CommandBinary {
    public CommandMultiply() {
        super("MULTIPLY");
    }

    @Override
    public void run(Context ctx, List<Object> args) throws RuntimeCommandException, BadArgumentCommandException {
        super.run(ctx, args);
        ctx.push(this.firstOperand * this.secondOperand);
    }
}
