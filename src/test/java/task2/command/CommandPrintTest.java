package task2.command;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import task2.error.*;
import task2.factory.CommandFactory;
import task2.util.Context;

import java.util.ArrayList;

public class CommandPrintTest extends TestCase {
    public CommandPrintTest(String testName) {super(testName);}

    public static Test suite() {return new TestSuite(CommandPrintTest.class);}

    public void testApp() {
        try {
            Context ctx = new Context();
            ctx.push(42.42D);
            CommandFactory factory = new CommandFactory();
            factory.init();

            Command command = factory.create("PRINT");
            assertEquals(command.toString(), "PRINT");

            command.run(ctx, new ArrayList<Object>());
        } catch (RuntimeCommandException | BadArgumentCommandException | CommandCreationException | ConfigException e) {
            fail(e.getMessage());
        }
    }
}
