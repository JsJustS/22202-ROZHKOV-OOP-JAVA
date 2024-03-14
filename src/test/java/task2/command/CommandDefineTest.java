package task2.command;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import task2.error.*;
import task2.factory.CommandFactory;
import task2.util.Context;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandDefineTest extends TestCase {
    public CommandDefineTest(String testName) {super(testName);}

    public static Test suite() {return new TestSuite(CommandDefineTest.class);}

    public void testApp() {
        try {
            Context ctx = new Context();
            CommandFactory factory = new CommandFactory();
            factory.init();

            Command command = factory.create("DEFINE");
            assertEquals(command.toString(), "DEFINE");

            assertFalse(ctx.hasVar("VAR_NAME"));
            command.run(ctx, new ArrayList<Object>(Arrays.asList("VAR_NAME", 42.42D)));
            assertTrue(ctx.hasVar("VAR_NAME"));
            assertEquals(ctx.getVar("VAR_NAME"), 42.42D);

            command.run(ctx, new ArrayList<Object>(Arrays.asList("VAR_NAME", 13.37D)));
            assertEquals(ctx.getVar("VAR_NAME"), 13.37D);
        } catch (RuntimeCommandException | BadArgumentCommandException | CommandCreationException | ConfigException e) {
            fail(e.getMessage());
        }
    }
}
