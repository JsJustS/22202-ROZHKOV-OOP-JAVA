package task3.engine;

import task3.controller.NetworkController;
import task3.model.ClientModel;
import task3.model.GameModel;

/**
 * manages communication between ClientModel and GameModel
 * basically a controller
 * */
public class NetworkManager {
    private final NetworkController networkController;

    public NetworkManager(ClientModel clientModel, GameModel gameModel) {
        networkController = new NetworkController();
//        gameModel.subscribe(()->{
//            networkController.execute(
//                    NetworkController.PacketType.BLOCK_PLACED,
//                    clientModel,
//                    gameModel.get
//                    );
//        });
    }
}
