package task3.view.gameplay;

import task3.model.GameModel;
import task3.model.entity.EntityModel;
import task3.model.entity.RenderLayer;
import task3.util.ResourceManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class FieldPanel extends JPanel {
    private final GameModel model;
    private final int intents = 75;
    public FieldPanel(GameModel model) {
        this.model = model;
    }

    @Override
    public Dimension getPreferredSize() {
        int best = Math.min(super.getParent().getWidth() - intents, super.getParent().getHeight() - intents);
        return new Dimension(best, best);
    }

    @Override
    public void revalidate() {
        super.revalidate();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());

        if (!model.isMapReady()) return;

        renderEntities(g);
    }

    private void renderEntities(Graphics g) {
        RenderLayer[] layersToRender = RenderLayer.values();
        for (RenderLayer layer : layersToRender) {
            renderEntitiesLayer(g, layer);
        }
    }

    private void renderEntitiesLayer(Graphics g, RenderLayer layer) {
        int widthPerBlock = getWidth() / model.getFieldWidthInBlocks();
        int heightPerBlock = getHeight() / model.getFieldHeightInBlocks();
        int spareVerticalPixels = getWidth() - (widthPerBlock * model.getFieldWidthInBlocks());
        int spareHorizontalPixels = getHeight() - (heightPerBlock * model.getFieldHeightInBlocks());

        for (EntityModel entity : model.getEntities()) {
            if (entity.getRenderLayer() != layer) continue;

            int w = (entity.getX() < spareVerticalPixels) ? widthPerBlock + 1 : widthPerBlock;
            int h = (entity.getY() < spareHorizontalPixels) ? heightPerBlock + 1 : heightPerBlock;
            int x = widthPerBlock * (int)entity.getX() + Math.min((int)entity.getX(), spareVerticalPixels);
            int y = heightPerBlock * (int)entity.getY() + Math.min((int)entity.getY(), spareHorizontalPixels);
            int spriteWidth = (int)(w*entity.getHitboxWidth());
            int spriteHeight = (int)(h*entity.getHitboxHeight());
            int spriteX = (int)(x+w*(entity.getX()-(int)entity.getX())-spriteWidth/2);
            int spriteY = (int)(y+h*(entity.getY()-(int)entity.getY())-spriteHeight/2);

            BufferedImage sprite = (entity.getSpritePath() != null) ?
                    ResourceManager.getSprite(entity.getSpritePath()) :
                    ResourceManager.getMissingTexture();
            g.drawImage(sprite, spriteX, spriteY, spriteWidth, spriteHeight,this);
        }
    }
}
