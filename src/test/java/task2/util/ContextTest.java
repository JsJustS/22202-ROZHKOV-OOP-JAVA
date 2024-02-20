package task2.util;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import task2.error.RuntimeContextException;

public class ContextTest extends TestCase {
    public ContextTest(String testName) {super(testName);}

    public static Test suite() {return new TestSuite(ContextTest.class);}

    public void testApp() {
        Context ctx = new Context();

        double var_value = 42.42D;
        assertFalse(ctx.hasVar("VAR_NAME"));

        ctx.setVar("VAR_NAME", var_value);
        assertTrue(ctx.hasVar("VAR_NAME"));
        assertEquals(ctx.getVar("VAR_NAME"), var_value);

        try {
            ctx.peek();
            fail("Stack should be empty, asserting throw of RuntimeContextException");
        } catch (RuntimeContextException ignored) {}

        try {
            ctx.pop();
            fail("Stack should be empty, asserting throw of RuntimeContextException");
        } catch (RuntimeContextException ignored) {}

        ctx.push(var_value);

        try {
            assertEquals(ctx.peek(), var_value);
            assertEquals(ctx.pop(), var_value);
        } catch (RuntimeContextException e) {
            fail(e.getMessage());
        }

        try {
            ctx.peek();
            fail("Stack should be empty, asserting throw of RuntimeContextException");
        } catch (RuntimeContextException ignored) {}

        try {
            ctx.pop();
            fail("Stack should be empty, asserting throw of RuntimeContextException");
        } catch (RuntimeContextException ignored) {}
    }
}
