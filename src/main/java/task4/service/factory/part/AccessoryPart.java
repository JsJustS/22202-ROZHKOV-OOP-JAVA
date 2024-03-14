package task4.service.factory.part;

import task4.model.World;

public class AccessoryPart extends Part {
    private final String threadName;
    public AccessoryPart(String producerId, String threadName) {
        super(producerId);
        this.threadName = threadName;
    }

    @Override
    public String getId() {
        return super.getId() + "$fromThread_" + this.threadName;
    }
}
