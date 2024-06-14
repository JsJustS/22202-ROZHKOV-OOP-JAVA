package task2.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import task2.error.*;
import task2.factory.CommandFactory;
import task2.util.Context;

import java.util.ArrayList;
import java.util.Collections;

public class CommandPushTest {
    @Test
    public void testApp() {
        try {
            Context ctx = new Context();
            CommandFactory factory = new CommandFactory();
            factory.init();

            Command command = factory.create("PUSH");
            Assertions.assertEquals(command.toString(), "PUSH");

            command.run(ctx, new ArrayList<Object>(Collections.singletonList(42.42D)));
            Assertions.assertEquals(ctx.peek(), 42.42D);
        } catch (RuntimeContextException | RuntimeCommandException | BadArgumentCommandException |
                 CommandCreationException | ConfigException e) {
            Assertions.fail(e.getMessage());
        }
    }
}
