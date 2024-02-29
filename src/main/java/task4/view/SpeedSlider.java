package task4.view;

import task4.controller.Controller;
import task4.model.World;
import task4.util.pubsub.ISubscriber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

class SpeedSlider extends JPanel implements ISubscriber {
    private final String format;
    private final World world;
    private final World.Part part;
    private final JScrollBar scrollBar;
    private final JLabel label;

    public
    SpeedSlider(String format, Controller controller, World world, World.Part part) {
        this.world = world;
        this.part = part;
        this.format = format;
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        this.scrollBar = new JScrollBar(Adjustable.HORIZONTAL, 1000, 100, 100, 5100);
        this.scrollBar.setPreferredSize(new Dimension(300, 20));
        this.scrollBar.setMaximumSize(new Dimension(300, 20));
        this.scrollBar.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                switch (part) {
                    case BODY: controller.execute(Controller.Operation.UPD_SPEED_BODY, world, scrollBar.getValue()); return;
                    case MOTOR: controller.execute(Controller.Operation.UPD_SPEED_MOTOR, world, scrollBar.getValue()); return;
                    case ACCESSORY: controller.execute(Controller.Operation.UPD_SPEED_ACCESSORY, world, scrollBar.getValue()); return;
                }
                throw new RuntimeException("Undefined part");
            }
        });

        this.scrollBar.setAlignmentX(JScrollBar.LEFT_ALIGNMENT);
        this.scrollBar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        this.add(this.scrollBar);

        this.label = new JLabel(String.format(this.format, this.scrollBar.getValue()), SwingConstants.LEFT);
        this.label.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
        this.add(this.label);

        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    @Override
    public void onNotification() {
        SwingUtilities.invokeLater(
                ()->this.label.setText(String.format(this.format, this.world.getCreationSpeed(part)))
        );
    }
}
