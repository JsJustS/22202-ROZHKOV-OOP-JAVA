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
            // todo: Поговорить об условии
            // Сейчас при попытке переозначить переменную
            // Она заменяется на этапе подстановки, и здесь вылетает ошибка
            // Стоит ли так и оставить, или переделать?
            // Технически условие не нарушается
            this.varName = (String) args.get(0);
            this.varValue = (double) args.get(1);
        } catch (ClassCastException e) {
            throw new BadArgumentCommandException(this.name + " could not parse arguments");
        }

        if (Double.isNaN(this.varValue)) {
            throw new RuntimeCommandException(this.name + " has undefined variable.");
        }

        ctx.setVar(this.varName, this.varValue);
    }
}
