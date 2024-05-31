package task3.controller;

import task3.model.ClientModel;

public class NetworkS2CController implements IController<NetworkS2CController.PacketType, ClientModel> {
    private final ClientModel client;

    public NetworkS2CController(ClientModel clientModel) {
        this.client = clientModel;
    }

    public enum PacketType {
        MAP_DIM_CHANGED,
        ENTITY_SPAWNED,
        ENTITY_DESPAWNED,
        ENTITY_PLAYED_ANIMATION,
        BLOCK_PLACED,
        BLOCK_REMOVED
    }

    public <T> void execute(PacketType packetType, T value) {
        this.execute(packetType, client, value);
    }

    @Override
    public <T> void execute(PacketType packetType, ClientModel model, T value) {
        switch (packetType) {
            case MAP_DIM_CHANGED: {
                int[] dims = (int[]) value;
                model.setFieldWidthInBlocks(dims[0]);
                model.setFieldHeightInBlocks(dims[1]);
                model.setMapReady(true);
                break;
            }
        }
    }
}
