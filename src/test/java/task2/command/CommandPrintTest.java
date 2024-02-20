package task2.command;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import task2.error.*;
import task2.util.CommandFactory;
import task2.util.Context;

import java.util.ArrayList;

public class CommandPrintTest extends TestCase {
    public CommandPrintTest(String testName) {super(testName);}

    public static Test suite() {return new TestSuite(CommandPrintTest.class);}

    public void testApp() {
        try {
            Context ctx = new Context();
            ctx.push(42.42D);
            Command command = new CommandFactory().create("PRINT");
            assertEquals(command.toString(), "PRINT");

            command.loadArgs(new ArrayList<Object>());
            command.run(ctx);
        } catch (RuntimeCommandException | BadArgumentCommandException | CommandCreationException | ConfigException e) {
            fail(e.getMessage());
        }
    }
}
