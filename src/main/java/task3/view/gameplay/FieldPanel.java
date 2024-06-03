package task3.view.gameplay;

import task3.engine.block.Block;
import task3.engine.entity.Entity;
import task3.model.ClientModel;

import javax.swing.*;
import java.awt.*;

public class FieldPanel extends JPanel {
    private final ClientModel model;
    public FieldPanel(ClientModel model) {
        this.model = model;
    }

    @Override
    public Dimension getPreferredSize() {
        int best = Math.min(super.getParent().getWidth(), super.getParent().getHeight());
        return new Dimension(best, best);
    }

    @Override
    public void revalidate() {
        super.revalidate();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.PINK);
        g.fillRect(0, 0, getWidth(), getHeight());

        if (!model.isMapReady()) return;

        renderBlocksLayer(g);
        renderEntitiesLayer(g);
    }

    private void renderEntitiesLayer(Graphics g) {
        int widthPerBlock = getWidth() / model.getFieldWidthInBlocks();
        int heightPerBlock = getHeight() / model.getFieldHeightInBlocks();
        int spareVerticalPixels = getWidth() - (widthPerBlock * model.getFieldWidthInBlocks());
        int spareHorizontalPixels = getHeight() - (heightPerBlock * model.getFieldHeightInBlocks());

        for (Entity entity : model.getClientEntities()) {
            int w = (entity.getX() < spareVerticalPixels) ? widthPerBlock + 1 : widthPerBlock;
            int h = (entity.getY() < spareHorizontalPixels) ? heightPerBlock + 1 : heightPerBlock;
            int x = widthPerBlock * (int)entity.getX() + Math.min((int)entity.getX(), spareVerticalPixels);
            int y = heightPerBlock * (int)entity.getY() + Math.min((int)entity.getY(), spareHorizontalPixels);
            int spriteWidth = (int)(w*entity.getHitboxWidth());
            int spriteHeight = (int)(h*entity.getHitboxHeight());
            int spriteX = (int)(x+w*(entity.getX()-(int)entity.getX())-spriteWidth/2);
            int spriteY = (int)(y+h*(entity.getY()-(int)entity.getY())-spriteHeight/2);
            g.drawImage(entity.getSprite(), spriteX, spriteY, spriteWidth, spriteHeight,this);
        }
    }

    private void renderBlocksLayer(Graphics g) {
        int widthPerBlock = getWidth() / model.getFieldWidthInBlocks();
        int heightPerBlock = getHeight() / model.getFieldHeightInBlocks();
        int spareVerticalPixels = getWidth() - (widthPerBlock * model.getFieldWidthInBlocks());
        int spareHorizontalPixels = getHeight() - (heightPerBlock * model.getFieldHeightInBlocks());

        for (Block block : model.getBlocks()) {
            int w = (block.getX() < spareVerticalPixels) ? widthPerBlock + 1 : widthPerBlock;
            int h = (block.getY() < spareHorizontalPixels) ? heightPerBlock + 1 : heightPerBlock;
            int x = widthPerBlock * block.getX() + Math.min(block.getX(), spareVerticalPixels);
            int y = heightPerBlock * block.getY() + Math.min(block.getY(), spareHorizontalPixels);
            g.drawImage(block.getSprite(), x, y, w, h, this);
        }
    }
}
