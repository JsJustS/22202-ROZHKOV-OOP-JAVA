package task2.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import task2.error.*;
import task2.factory.CommandFactory;
import task2.util.Context;

import java.util.ArrayList;

public class CommandPrintTest {
    @Test
    public void testApp() {
        try {
            Context ctx = new Context();
            ctx.push(42.42D);
            CommandFactory factory = new CommandFactory();
            factory.init();

            Command command = factory.create("PRINT");
            Assertions.assertEquals(command.toString(), "PRINT");

            command.run(ctx, new ArrayList<Object>());
        } catch (RuntimeCommandException | BadArgumentCommandException | CommandCreationException | ConfigException e) {
            Assertions.fail(e.getMessage());
        }
    }
}
