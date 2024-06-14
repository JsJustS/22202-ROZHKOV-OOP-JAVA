package task2.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import task2.error.*;
import task2.factory.CommandFactory;
import task2.util.Context;

import java.util.ArrayList;

public class CommandMinusTest {
    @Test
    public void testApp() {
        try {
            Context ctx = new Context();
            CommandFactory factory = new CommandFactory();
            factory.init();

            Command command = factory.create("MINUS");
            Assertions.assertEquals(command.toString(), "MINUS");

            ctx.push(5D); ctx.push(4D);
            command.run(ctx, new ArrayList<Object>());

            Assertions.assertEquals(ctx.peek(), 1D);

            command = factory.create("-");
            Assertions.assertEquals(command.toString(), "MINUS");

            ctx.push(1D); ctx.push(9D);
            command.run(ctx, new ArrayList<Object>());

            Assertions.assertEquals(ctx.peek(), -8D);
        } catch (RuntimeContextException | RuntimeCommandException | BadArgumentCommandException |
                 CommandCreationException | ConfigException e) {
            Assertions.fail(e.getMessage());
        }
    }
}
