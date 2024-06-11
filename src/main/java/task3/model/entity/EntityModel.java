package task3.model.entity;

import task3.model.IModel;
import task3.model.abilityInstance.Ability;

public class EntityModel implements IModel {
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

    protected Ability ability;

    protected boolean isMoving;
    protected Direction direction;

    protected String spritePath;
    protected RenderLayer layer;

    public EntityModel() {
        setAlive(true);
        setRenderLayer(RenderLayer.values()[RenderLayer.values().length-1]);
        setMoving(false);
    }

    public Ability getAbility() {
        return ability;
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
    }

    public boolean isCollidable() {
        return isCollidable;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setCollidable(boolean isCollidable) {
        this.isCollidable = isCollidable;
    }

    public String getSpritePath() {
        return spritePath;
    }

    public void setSpritePath(String path) {
        this.spritePath = path;
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

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void setRenderLayer(RenderLayer layer) {
        this.layer = layer;
    }

    public RenderLayer getRenderLayer() {
        return layer;
    }
}
