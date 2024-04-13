package task2.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import task2.error.*;
import task2.factory.CommandFactory;
import task2.util.Context;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandDefineTest {
    @Test
    public void testApp() {
        try {
            Context ctx = new Context();
            CommandFactory factory = new CommandFactory();
            factory.init();

            Command command = factory.create("DEFINE");
            Assertions.assertEquals(command.toString(), "DEFINE");

            Assertions.assertFalse(ctx.hasVar("VAR_NAME"));
            command.run(ctx, new ArrayList<Object>(Arrays.asList("VAR_NAME", 42.42D)));
            Assertions.assertTrue(ctx.hasVar("VAR_NAME"));
            Assertions.assertEquals(ctx.getVar("VAR_NAME"), 42.42D);

            command.run(ctx, new ArrayList<Object>(Arrays.asList("VAR_NAME", 13.37D)));
            Assertions.assertEquals(ctx.getVar("VAR_NAME"), 13.37D);
        } catch (RuntimeCommandException | BadArgumentCommandException | CommandCreationException | ConfigException e) {
            Assertions.fail(e.getMessage());
        }
    }
}
