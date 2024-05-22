package task3.view;

public abstract class Screen {
    // Implementation-based
    protected IRenderer renderer;

    public Screen(int w, int h) {
        renderer = new RenderImplSwing(w, h);
    }


    void tick() {

    }

    /**
     * Draw itself
     * */
    void render() {

    }
}
