package task4.service.factory.part;

import task4.model.World;

public abstract class Part implements Identifiable {
    protected final String producerId;

    public Part(String producerId) {
        this.producerId = producerId;
    }

    @Override
    public String getId() {
        return "$prodBy_" + producerId + "$id_" + this.hashCode();
    }
}
