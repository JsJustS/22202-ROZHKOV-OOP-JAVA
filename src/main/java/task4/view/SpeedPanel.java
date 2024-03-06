package task4.view;

import task4.controller.Controller;
import task4.model.World;
import task4.util.pubsub.ISubscriber;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class SpeedPanel extends JPanel {
    private final LabelPanel labelPanel;
    private final SliderPanel sliderPanel;

    public SpeedPanel(World world, Controller controller) {
        // 1. Create sliders firstly, so they can set values to model on <init>
        this.sliderPanel = new SliderPanel(world, controller);
        // 2. Create labels secondly, so they can load stored values from model on <init>
        this.labelPanel = new LabelPanel(world);
        world.subscribe(this.labelPanel);

        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        this.sliderPanel.setAlignmentX(SliderPanel.RIGHT_ALIGNMENT);
        this.add(sliderPanel);

        this.labelPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        this.labelPanel.setAlignmentX(LabelPanel.LEFT_ALIGNMENT);
        this.labelPanel.setHorizontalAlignment(JLabel.LEFT);
        this.add(labelPanel);

        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    private class LabelPanel extends JPanel implements ISubscriber {
        private final World world;
        private final JLabel bodyLabel;
        private final JLabel motorLabel;
        private final JLabel accessoryLabel;
        private final JLabel dealerLabel;

        private final HashMap<JLabel, String> FORMAT;
        private final HashMap<JLabel, World.Part> LABEL2PART;

        {
            this.FORMAT = new HashMap<>();
            this.LABEL2PART = new HashMap<>();
        }

        public LabelPanel(World world) {
            this.world = world;

            this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

            this.bodyLabel = new JLabel();
            this.motorLabel = new JLabel();
            this.accessoryLabel = new JLabel();
            this.dealerLabel = new JLabel();

            this.fillFormat();
            this.fillParts();

            this.add(bodyLabel);
            this.add(Box.createRigidArea(new Dimension(0, 5)));
            this.add(motorLabel);
            this.add(Box.createRigidArea(new Dimension(0, 5)));
            this.add(accessoryLabel);
            this.add(Box.createRigidArea(new Dimension(0, 5)));
            this.add(dealerLabel);

            // Have to load values from model on <init>
            this.onNotification();
        }

        public void setHorizontalAlignment(int alignment) {
            this.bodyLabel.setHorizontalAlignment(alignment);
            this.motorLabel.setHorizontalAlignment(alignment);
            this.accessoryLabel.setHorizontalAlignment(alignment);
            this.dealerLabel.setHorizontalAlignment(alignment);
        }

        public void setVerticalAlignment(int alignment) {
            this.bodyLabel.setVerticalAlignment(alignment);
            this.motorLabel.setVerticalAlignment(alignment);
            this.accessoryLabel.setVerticalAlignment(alignment);
            this.dealerLabel.setVerticalAlignment(alignment);
        }

        private void fillFormat() {
            this.FORMAT.put(this.bodyLabel, "Body Speed: %04d");
            this.FORMAT.put(this.motorLabel, "Motor Speed: %04d");
            this.FORMAT.put(this.accessoryLabel, "Accessory Speed: %04d");
            this.FORMAT.put(this.dealerLabel, "Dealers Speed: %04d");
        }

        private void fillParts() {
            this.LABEL2PART.put(this.bodyLabel, World.Part.BODY);
            this.LABEL2PART.put(this.motorLabel, World.Part.MOTOR);
            this.LABEL2PART.put(this.accessoryLabel, World.Part.ACCESSORY);
        }

        public void updateLabel(JLabel label) {
            if (!this.FORMAT.containsKey(label)) {
                throw new RuntimeException("Unsupported part");
            }
            label.setText(
                    String.format(
                            this.FORMAT.get(label),
                            ((this.LABEL2PART.containsKey(label))) ?
                            this.world.getCreationSpeed(this.LABEL2PART.get(label)) :
                            this.world.getDealersSpeed()
                    )
            );
        }

        @Override
        public void onNotification() {
            SwingUtilities.invokeLater(
                    ()->{
                        this.updateLabel(this.bodyLabel);
                        this.updateLabel(this.motorLabel);
                        this.updateLabel(this.accessoryLabel);
                        this.updateLabel(this.dealerLabel);
                    }
            );
        }
    }

    private class SliderPanel extends JPanel {
        private final World world;
        private final Controller controller;

        private final JSlider bodySlider;
        private final JSlider motorSlider;
        private final JSlider accessorySlider;
        private final JSlider dealersSlider;

        public SliderPanel(World world, Controller controller) {
            this.world = world;
            this.controller = controller;

            this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

            this.bodySlider = new JSlider(SwingConstants.HORIZONTAL, 100, 5000, 1000);
            this.motorSlider = new JSlider(SwingConstants.HORIZONTAL, 100, 5000, 1000);
            this.accessorySlider = new JSlider(SwingConstants.HORIZONTAL, 100, 5000, 1000);
            this.dealersSlider = new JSlider(SwingConstants.HORIZONTAL, 100, 5000, 1000);

            this.bodySlider.setPreferredSize(new Dimension(300, 20));
            this.motorSlider.setPreferredSize(new Dimension(300, 20));
            this.accessorySlider.setPreferredSize(new Dimension(300, 20));
            this.dealersSlider.setPreferredSize(new Dimension(300, 20));

            this.bodySlider.addChangeListener(
                    (e)->controller.execute(Controller.Operation.UPD_SPEED_BODY, world, this.bodySlider.getValue())
            );
            this.motorSlider.addChangeListener(
                    (e)->controller.execute(Controller.Operation.UPD_SPEED_MOTOR, world, this.motorSlider.getValue())
            );
            this.accessorySlider.addChangeListener(
                    (e)->controller.execute(Controller.Operation.UPD_SPEED_ACCESSORY, world, this.accessorySlider.getValue())
            );
            this.dealersSlider.addChangeListener(
                    (e)->controller.execute(Controller.Operation.UPD_SPEED_DEALERS, world, this.dealersSlider.getValue())
            );

            this.add(this.bodySlider);
            this.add(Box.createRigidArea(new Dimension(0, 5)));
            this.add(this.motorSlider);
            this.add(Box.createRigidArea(new Dimension(0, 5)));
            this.add(this.accessorySlider);
            this.add(Box.createRigidArea(new Dimension(0, 5)));
            this.add(this.dealersSlider);

            // Have to set values with controller on <init>
            controller.execute(Controller.Operation.UPD_SPEED_BODY, world, this.bodySlider.getValue());
            controller.execute(Controller.Operation.UPD_SPEED_MOTOR, world, this.motorSlider.getValue());
            controller.execute(Controller.Operation.UPD_SPEED_ACCESSORY, world, this.accessorySlider.getValue());
            controller.execute(Controller.Operation.UPD_SPEED_DEALERS, world, this.dealersSlider.getValue());
        }
    }
}
