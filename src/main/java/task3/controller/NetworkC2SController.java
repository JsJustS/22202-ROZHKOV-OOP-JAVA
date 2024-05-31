package task3.controller;
import task3.model.GameModel;

public class NetworkC2SController implements IController<NetworkS2CController.PacketType, GameModel> {

    private final GameModel server;

    public NetworkC2SController(GameModel gameModel) {
        this.server = gameModel;
    }

    public enum PacketType {
        PLAYER_JOINED,
        PLAYER_LEFT,
        PLAYER_MOVED,
        PLAYER_ABILITY_USED
    }

    public <T> void execute(NetworkS2CController.PacketType packetType, T value) {
        this.execute(packetType, server, value);
    }

    @Override
    public <T> void execute(NetworkS2CController.PacketType packetType, GameModel model, T value) {

    }
}
