package task3.controller;
import task3.engine.ability.DestroyBlockAbilityInstance;
import task3.model.GameModel;

public class NetworkC2SController implements IController<NetworkC2SController.PacketType, GameModel> {

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

    public <T> void execute(NetworkC2SController.PacketType packetType, T value) {
        this.execute(packetType, server, value);
    }

    @Override
    public <T> void execute(NetworkC2SController.PacketType packetType, GameModel model, T value) {
        switch (packetType) {
            case PLAYER_JOINED: {
                break;
            }
            case PLAYER_LEFT: {
                break;
            }
            case PLAYER_MOVED: {
                break;
            }
            case PLAYER_ABILITY_USED: {
                int[] packet = (int[]) value;
                model.addAbilityInstance(
                        new DestroyBlockAbilityInstance(packet[0], packet[1], null)
                );
                break;
            }
        }
    }
}
