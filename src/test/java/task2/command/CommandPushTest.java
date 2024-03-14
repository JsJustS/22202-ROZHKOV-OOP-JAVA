package task2.command;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import task2.error.*;
import task2.factory.CommandFactory;
import task2.util.Context;

import java.util.ArrayList;
import java.util.Collections;

public class CommandPushTest extends TestCase {
    public CommandPushTest(String testName) {super(testName);}

    public static Test suite() {return new TestSuite(CommandPushTest.class);}

    public void testApp() {
        try {
            Context ctx = new Context();
            CommandFactory factory = new CommandFactory();
            factory.init();

            Command command = factory.create("PUSH");
            assertEquals(command.toString(), "PUSH");

            command.run(ctx, new ArrayList<Object>(Collections.singletonList(42.42D)));
            assertEquals(ctx.peek(), 42.42D);
        } catch (RuntimeContextException | RuntimeCommandException | BadArgumentCommandException |
                 CommandCreationException | ConfigException e) {
            fail(e.getMessage());
        }
    }
}
