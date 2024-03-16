package task2.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import task2.error.RuntimeContextException;

public class ContextTest {
    @Test
    public void testApp() {
        Context ctx = new Context();

        double var_value = 42.42D;
        Assertions.assertFalse(ctx.hasVar("VAR_NAME"));

        ctx.setVar("VAR_NAME", var_value);
        Assertions.assertTrue(ctx.hasVar("VAR_NAME"));
        Assertions.assertEquals(ctx.getVar("VAR_NAME"), var_value);

        try {
            ctx.peek();
            Assertions.fail("Stack should be empty, asserting throw of RuntimeContextException");
        } catch (RuntimeContextException ignored) {}

        try {
            ctx.pop();
            Assertions.fail("Stack should be empty, asserting throw of RuntimeContextException");
        } catch (RuntimeContextException ignored) {}

        ctx.push(var_value);

        try {
            Assertions.assertEquals(ctx.peek(), var_value);
            Assertions.assertEquals(ctx.pop(), var_value);
        } catch (RuntimeContextException e) {
            Assertions.fail(e.getMessage());
        }

        try {
            ctx.peek();
            Assertions.fail("Stack should be empty, asserting throw of RuntimeContextException");
        } catch (RuntimeContextException ignored) {}

        try {
            ctx.pop();
            Assertions.fail("Stack should be empty, asserting throw of RuntimeContextException");
        } catch (RuntimeContextException ignored) {}
    }
}
