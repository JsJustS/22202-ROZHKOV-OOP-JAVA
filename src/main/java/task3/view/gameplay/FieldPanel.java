package task3.view.gameplay;

import task3.engine.block.Block;
import task3.model.ClientModel;
import task3.util.pubsub.ISubscriber;

import javax.swing.*;
import java.awt.*;

public class FieldPanel extends JPanel implements ISubscriber {
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
            int w = (block.getX() < spareVerticalPixels) ? widthPerBlock : widthPerBlock + 1;
            int h = (block.getY() < spareHorizontalPixels) ? heightPerBlock : heightPerBlock + 1;
            int x, y;
            if (block.getX() > spareVerticalPixels) {
                x = (widthPerBlock+1)*spareHorizontalPixels + widthPerBlock*(block.getX()-spareVerticalPixels);
            } else {
                x = (widthPerBlock+1)*block.getX();
            }
            if (block.getY() > spareHorizontalPixels) {
                y = (heightPerBlock+1)*spareHorizontalPixels + heightPerBlock*(block.getY()-spareHorizontalPixels);
            } else {
                y = (heightPerBlock+1)*block.getY();
            }
            g.drawImage(block.getSprite(), x, y, w, h, this);
        }
    }

    @Override
    public void onNotification() {

    }
}
