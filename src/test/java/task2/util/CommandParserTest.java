package task2.util;

import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;

public class CommandParserTest {

    public void testApp() {
        CommandParser emptied = new CommandParser("");
        CommandParser nullified = new CommandParser(null);
        CommandParser commented = new CommandParser("# this is comment");
        emptied.parse();
        nullified.parse();
        commented.parse();

        Assertions.assertEquals(emptied.getCommandName(), nullified.getCommandName());
        Assertions.assertEquals(emptied.shouldSkip(), nullified.shouldSkip());
        Assertions.assertEquals(emptied.getArgs(), nullified.getArgs());
        Assertions.assertEquals(commented.getCommandName(), nullified.getCommandName());
        Assertions.assertEquals(commented.shouldSkip(), nullified.shouldSkip());
        Assertions.assertEquals(commented.getArgs(), nullified.getArgs());
        Assertions.assertNull(emptied.getCommandName());
        Assertions.assertTrue(emptied.shouldSkip());
        Assertions.assertEquals(emptied.getArgs(), new ArrayList<>());

        CommandParser command = new CommandParser("COMMAND_NAME TEXT_ARG 42.42");
        command.parse();
        Assertions.assertEquals(command.getCommandName(), "COMMAND_NAME");
        ArrayList<Object> args = new ArrayList<>();
        args.add("TEXT_ARG"); args.add(42.42D);
        Assertions.assertEquals(command.getArgs(), args);
        Assertions.assertFalse(command.shouldSkip());
    }
}
