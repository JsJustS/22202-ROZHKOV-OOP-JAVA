package task3.engine.entity;

import java.awt.image.BufferedImage;

public abstract class Entity {
    // Note:
    // (x, y) should be the center of Entity.
    // it's hitbox should be calculated as (x - w/2, y - h/2) and (x + w/2, y + h/2).
    protected double x;
    protected double y;
    protected double speed;
    protected double maxVelocity;
    protected double velocity;
    protected double friction;
    private double hitboxWidth;
    private double hitboxHeight;
    protected boolean alive;
    private int id;

    protected boolean isMoving;
    protected Direction direction;

    protected BufferedImage sprite;

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    Entity() {
        x = 0;
        y = 0;
        this.hitboxWidth = 0.5;
        this.hitboxHeight = 0.5;
        this.speed = 1;
        this.friction = 0.5;
        this.maxVelocity = 0.5;
        this.alive = true;
        this.isMoving = false;
        this.direction = Direction.UP;
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

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return this.y;
    }

    protected void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return this.speed;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
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
        this.tickMovement();
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public void setDirection(PlayerEntity.Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void tickMovement() {
        if (Math.abs(this.getVelocity()) > this.maxVelocity) {
            this.setVelocity(this.maxVelocity*Math.signum(this.getVelocity()));
        }

        switch (this.direction) {
            case UP:
                this.setY(this.getY()-this.getVelocity());
                break;
            case LEFT:
                this.setX(this.getX()-this.getVelocity());
                break;
            case DOWN:
                this.setY(this.getY()+this.getVelocity());
                break;
            case RIGHT:
                this.setX(this.getX()+this.getVelocity());
                break;
        }
        this.setVelocity(this.getVelocity() * this.friction);
        if (this.getVelocity() < 0.001 && this.getVelocity() > -0.001) {
            this.setVelocity(0);
            this.setX((int)this.getX()+0.5);
            this.setY((int)this.getY()+0.5);
        }
    }

    public void kill() {
        this.alive = false;
    }

    public boolean isAlive() {
        return alive;
    }
}
