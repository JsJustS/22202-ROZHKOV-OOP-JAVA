package task2.command;

import task2.error.BadArgumentCommandException;
import task2.error.RuntimeCommandException;
import task2.util.Context;

import java.util.List;

public class CommandDefine extends Command {

    private String varName;
    private double varValue;

    public CommandDefine() {
        super("DEFINE", 2);
        this.varName = null;
        this.varValue = Double.NaN;
    }

    @Override
    public void run(Context ctx, List<Object> args) throws RuntimeCommandException, BadArgumentCommandException {
        super.run(ctx, args);

        try {
            this.varName = (String) args.get(0);
            this.varValue = (double) this.parseArgument(ctx, args.get(1));
        } catch (ClassCastException e) {
            throw new BadArgumentCommandException(this.name + " could not parse arguments");
        }

        if (Double.isNaN(this.varValue)) {
            throw new RuntimeCommandException(this.name + " has undefined variable.");
        }

        ctx.setVar(this.varName, this.varValue);
    }
}
