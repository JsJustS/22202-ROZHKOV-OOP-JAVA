package task1.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DigitSequenceGen implements ISequenceGen<Integer> {
    @Override
    public List<Integer> generate(int length, Random random) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < Math.min(10, length); ++i) {
            list.add(i);
        }
        Collections.shuffle(list, random);
        return list;
    }
}
