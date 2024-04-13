package task2.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import task2.command.*;
import task2.error.CommandCreationException;
import task2.error.ConfigException;
import task2.factory.CommandFactory;

public class CommandFactoryTest {

    @Test

    public void testApp() {
        try {
            CommandFactory factory = new CommandFactory();
            factory.init();
            Assertions.assertSame(factory.create("DEFINE").getClass(), CommandDefine.class);
            Assertions.assertSame(factory.create("/").getClass(), CommandDivide.class);
            Assertions.assertSame(factory.create("DIVIDE").getClass(), CommandDivide.class);
            Assertions.assertSame(factory.create("-").getClass(), CommandMinus.class);
            Assertions.assertSame(factory.create("MINUS").getClass(), CommandMinus.class);
            Assertions.assertSame(factory.create("+").getClass(), CommandPlus.class);
            Assertions.assertSame(factory.create("PLUS").getClass(), CommandPlus.class);
            Assertions.assertSame(factory.create("*").getClass(), CommandMultiply.class);
            Assertions.assertSame(factory.create("MULTIPLY").getClass(), CommandMultiply.class);
            Assertions.assertSame(factory.create("SQRT").getClass(), CommandSquareRoot.class);
            Assertions.assertSame(factory.create("POP").getClass(), CommandPop.class);
            Assertions.assertSame(factory.create("PRINT").getClass(), CommandPrint.class);
            Assertions.assertSame(factory.create("PUSH").getClass(), CommandPush.class);
        } catch (ConfigException | CommandCreationException e) {
            Assertions.fail(e.getMessage());
        }
    }
}
