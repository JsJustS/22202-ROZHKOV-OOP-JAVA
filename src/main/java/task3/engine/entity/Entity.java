package task3.engine.entity;

import task3.controller.NetworkS2CController;
import task3.engine.block.Block;
import task3.model.GameModel;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

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
    protected boolean isCollidable;

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
        this.direction = Direction.DOWN;
        this.isCollidable = true;
    }

    public boolean isCollidable() {
        return isCollidable;
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

    public void tick(GameModel model) {
        this.tickMovement(model);
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

    public void tickMovement(GameModel model) {
        if (Math.abs(this.getVelocity()) > this.maxVelocity) {
            this.setVelocity(this.maxVelocity*Math.signum(this.getVelocity()));
        }

        double prevX = this.getX();
        double prevY = this.getY();
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
        if (isColliding(model)) {
            this.setX(prevX);
            this.setY(prevY);
            this.setVelocity(this.getVelocity() * this.friction);
        }

        this.setVelocity(this.getVelocity() * this.friction);
        if (this.getVelocity() < 0.001 && this.getVelocity() > -0.001) {
            this.setVelocity(0);
            this.setX((int)this.getX()+0.5);
            this.setY((int)this.getY()+0.5);
        }
    }

    public void damage(Entity source) {

    }

    public <T>void onFinishAbility(T data) {

    }

    public void kill() {
        this.alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

    private boolean isColliding(GameModel model) {
        List<Block> blocksToCheck = new ArrayList<>();
        blocksToCheck.add(model.getBlock((int)this.getX(), (int)this.getY()));
        blocksToCheck.add(model.getBlock((int)this.getX()+1, (int)this.getY()));
        blocksToCheck.add(model.getBlock((int)this.getX()-1, (int)this.getY()));
        blocksToCheck.add(model.getBlock((int)this.getX(), (int)this.getY()+1));
        blocksToCheck.add(model.getBlock((int)this.getX(), (int)this.getY()-1));
        for (Block block : blocksToCheck) {
            if (block == null) continue;
            if (!block.isCollidable()) continue;
            if (block.getX() > this.getX() + this.getHitboxWidth()/2 || this.getX() - this.getHitboxWidth()/2 > block.getX() + 1) {
                continue;
            }
            if (block.getY() > this.getY() + this.getHitboxHeight()/2 || this.getY() - this.getHitboxHeight()/2 > block.getY() + 1) {
                continue;
            }
            return true;
        }
        for (Entity entity : model.getEntities()) {
            if (!entity.isCollidable()) {
                continue;
            }
            if (entity.equals(this)) {
                continue;
            }
            if ((entity instanceof BombEntity) && ((BombEntity)entity).getParent().equals(this)) {
                continue;
            }
            if (entity.getX()-entity.getHitboxWidth()/2 > this.getX() + this.getHitboxWidth()/2 || this.getX() - this.getHitboxWidth()/2 > entity.getX()+entity.getHitboxWidth()/2) {
                continue;
            }
            if (entity.getY()-entity.getHitboxHeight()/2 > this.getY() + this.getHitboxHeight()/2 || this.getY() - this.getHitboxHeight()/2 > entity.getY()+entity.getHitboxHeight()) {
                continue;
            }
            return true;
        }
        return false;
    }
}
