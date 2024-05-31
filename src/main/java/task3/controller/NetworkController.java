package task3.controller;

import task3.model.ClientModel;

public class NetworkController implements IController<NetworkController.PacketType, ClientModel> {

    public enum PacketType {
        //### SERVER ##########
        MAP_CHANGED,
        ENTITY_SPAWNED,
        ENTITY_DESPAWNED,
        ENTITY_PLAYED_ANIMATION,
        BLOCK_PLACED,
        BLOCK_REMOVED,
        //### CLIENT ##########
        PLAYER_JOINED,
        PLAYER_LEFT,
        PLAYER_MOVED,
        PLAYER_ABILITY_USED
    }

    @Override
    public <T> void execute(PacketType packetType, ClientModel model, T value) {

    }
}
