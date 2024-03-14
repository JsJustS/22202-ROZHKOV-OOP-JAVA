package task4.service.factory.part;

import task4.model.World;

public class Car implements Identifiable {
    private final String threadName;

    public Car(String threadName) {
        this.threadName = threadName;
    }
    @Override
    public String getId() {
        return "$car_" + this.toString() + "$fromThread_" + this.threadName;
    }
}
