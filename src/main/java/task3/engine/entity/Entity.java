package task3.engine.entity;

import task3.model.GameModel;

public abstract class Entity {
    // Note:
    // (x, y) should be the center of Entity.
    // it's hitbox should be calculated as (x - w/2, y - h/2) and (x + w/2, y + h/2).
    protected double x;
    protected double y;
    protected double w;
    protected double h;
    protected boolean alive;

    Entity(double w, double h) {
        x = 0;
        y = 0;
        this.w = w;
        this.h = h;
        this.alive = true;
    }

    public void tick() {

    }

    public void kill() {
        this.alive = false;
    }

    public boolean isAlive() {
        return alive;
    }
}
