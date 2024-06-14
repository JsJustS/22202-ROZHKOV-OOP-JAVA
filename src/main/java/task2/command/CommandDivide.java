package task2.command;

import task2.error.BadArgumentCommandException;
import task2.error.RuntimeCommandException;
import task2.util.Context;

import java.util.List;

public class CommandDivide extends CommandBinary {
    public CommandDivide() {
        super("DIVIDE");
    }

    @Override
    public void run(Context ctx, List<Object> args) throws RuntimeCommandException, BadArgumentCommandException {
        super.run(ctx, args);
        try {
            if (this.secondOperand == 0) throw new ArithmeticException("Division by zero.");
            ctx.push(this.firstOperand / this.secondOperand);
        } catch (ArithmeticException e) {
            ctx.push(this.secondOperand);
            ctx.push(this.firstOperand);
            throw new RuntimeCommandException(this.name + " could not perform operation due to an arithmetic exception");
        }
    }
}
