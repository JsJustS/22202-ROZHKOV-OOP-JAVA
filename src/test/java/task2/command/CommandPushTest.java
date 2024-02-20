package task2.command;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import task2.error.*;
import task2.util.CommandFactory;
import task2.util.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class CommandPushTest extends TestCase {
    public CommandPushTest(String testName) {super(testName);}

    public static Test suite() {return new TestSuite(CommandPushTest.class);}

    public void testApp() {
        try {
            Context ctx = new Context();
            Command command = new CommandFactory().create("PUSH");
            assertEquals(command.toString(), "PUSH");

            command.loadArgs(new ArrayList<Object>(Collections.singletonList(42.42D)));
            command.run(ctx);
            assertEquals(ctx.peek(), 42.42D);
        } catch (RuntimeContextException | RuntimeCommandException | BadArgumentCommandException |
                 CommandCreationException | ConfigException e) {
            fail(e.getMessage());
        }
    }
}
