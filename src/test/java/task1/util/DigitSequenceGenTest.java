package task1.util;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Random;

public class DigitSequenceGenTest extends TestCase {

    public DigitSequenceGenTest(String testName) {super(testName);}

    public static Test suite() {return new TestSuite(DigitSequenceGenTest.class);}

    public void testApp() {
        ISequenceGen<Integer> generator = new DigitSequenceGen();
        assertEquals(generator.generate(0, new Random()).size(), 0);
        assertEquals(generator.generate(5, new Random()).size(), 5);
        assertEquals(generator.generate(100000, new Random()).size(), 10);
    }
}
