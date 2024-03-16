package task1.util;

import java.util.List;
import java.util.Random;

public interface ISequenceGen<T> {

    public List<T> generate(int length, Random random);
}
