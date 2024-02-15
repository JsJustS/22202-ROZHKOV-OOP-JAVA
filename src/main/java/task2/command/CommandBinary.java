package task2.command;

import task2.error.RuntimeCommandException;
import task2.error.RuntimeContextException;
import task2.util.Context;

public class CommandBinary extends Command {
    protected double firstOperand;
    protected double secondOperand;

    protected CommandBinary(String name) {
        super(name, 0);
        this.firstOperand = Double.NaN;
        this.secondOperand = Double.NaN;
    }

    public void run(Context ctx) throws RuntimeCommandException {
        super.run(ctx);
        try {
            this.secondOperand = ctx.pop();
            this.firstOperand = ctx.pop();
        } catch (RuntimeContextException e) {
            throw new RuntimeCommandException(this.name + " could not perform operation: " + e.getMessage());
        }
    }
}
