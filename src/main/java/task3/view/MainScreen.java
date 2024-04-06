package task3.view;

import java.awt.*;
import java.util.Stack;

public class MainScreen extends Screen {
    private Stack<Screen> screenStack;
    private static MainScreen instance;

    private MainScreen() {

    }

    static MainScreen getInstance() {
        if (instance == null)  {
            instance = new MainScreen();
        }
        instance.renderer.setColor(new Color(155, 100, 100, 127))
                .setFont(Font.getFont("Comic Sans").deriveFont(14f));
        return instance;
    }

    void openScreen(Screen screen) {
        screenStack.add(screen);
    }

    void closeScreen() {
        screenStack.pop();
    }

    Screen getActiveScreen() {
        return screenStack.peek();
    }

    void tick() {
        for (Screen screen : screenStack) {
            screen.tick();
        }

        this.render();
    }

    void render() {
        // 1. render itself

        // 2. other
        for (Screen screen : screenStack) {
            screen.render();
        }
    }
}
