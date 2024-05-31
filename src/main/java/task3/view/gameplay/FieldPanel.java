package task3.view.gameplay;

import task3.view.pubsub.ISubscriber;

import javax.swing.*;
import java.awt.*;

public class FieldPanel extends JPanel implements ISubscriber {
    public FieldPanel() {
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 20));
    }

    @Override
    public void revalidate() {
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 20));
        super.revalidate();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    @Override
    public void onNotification() {

    }
}
