package task2.command;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import task2.error.*;
import task2.util.CommandFactory;
import task2.util.Context;
import task2.util.ContextTest;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandDefineTest extends TestCase {
    public CommandDefineTest(String testName) {super(testName);}

    public static Test suite() {return new TestSuite(CommandDefineTest.class);}

    public void testApp() {
        try {
            Context ctx = new Context();
            Command command = new CommandFactory().create("DEFINE");
            assertEquals(command.toString(), "DEFINE");

            command.loadArgs(new ArrayList<Object>(Arrays.asList("VAR_NAME", 42.42D)));

            assertFalse(ctx.hasVar("VAR_NAME"));
            command.run(ctx);
            assertTrue(ctx.hasVar("VAR_NAME"));
            assertEquals(ctx.getVar("VAR_NAME"), 42.42D);
        } catch (RuntimeCommandException | BadArgumentCommandException | CommandCreationException | ConfigException e) {
            fail(e.getMessage());
        }
    }
}
