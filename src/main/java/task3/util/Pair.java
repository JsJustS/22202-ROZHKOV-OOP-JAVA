package task3.util;

import java.util.Objects;

public final class Pair<E1, E2> {
    private final E1 element1;
    private final E2 element2;

    public Pair(E1 element1, E2 element2) {
        this.element1 = element1;
        this.element2 = element2;
    }

    public E1 getFirst() {
        return element1;
    }

    public E2 getSecond() {
        return element2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return element1.equals(pair.element1) && element2.equals(pair.element2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(element1, element2);
    }

    @Override
    public String toString() {
        return "Pair{" +
                "first=" + element1 +
                ", second=" + element2 +
                '}';
    }
}
