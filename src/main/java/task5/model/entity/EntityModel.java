package task5.model.entity;

import task5.model.IModel;
import task5.model.abilityInstance.Ability;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityModel implements IModel {
    protected EntityType entityType;
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
    protected int receivedDamage;
    protected EntityModel attacker;

    protected Ability ability;

    protected boolean isMoving;
    protected Direction direction;

    private final Map<Direction, List<String>> spriteSheets = new HashMap<>();
    protected RenderLayer layer;
    private int animationTick = 0;
    private int animationStep = 0;
    private int animationPerTick = 10;

    public EntityModel() {
        setAlive(true);
        setRenderLayer(RenderLayer.values()[RenderLayer.values().length-1]);
        setMoving(false);
        setSpeed(1);
        setFriction(0.5);
        setMaxVelocity(0.5);
        setCollidable(false);
        setDirection(Direction.DOWN);
        loadSpriteSheets();
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
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

    public double getMaxVelocity() {
        return maxVelocity;
    }

    public void setMaxVelocity(double maxVelocity) {
        this.maxVelocity = maxVelocity;
    }

    public double getFriction() {
        return friction;
    }

    public void setFriction(double friction) {
        this.friction = friction;
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
        if (!this.spriteSheets.containsKey(this.getDirection())) return null;
        List<String> sheet = this.spriteSheets.get(this.getDirection());
        return sheet.get(this.animationStep % sheet.size());
    }

    protected void addSpriteSheet(Direction direction, List<String> sheet) {
        this.spriteSheets.put(direction, sheet);
    }

    public void loadSpriteSheets() {
    }

    public int getAnimationTick() {
        return animationTick;
    }

    public void setAnimationTick(int animationTick) {
        this.animationTick = animationTick;
    }

    public int getAnimationStep() {
        return animationStep;
    }

    public void setAnimationStep(int animationStep) {
        this.animationStep = animationStep;
    }

    public int getReceivedDamage() {
        return receivedDamage;
    }

    public void setReceivedDamage(int receivedDamage) {
        this.receivedDamage = receivedDamage;
    }

    public EntityModel getAttacker() {
        return attacker;
    }

    public void setAttacker(EntityModel attacker) {
        this.attacker = attacker;
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

    public int getAnimationPerTick() {
        return animationPerTick;
    }

    public void setAnimationPerTick(int animationPerTick) {
        this.animationPerTick = animationPerTick;
    }
}
