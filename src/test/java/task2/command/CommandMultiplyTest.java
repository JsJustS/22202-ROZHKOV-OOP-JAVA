package task2.command;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import task2.error.*;
import task2.factory.CommandFactory;
import task2.util.Context;

import java.util.ArrayList;

public class CommandMultiplyTest extends TestCase {
    public CommandMultiplyTest(String testName) {super(testName);}

    public static Test suite() {return new TestSuite(CommandMultiplyTest.class);}

    public void testApp() {
        try {
            Context ctx = new Context();
            CommandFactory factory = new CommandFactory();
            factory.init();

            Command command = factory.create("MULTIPLY");
            assertEquals(command.toString(), "MULTIPLY");

            ctx.push(3D); ctx.push(2D);
            command.run(ctx, new ArrayList<Object>());

            assertEquals(ctx.peek(), 6D);

            command = factory.create("*");
            assertEquals(command.toString(), "MULTIPLY");

            ctx.push(2D); ctx.push(9D);
            command.run(ctx, new ArrayList<Object>());

            assertEquals(ctx.peek(), 18D);
        } catch (RuntimeContextException | RuntimeCommandException | BadArgumentCommandException |
                 CommandCreationException | ConfigException e) {
            fail(e.getMessage());
        }
    }
}
