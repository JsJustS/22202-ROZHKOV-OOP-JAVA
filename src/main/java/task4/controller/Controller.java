package task4.controller;

import task4.model.World;

public class Controller implements IController<Controller.Operation, World> {
    public enum Operation {
        UPD_SPEED_BODY,
        UPD_SPEED_MOTOR,
        UPD_SPEED_ACCESSORY,
        UPD_SPEED_DEALERS
    }

    @Override
    public void execute(Operation operation, World world, Object value) {
        switch (operation) {
            case UPD_SPEED_BODY: world.setCreationSpeed(World.Part.BODY, (Integer) value); return;
            case UPD_SPEED_MOTOR: world.setCreationSpeed(World.Part.MOTOR, (Integer) value); return;
            case UPD_SPEED_ACCESSORY: world.setCreationSpeed(World.Part.ACCESSORY, (Integer) value); return;
            case UPD_SPEED_DEALERS: world.setDealersSpeed((Integer) value); return;
        }
        throw new RuntimeException("Unsupported operation");
    }
}
