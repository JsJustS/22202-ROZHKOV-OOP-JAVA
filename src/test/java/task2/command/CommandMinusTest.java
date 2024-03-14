package task2.command;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import task2.error.*;
import task2.factory.CommandFactory;
import task2.util.Context;

import java.util.ArrayList;

public class CommandMinusTest extends TestCase {
    public CommandMinusTest(String testName) {super(testName);}

    public static Test suite() {return new TestSuite(CommandMinusTest.class);}

    public void testApp() {
        try {
            Context ctx = new Context();
            CommandFactory factory = new CommandFactory();
            factory.init();

            Command command = factory.create("MINUS");
            assertEquals(command.toString(), "MINUS");

            ctx.push(5D); ctx.push(4D);
            command.run(ctx, new ArrayList<Object>());

            assertEquals(ctx.peek(), 1D);

            command = factory.create("-");
            assertEquals(command.toString(), "MINUS");

            ctx.push(1D); ctx.push(9D);
            command.run(ctx, new ArrayList<Object>());

            assertEquals(ctx.peek(), -8D);
        } catch (RuntimeContextException | RuntimeCommandException | BadArgumentCommandException |
                 CommandCreationException | ConfigException e) {
            fail(e.getMessage());
        }
    }
}
