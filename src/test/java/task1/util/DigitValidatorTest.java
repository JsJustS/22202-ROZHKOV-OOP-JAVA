package task1.util;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DigitValidatorTest extends TestCase {
    public DigitValidatorTest(String testName) {super(testName);}

    public static Test suite() {return new TestSuite(DigitValidatorTest.class);}

    public void testApp() {
        IValidator validator = new DigitValidator(5);

        assertTrue(validator.isOk("01234"));
        assertFalse(validator.isOk("012345"));
        assertFalse(validator.isOk("01233"));
        assertFalse(validator.isOk("0123a"));
    }
}
