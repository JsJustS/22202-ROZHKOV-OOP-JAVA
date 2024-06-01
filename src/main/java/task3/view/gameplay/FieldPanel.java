package task3.view.gameplay;

import task3.engine.block.Block;
import task3.model.ClientModel;
import task3.util.keyboard.KeyBindManager;
import task3.util.pubsub.ISubscriber;

import javax.swing.*;
import java.awt.*;

public class FieldPanel extends JPanel {
    private final ClientModel model;
    public FieldPanel(ClientModel model) {
        this.model = model;
    }

    @Override
    public void revalidate() {
        super.revalidate();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        if (!model.isMapReady()) return;

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
