package task2.util;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import task2.command.*;
import task2.error.CommandCreationException;
import task2.error.ConfigException;

public class CommandFactoryTest extends TestCase {

    public CommandFactoryTest(String testName) {super(testName);}

    public static Test suite() {return new TestSuite(CommandFactoryTest.class);}

    public void testApp() {
        try {
            CommandFactory factory = new CommandFactory();
            assertSame(factory.create("DEFINE").getClass(), CommandDefine.class);
            assertSame(factory.create("/").getClass(), CommandDivide.class);
            assertSame(factory.create("DIVIDE").getClass(), CommandDivide.class);
            assertSame(factory.create("-").getClass(), CommandMinus.class);
            assertSame(factory.create("MINUS").getClass(), CommandMinus.class);
            assertSame(factory.create("+").getClass(), CommandPlus.class);
            assertSame(factory.create("PLUS").getClass(), CommandPlus.class);
            assertSame(factory.create("*").getClass(), CommandMultiply.class);
            assertSame(factory.create("MULTIPLY").getClass(), CommandMultiply.class);
            assertSame(factory.create("SQRT").getClass(), CommandSquareRoot.class);
            assertSame(factory.create("POP").getClass(), CommandPop.class);
            assertSame(factory.create("PRINT").getClass(), CommandPrint.class);
            assertSame(factory.create("PUSH").getClass(), CommandPush.class);
        } catch (ConfigException | CommandCreationException e) {
            fail(e.getMessage());
        }
    }
}
