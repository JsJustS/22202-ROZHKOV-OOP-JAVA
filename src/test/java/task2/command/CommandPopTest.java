package task2.command;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import task2.error.*;
import task2.factory.CommandFactory;
import task2.util.Context;

import java.util.ArrayList;

public class CommandPopTest extends TestCase {
    public CommandPopTest(String testName) {super(testName);}

    public static Test suite() {return new TestSuite(CommandPopTest.class);}

    public void testApp() {
        try {
            Context ctx = new Context();
            ctx.push(42.42D);
            ctx.push(42.042D);
            CommandFactory factory = new CommandFactory();
            factory.init();

            Command command = factory.create("POP");
            assertEquals(command.toString(), "POP");

            command.run(ctx, new ArrayList<Object>());

            assertEquals(ctx.peek(), 42.42D);
        } catch (RuntimeContextException | RuntimeCommandException | BadArgumentCommandException |
                 CommandCreationException | ConfigException e) {
            fail(e.getMessage());
        }
    }
}
