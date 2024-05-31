package task3.view.gameplay;

import task3.engine.block.Block;
import task3.model.ClientModel;
import task3.view.pubsub.ISubscriber;

import javax.swing.*;
import java.awt.*;

public class FieldPanel extends JPanel implements ISubscriber {
    private final ClientModel model;
    public FieldPanel(ClientModel model) {
        this.model = model;
        //this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 20));
    }

    @Override
    public void revalidate() {
        //this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 20));
        super.revalidate();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        int widthPerBlock = getWidth() / model.getFieldWidthInBlocks();
        int heightPerBlock = getHeight() / model.getFieldHeightInBlocks();
        int spareVerticalPixels = getWidth() - (widthPerBlock * model.getFieldWidthInBlocks());
        int spareHorizontalPixels = getHeight() - (heightPerBlock * model.getFieldHeightInBlocks());
        System.out.println(getWidth() +"|"+ getHeight());
        System.out.println(widthPerBlock +"|"+ heightPerBlock);
        System.out.println(spareVerticalPixels +"|"+ spareHorizontalPixels);

        for (Block block : model.getBlocks()) {
            System.out.println("--------------");
            int w = (block.getX() < spareVerticalPixels) ? widthPerBlock : widthPerBlock + 1;
            int h = (block.getY() < spareHorizontalPixels) ? heightPerBlock : heightPerBlock + 1;
            //int x = Math.min(spareVerticalPixels, block.getX()) * (widthPerBlock + 1) + widthPerBlock * Math.max(0, (spareVerticalPixels - block.getX()));
            //int y = Math.min(spareHorizontalPixels, block.getY()) * (heightPerBlock + 1) + heightPerBlock * Math.max(0, (spareHorizontalPixels - block.getY()));
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
            System.out.println(w +"|"+ h);
            System.out.println(x +"|"+ y);
            g.drawImage(block.getSprite(), x, y, w, h, this);
        }
        System.out.println("==============");
    }

    @Override
    public void onNotification() {

    }
}
