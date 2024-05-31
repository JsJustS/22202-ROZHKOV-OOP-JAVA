package task3.engine.block;

import java.awt.image.BufferedImage;

public abstract class Block {
    protected int x;
    protected int y;
    protected int wSprite;
    protected int hSprite;
    protected BufferedImage sprite;
    protected double blastResistance;
    protected boolean isCollidable;

    Block(int x, int y) {

    }
}
