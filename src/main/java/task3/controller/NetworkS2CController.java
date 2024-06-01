package task3.controller;

import task3.model.ClientModel;
import task3.view.MainWindow;

import javax.swing.*;

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
        // Execute on client Thread
        SwingUtilities.invokeLater(() -> {
            this.execute(packetType, client, value);
        });
    }

    @Override
    public <T> void execute(PacketType packetType, ClientModel model, T value) {
        switch (packetType) {
            case MAP_DIM_CHANGED: {
                int[] packet = (int[]) value;
                model.setFieldWidthInBlocks(packet[0]);
                model.setFieldHeightInBlocks(packet[1]);
                model.setMapReady(true);
                break;
            }
            case BLOCK_PLACED: {
                int[] packet = (int[]) value;
                model.addBlock(packet[0], packet[1], packet[2]);
                break;
            }
            case BLOCK_REMOVED: {
                int[] packet = (int[]) value;
                model.removeBlock(packet[0], packet[1]);
                break;
            }
        }
    }
}
