package task4.model;

import task4.util.pubsub.Publisher;

public class World extends Publisher {

    public enum Part {
        BODY,
        MOTOR,
        ACCESSORY
    }
    // === Speed values
    private int motorCreationSpeed;
    private int bodyCreationSpeed;
    private int accessoryCreationSpeed;
    private int dealersSpeed;

    // === Storages
    private int motorStoredCount;
    private int bodyStoredCount;
    private int accessoryStoredCount;
    private int carsStoredCount;

    private int motorCraftedCount;
    private int bodyCraftedCount;
    private int accessoryCraftedCount;
    private int carsCraftedCount;
    // === Threads
    private int tasksWaitingCount;

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

    public int getDealersSpeed() {
        return dealersSpeed;
    }

    public void setDealersSpeed(int value) {
        dealersSpeed = value;
        this.notifySubscribers();
    }

    public int getMotorStoredCount() {
        return motorStoredCount;
    }

    public void setMotorStoredCount(int motorStoredCount) {
        this.motorStoredCount = motorStoredCount;
        this.notifySubscribers();
    }

    public int getBodyStoredCount() {
        return bodyStoredCount;
    }

    public void setBodyStoredCount(int bodyStoredCount) {
        this.bodyStoredCount = bodyStoredCount;
        this.notifySubscribers();
    }

    public int getAccessoryStoredCount() {
        return accessoryStoredCount;
    }

    public void setAccessoryStoredCount(int accessoryStoredCount) {
        this.accessoryStoredCount = accessoryStoredCount;
        this.notifySubscribers();
    }

    public int getCarsStoredCount() {
        return carsStoredCount;
    }

    public void setCarsStoredCount(int carsStoredCount) {
        this.carsStoredCount = carsStoredCount;
        this.notifySubscribers();
    }

    public int getMotorCraftedCount() {
        return motorCraftedCount;
    }

    public void setMotorCraftedCount(int motorCraftedCount) {
        this.motorCraftedCount = motorCraftedCount;
        this.notifySubscribers();
    }

    public int getBodyCraftedCount() {
        return bodyCraftedCount;
    }

    public void setBodyCraftedCount(int bodyCraftedCount) {
        this.bodyCraftedCount = bodyCraftedCount;
        this.notifySubscribers();
    }

    public int getAccessoryCraftedCount() {
        return accessoryCraftedCount;
    }

    public void setAccessoryCraftedCount(int accessoryCraftedCount) {
        this.accessoryCraftedCount = accessoryCraftedCount;
        this.notifySubscribers();
    }

    public int getCarsCraftedCount() {
        return carsCraftedCount;
    }

    public void setCarsCraftedCount(int carsCraftedCount) {
        this.carsCraftedCount = carsCraftedCount;
        this.notifySubscribers();
    }

    public int getTasksWaitingCount() {
        return tasksWaitingCount;
    }

    public void setTasksWaitingCount(int tasksWaitingCount) {
        this.tasksWaitingCount = tasksWaitingCount;
        this.notifySubscribers();
    }
}
