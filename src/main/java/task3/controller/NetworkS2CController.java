package task3.controller;

import task3.model.ClientModel;

public class NetworkS2CController implements IController<NetworkS2CController.PacketType, ClientModel> {

    public enum PacketType {
        ENTITY_SPAWNED,
        ENTITY_DESPAWNED,
        ENTITY_PLAYED_ANIMATION,
        BLOCK_PLACED,
        BLOCK_REMOVED
    }

    @Override
    public <T> void execute(PacketType packetType, ClientModel model, T value) {

    }
}
