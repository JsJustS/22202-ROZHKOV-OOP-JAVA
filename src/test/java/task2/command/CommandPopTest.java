package task2.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import task2.error.*;
import task2.factory.CommandFactory;
import task2.util.Context;

import java.util.ArrayList;

public class CommandPopTest {
@Test
    public void testApp() {
        try {
            Context ctx = new Context();
            ctx.push(42.42D);
            ctx.push(42.042D);
            CommandFactory factory = new CommandFactory();
            factory.init();

            Command command = factory.create("POP");
            Assertions.assertEquals(command.toString(), "POP");

            command.run(ctx, new ArrayList<Object>());

            Assertions.assertEquals(ctx.peek(), 42.42D);
        } catch (RuntimeContextException | RuntimeCommandException | BadArgumentCommandException |
                 CommandCreationException | ConfigException e) {
            Assertions.fail(e.getMessage());
        }
    }
}
