package task3.util;

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
}
