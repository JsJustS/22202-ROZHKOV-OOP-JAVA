package task4.service.factory.part;

public class Car implements Identifiable {
    private final String threadName;

    private final BodyPart bodyPart;
    private final MotorPart motorPart;
    private final AccessoryPart accessoryPart;

    public Car(BodyPart bodyPart, MotorPart motorPart, AccessoryPart accessoryPart) {
        this.threadName = Thread.currentThread().getName();
        this.bodyPart = bodyPart;
        this.motorPart = motorPart;
        this.accessoryPart = accessoryPart;
    }

    public String getBodyId() {
        return this.bodyPart.getId();
    }

    public String getMotorId() {
        return this.motorPart.getId();
    }

    public String getAccessoryId() {
        return this.accessoryPart.getId();
    }
    @Override
    public String getId() {
        return "$car_" + this.toString() + "$fromThread_" + this.threadName;
    }

    @Override
    public String toString() {
        return String.format(
                "Auto %1$s (Body: %2$s, Motor: %3$s, Accessory: %4$s)",
                this.getId(), this.getBodyId(), this.getMotorId(), this.getAccessoryId()
        );
    }
}
