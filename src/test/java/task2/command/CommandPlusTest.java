package task2.command;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import task2.error.*;
import task2.util.CommandFactory;
import task2.util.Context;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandPlusTest extends TestCase {
    public CommandPlusTest(String testName) {super(testName);}

    public static Test suite() {return new TestSuite(CommandPlusTest.class);}

    public void testApp() {
        try {
            Context ctx = new Context();

            Command command = new CommandFactory().create("PLUS");
            assertEquals(command.toString(), "PLUS");

            ctx.push(1D); ctx.push(2D);
            command.run(ctx, new ArrayList<Object>());

            assertEquals(ctx.peek(), 3D);

            command = new CommandFactory().create("+");
            assertEquals(command.toString(), "PLUS");

            ctx.push(1D); ctx.push(9D);
            command.run(ctx, new ArrayList<Object>());

            assertEquals(ctx.peek(), 10D);
        } catch (RuntimeContextException | RuntimeCommandException | BadArgumentCommandException |
                 CommandCreationException | ConfigException e) {
            fail(e.getMessage());
        }
    }
}
