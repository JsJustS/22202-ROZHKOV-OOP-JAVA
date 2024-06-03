package task3.engine.entity;

import java.awt.image.BufferedImage;

public abstract class Entity {
    // Note:
    // (x, y) should be the center of Entity.
    // it's hitbox should be calculated as (x - w/2, y - h/2) and (x + w/2, y + h/2).
    protected double x;
    protected double y;
    private double hitboxWidth;
    private double hitboxHeight;
    protected boolean alive;
    private int id;

    protected BufferedImage sprite;

    Entity() {
        x = 0;
        y = 0;
        this.hitboxWidth = 0.5;
        this.hitboxHeight = 0.5;
        this.alive = true;
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    protected void setWidth(double w) {
        this.hitboxWidth = w;
    }

    public double getHitboxWidth() {
        return hitboxWidth;
    }

    public double getHitboxHeight() {
        return hitboxHeight;
    }

    protected void setHeight(double h) {
        this.hitboxHeight = h;
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
