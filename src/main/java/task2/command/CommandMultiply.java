package task2.command;

import task2.error.RuntimeCommandException;
import task2.util.Context;

public class CommandMultiply extends CommandBinary {
    public CommandMultiply() {
        super("MULTIPLY");
    }

    @Override
    public void run(Context ctx) throws RuntimeCommandException {
        super.run(ctx);
        ctx.push(this.firstOperand * this.secondOperand);
    }
}
