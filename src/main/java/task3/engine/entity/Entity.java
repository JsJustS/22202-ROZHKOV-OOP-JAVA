package task3.engine.entity;

public abstract class Entity {
    // Note:
    // (x, y) should be the center of Entity.
    // it's hitbox should be calculated as (x - w/2, y - h/2) and (x + w/2, y + h/2).
    protected double x;
    protected double y;
    protected double w;
    protected double h;

    Entity(double w, double h) {

    }
}
