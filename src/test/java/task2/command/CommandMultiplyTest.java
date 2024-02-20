package task2.command;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import task2.error.*;
import task2.util.CommandFactory;
import task2.util.Context;

import java.util.ArrayList;

public class CommandMultiplyTest extends TestCase {
    public CommandMultiplyTest(String testName) {super(testName);}

    public static Test suite() {return new TestSuite(CommandMultiplyTest.class);}

    public void testApp() {
        try {
            Context ctx = new Context();

            Command command = new CommandFactory().create("MULTIPLY");
            assertEquals(command.toString(), "MULTIPLY");

            ctx.push(3D); ctx.push(2D);
            command.loadArgs(new ArrayList<Object>());
            command.run(ctx);

            assertEquals(ctx.peek(), 6D);

            command = new CommandFactory().create("*");
            assertEquals(command.toString(), "MULTIPLY");

            ctx.push(2D); ctx.push(9D);
            command.loadArgs(new ArrayList<Object>());
            command.run(ctx);

            assertEquals(ctx.peek(), 18D);
        } catch (RuntimeContextException | RuntimeCommandException | BadArgumentCommandException |
                 CommandCreationException | ConfigException e) {
            fail(e.getMessage());
        }
    }
}
