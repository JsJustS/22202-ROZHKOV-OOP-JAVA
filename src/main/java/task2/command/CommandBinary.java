package task2.command;

import task2.error.BadArgumentCommandException;
import task2.error.RuntimeCommandException;
import task2.error.RuntimeContextException;
import task2.util.Context;

import java.util.List;

public class CommandBinary extends Command {
    protected double firstOperand;
    protected double secondOperand;

    protected CommandBinary(String name) {
        super(name, 0);
        this.firstOperand = Double.NaN;
        this.secondOperand = Double.NaN;
    }

    public void run(Context ctx, List<Object> args) throws RuntimeCommandException, BadArgumentCommandException {
        super.run(ctx, args);
        try {
            this.secondOperand = ctx.pop();
        } catch (RuntimeContextException e) {
            throw new RuntimeCommandException(this.name + " could not perform operation: " + e.getMessage());
        }

        try {
            this.firstOperand = ctx.pop();
        } catch (RuntimeContextException e) {
            ctx.push(this.secondOperand);
            throw new RuntimeCommandException(this.name + " could not perform operation: " + e.getMessage());
        }
    }
}
