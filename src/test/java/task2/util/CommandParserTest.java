package task2.util;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.ArrayList;

public class CommandParserTest extends TestCase {
    public CommandParserTest(String testName) {super(testName);}

    public static Test suite() {return new TestSuite(CommandParserTest.class);}

    public void testApp() {
        Context ctx = new Context();

        CommandParser emptied = new CommandParser("");
        CommandParser nullified = new CommandParser(null);
        CommandParser commented = new CommandParser("# this is comment");

        assertEquals(emptied.getCommandName(), nullified.getCommandName());
        assertEquals(emptied.shouldSkip(), nullified.shouldSkip());
        assertEquals(emptied.getArgs(ctx), nullified.getArgs(ctx));
        assertEquals(commented.getCommandName(), nullified.getCommandName());
        assertEquals(commented.shouldSkip(), nullified.shouldSkip());
        assertEquals(commented.getArgs(ctx), nullified.getArgs(ctx));
        assertNull(emptied.getCommandName());
        assertTrue(emptied.shouldSkip());
        assertEquals(emptied.getArgs(ctx), new ArrayList<>());

        CommandParser command = new CommandParser("COMMAND_NAME TEXT_ARG 42.42");
        assertEquals(command.getCommandName(), "COMMAND_NAME");
        ArrayList<Object> args = new ArrayList<>();
        args.add("TEXT_ARG"); args.add(42.42D);
        assertEquals(command.getArgs(ctx), args);
        assertFalse(command.shouldSkip());
    }
}
