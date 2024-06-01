package task3.engine.block;

import java.awt.image.BufferedImage;

public abstract class Block {
    protected int x;
    protected int y;
    protected BufferedImage sprite;
    protected int blastResistance;
    protected boolean isCollidable;

    Block(int x, int y) {
        this.x = x;
        this.y = y;
        blastResistance = 0;
        isCollidable = false;
    }

    public int getX() {return x;}
    public int getY() {return y;}
    public BufferedImage getSprite() {return sprite;}

    public void onExplosion() {};
}