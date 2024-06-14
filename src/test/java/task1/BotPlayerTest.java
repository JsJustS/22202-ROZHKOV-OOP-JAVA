package task1;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import task1.util.Config;
import task1.util.DigitSequenceGen;

import java.util.ArrayList;
import java.util.List;

public class BotPlayerTest extends TestCase {

    public BotPlayerTest(String testName) {super(testName);}

    public static Test suite() {return new TestSuite(BotPlayerTest.class);}

    public void testApp() {
        BotPlayer<Integer> botPlayer = new BotPlayer<>(
                Config.GENERAL, new DigitSequenceGen()
        );

        assertFalse(botPlayer.isSequenceGuessed());
        assertEquals(botPlayer.getCows(), 0);
        assertEquals(botPlayer.getBulls(), 0);

        List<Integer> testList = new ArrayList<>();
        for (int i = 0; i < Config.GENERAL.length(); ++i) {
            testList.add(i);
        }

        assertEquals(
                botPlayer.getClue().length(),
                testList.toString().length()
        );
    }
}
