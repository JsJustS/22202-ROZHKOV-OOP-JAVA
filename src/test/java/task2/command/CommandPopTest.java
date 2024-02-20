package task2.command;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import task2.error.*;
import task2.util.CommandFactory;
import task2.util.Context;

import java.util.ArrayList;
import java.util.Collections;

public class CommandPopTest extends TestCase {
    public CommandPopTest(String testName) {super(testName);}

    public static Test suite() {return new TestSuite(CommandPopTest.class);}

    public void testApp() {
        try {
            Context ctx = new Context();
            ctx.push(42.42D);
            ctx.push(42.042D);

            Command command = new CommandFactory().create("POP");
            assertEquals(command.toString(), "POP");

            command.loadArgs(new ArrayList<Object>());
            command.run(ctx);

            assertEquals(ctx.peek(), 42.42D);
        } catch (RuntimeContextException | RuntimeCommandException | BadArgumentCommandException |
                 CommandCreationException | ConfigException e) {
            fail(e.getMessage());
        }
    }
}
