package task4;

import task4.util.IScreen;

public class Factory {

    private IScreen screen;

    public void setScreenForCallbacks(IScreen screen) {
        this.screen = screen;
    }
    public void updateBodyCraftingSpeed(int value) {
        // update
    }

    public void updateMotorCraftingSpeed(int value) {
        // update
    }

    public void updateAccessoryCraftingSpeed(int value) {
        // update
    }
}
