package task3.engine.block;

import task3.model.GameModel;

import java.awt.image.BufferedImage;

public abstract class Block {
    protected int x;
    protected int y;
    protected BufferedImage sprite;
    protected int blastResistance;
    protected int points;
    protected boolean isCollidable;

    Block(int x, int y) {
        this.x = x;
        this.y = y;
        blastResistance = 0;
        isCollidable = false;
        points = 0;
    }

    public boolean isCollidable() {
        return isCollidable;
    }

    public int getPoints() {return points;}
    public int getX() {return x;}
    public int getY() {return y;}
    public BufferedImage getSprite() {return sprite;}
    public int getBlastResistance() {return blastResistance;}

    public void onExplosion(GameModel model) {
        //System.out.println("onExplosion " + x + " " + y);
    };
}
