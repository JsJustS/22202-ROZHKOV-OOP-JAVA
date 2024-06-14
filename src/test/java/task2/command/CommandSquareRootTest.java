package task2.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import task2.error.*;
import task2.factory.CommandFactory;
import task2.util.Context;

import java.util.ArrayList;

public class CommandSquareRootTest {
    @Test
    public void testApp() {
        try {
            Context ctx = new Context();
            ctx.push(4D);
            CommandFactory factory = new CommandFactory();
            factory.init();

            Command command = factory.create("SQRT");
            Assertions.assertEquals(command.toString(), "SQRT");

            command.run(ctx, new ArrayList<Object>());

            Assertions.assertEquals(ctx.peek(), 2D);
        } catch (RuntimeContextException | RuntimeCommandException | BadArgumentCommandException |
                 CommandCreationException | ConfigException e) {
            Assertions.fail(e.getMessage());
        }
    }
}
