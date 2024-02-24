package task2.command;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import task2.error.*;
import task2.util.CommandFactory;
import task2.util.Context;

import java.util.ArrayList;

public class CommandSquareRootTest extends TestCase {
    public CommandSquareRootTest(String testName) {super(testName);}

    public static Test suite() {return new TestSuite(CommandSquareRootTest.class);}

    public void testApp() {
        try {
            Context ctx = new Context();
            ctx.push(4D);

            Command command = new CommandFactory().create("SQRT");
            assertEquals(command.toString(), "SQRT");

            command.run(ctx, new ArrayList<Object>());

            assertEquals(ctx.peek(), 2D);
        } catch (RuntimeContextException | RuntimeCommandException | BadArgumentCommandException |
                 CommandCreationException | ConfigException e) {
            fail(e.getMessage());
        }
    }
}
