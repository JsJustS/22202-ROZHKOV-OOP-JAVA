package task2.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import task2.error.*;
import task2.factory.CommandFactory;
import task2.util.Context;

import java.util.ArrayList;

public class CommandMultiplyTest {
    @Test
    public void testApp() {
        try {
            Context ctx = new Context();
            CommandFactory factory = new CommandFactory();
            factory.init();

            Command command = factory.create("MULTIPLY");
            Assertions.assertEquals(command.toString(), "MULTIPLY");

            ctx.push(3D); ctx.push(2D);
            command.run(ctx, new ArrayList<Object>());

            Assertions.assertEquals(ctx.peek(), 6D);

            command = factory.create("*");
            Assertions.assertEquals(command.toString(), "MULTIPLY");

            ctx.push(2D); ctx.push(9D);
            command.run(ctx, new ArrayList<Object>());

            Assertions.assertEquals(ctx.peek(), 18D);
        } catch (RuntimeContextException | RuntimeCommandException | BadArgumentCommandException |
                 CommandCreationException | ConfigException e) {
            Assertions.fail(e.getMessage());
        }
    }
}
