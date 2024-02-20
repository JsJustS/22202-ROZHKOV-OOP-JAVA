package task2.command;

import task2.error.BadArgumentCommandException;
import task2.error.RuntimeCommandException;
import task2.util.Context;

import java.util.List;

public class CommandPush extends Command {
    private double number;

    public CommandPush() {
        super("PUSH", 1);
        this.number = Double.NaN;
    }

    @Override
    public void run(Context ctx) throws RuntimeCommandException {
        super.run(ctx);
        if (Double.isNaN(this.number)) {
            throw new RuntimeCommandException(this.name + " tried to push NaN on stack.");
        }

        ctx.push(this.number);
    }

    @Override
    public void loadArgs(List<Object> args) throws BadArgumentCommandException {
        super.loadArgs(args);
        try {
            this.number = (double) args.get(0);
        } catch (ClassCastException e) {
            throw new BadArgumentCommandException(this.name + " could not parse argument");
        }
    }
}