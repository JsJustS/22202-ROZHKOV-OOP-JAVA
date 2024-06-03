package task3.engine.entity;

import java.awt.image.BufferedImage;

public abstract class Entity {
    // Note:
    // (x, y) should be the center of Entity.
    // it's hitbox should be calculated as (x - w/2, y - h/2) and (x + w/2, y + h/2).
    protected double x;
    protected double y;
    protected double speedX;
    protected double speedY;
    protected double maxVelocity;
    protected double velocityX;
    protected double velocityY;
    protected double friction;
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
        this.speedX = 1;
        this.speedY = 1;
        this.friction = 0.2;
        this.maxVelocity = 0.5;
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

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return this.y;
    }

    protected void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    public double getSpeedX() {
        return this.speedX;
    }

    protected void setSpeedY(double speedY) {
        this.speedY = speedY;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public double getSpeedY() {
        return this.speedY;
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

    public void tickMovement() {
        if (Math.abs(this.getVelocityX()) > this.maxVelocity) {
            this.setVelocityX(this.maxVelocity*Math.signum(this.getVelocityX()));
        }
        if (Math.abs(this.getVelocityY()) > this.maxVelocity) {
            this.setVelocityY(this.maxVelocity*Math.signum(this.getVelocityY()));
        }
        this.setX(this.getX()+this.getVelocityX());
        this.setY(this.getY()+this.getVelocityY());
        this.setVelocityX(this.getVelocityX() * this.friction);
        this.setVelocityY(this.getVelocityY() * this.friction);
        if (this.getVelocityX() < 0.001 && this.getVelocityX() > -0.001) {
            this.setVelocityX(0);
        }
        if (this.getVelocityY() < 0.001 && this.getVelocityY() > -0.001) {
            this.setVelocityY(0);
        }
    }

    public void kill() {
        this.alive = false;
    }

    public boolean isAlive() {
        return alive;
    }
}
