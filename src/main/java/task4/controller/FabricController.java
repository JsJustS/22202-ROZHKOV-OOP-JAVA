package task4.controller;

import task4.model.World;

public class FabricController implements IController<FabricController.Operation, World>{

    public enum Operation {
        UPD_BODY_STORED,
        UPD_MOTOR_STORED,
        UPD_ACCESSORY_STORED,
        UPD_CAR_STORED,
        UPD_BODY_CRAFTED,
        UPD_MOTOR_CRAFTED,
        UPD_ACCESSORY_CRAFTED,
        UPD_CAR_CRAFTED,
        UPD_TASK_WAITING
    }

    @Override
    public <T> void execute(Operation operation, World world, T value) {
        switch (operation) {
            case UPD_TASK_WAITING:
                world.setTasksWaitingCount((int) value);
                break;
            case UPD_BODY_STORED:
                world.setBodyStoredCount((int) value);
                break;
            case UPD_MOTOR_STORED:
                world.setMotorStoredCount((int) value);
                break;
            case UPD_ACCESSORY_STORED:
                world.setAccessoryStoredCount((int) value);
                break;
            case UPD_CAR_STORED:
                world.setCarsStoredCount((int) value);
                break;
            case UPD_BODY_CRAFTED:
                world.setBodyCraftedCount(world.getBodyCraftedCount() + (int) value);
                break;
            case UPD_MOTOR_CRAFTED:
                world.setMotorCraftedCount(world.getMotorCraftedCount() + (int) value);
                break;
            case UPD_ACCESSORY_CRAFTED:
                world.setAccessoryCraftedCount(world.getAccessoryCraftedCount() + (int) value);
                break;
            case UPD_CAR_CRAFTED:
                world.setCarsCraftedCount(world.getCarsCraftedCount() + (int) value);
                break;
            default: throw new RuntimeException("Unsupported operation");
        }
    }
}
