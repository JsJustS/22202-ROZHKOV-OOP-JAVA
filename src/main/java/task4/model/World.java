package task4.model;

import task4.util.pubsub.Publisher;

public class World extends Publisher {

    public enum Part {
        BODY,
        MOTOR,
        ACCESSORY
    }
    private int motorCreationSpeed;
    private int bodyCreationSpeed;
    private int accessoryCreationSpeed;

    public int getMotorCreationSpeed() {
        return motorCreationSpeed;
    }

    public int getCreationSpeed(Part part) {
        switch (part) {
            case BODY: return bodyCreationSpeed;
            case MOTOR: return motorCreationSpeed;
            case ACCESSORY: return accessoryCreationSpeed;
        }
        throw new RuntimeException("Undefined part");
    }

    public void setCreationSpeed(Part part, int value) {
        switch (part) {
            case BODY: bodyCreationSpeed = value; this.notifySubscribers(); return;
            case MOTOR: motorCreationSpeed = value; this.notifySubscribers(); return;
            case ACCESSORY: accessoryCreationSpeed = value; this.notifySubscribers(); return;
        }
        throw new RuntimeException("Undefined part");
    }
}
