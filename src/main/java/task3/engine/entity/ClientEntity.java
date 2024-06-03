package task3.engine.entity;

import task3.util.ResourceManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class ClientEntity extends Entity {
    protected BufferedImage sprite = ResourceManager.getMissingTexture();
    // Note:
    // xSprite and ySprite are coordinates on SpriteSheet.
    // They should mark the start coordinate of the Sprite (Up-Left corner) in pixels
    // wSprite and hSprite are width and height of the sprite.
    protected int xSprite;
    protected int ySprite;
    protected double wSprite;
    protected double hSprite;

    ClientEntity() {
        super();
    }

    public void render(JPanel canvas, Graphics g) {
        // 1. Translate entity coordinates in Window coordinates
        // 2. Translate Window coordinates to sprite coordinates
        g.drawImage(this.sprite, xSprite, ySprite, (int)wSprite, (int)hSprite, canvas);
    }
}
