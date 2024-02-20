package task2.command;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import task2.error.*;
import task2.util.CommandFactory;
import task2.util.Context;

import java.util.ArrayList;

public class CommandDivideTest extends TestCase {
    public CommandDivideTest(String testName) {super(testName);}

    public static Test suite() {return new TestSuite(CommandDivideTest.class);}

    public void testApp() {
        try {
            Context ctx = new Context();

            Command command = new CommandFactory().create("DIVIDE");
            assertEquals(command.toString(), "DIVIDE");

            ctx.push(1D); ctx.push(2D);
            command.loadArgs(new ArrayList<Object>());
            command.run(ctx);

            assertEquals(ctx.peek(), 0.5D);

            command = new CommandFactory().create("/");
            assertEquals(command.toString(), "DIVIDE");

            ctx.push(4D); ctx.push(2D);
            command.loadArgs(new ArrayList<Object>());
            command.run(ctx);

            assertEquals(ctx.peek(), 2D);
        } catch (RuntimeContextException | RuntimeCommandException | BadArgumentCommandException |
                 CommandCreationException | ConfigException e) {
            fail(e.getMessage());
        }
    }
}
