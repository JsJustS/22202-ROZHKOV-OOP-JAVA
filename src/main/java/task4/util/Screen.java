package task4.util;

import task4.Factory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class Screen implements IScreen{
    public Screen(Factory factory) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {}
                JFrame frame = new JFrame("Fabric");
                frame.setMinimumSize(new Dimension(500, 200));
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(new FabricPanel(factory));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });

    }

    private class FabricPanel extends JPanel {
        private static final String SPEED_BODY_TXT = "Body Speed: %d";
        private static final String SPEED_MOTOR_TXT = "Motor Speed: %d";
        private static final String SPEED_ACCESSORY_TXT = "Accessory Speed: %d";
        public FabricPanel(Factory factory) {
            this.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(15, 15, 15, 15);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JScrollBar bodyBar = new JScrollBar(Adjustable.HORIZONTAL, 100, 100, 100, 5000);
            bodyBar.addAdjustmentListener(new AdjustmentListener() {
                @Override
                public void adjustmentValueChanged(AdjustmentEvent e) {
                    factory.updateBodyCraftingSpeed(e.getValue());
                }
            });
            this.add(bodyBar, gbc);
            bodyBar.setPreferredSize(new Dimension(200, 20));
            gbc.gridx++;
            this.add(new JLabel(String.format(SPEED_BODY_TXT, bodyBar.getValue()), SwingConstants.LEFT), gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            JScrollBar motorBar = new JScrollBar(Adjustable.HORIZONTAL, 100, 100, 100, 5000);
            motorBar.addAdjustmentListener(new AdjustmentListener() {
                @Override
                public void adjustmentValueChanged(AdjustmentEvent e) {
                    factory.updateMotorCraftingSpeed(e.getValue());
                }
            });
            motorBar.setPreferredSize(new Dimension(200, 20));
            this.add(motorBar, gbc);
            gbc.gridx++;
            this.add(new JLabel(String.format(SPEED_MOTOR_TXT, motorBar.getValue()), SwingConstants.LEFT), gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            JScrollBar accessoryBar = new JScrollBar(Adjustable.HORIZONTAL, 100, 100, 100, 5000);
            accessoryBar.addAdjustmentListener(new AdjustmentListener() {
                @Override
                public void adjustmentValueChanged(AdjustmentEvent e) {
                    factory.updateAccessoryCraftingSpeed(e.getValue());
                }
            });
            accessoryBar.setPreferredSize(new Dimension(200, 20));
            this.add(accessoryBar, gbc);
            gbc.gridx++;
            this.add(new JLabel(String.format(SPEED_ACCESSORY_TXT, accessoryBar.getValue()), SwingConstants.LEFT), gbc);
        }
    }


    @Override
    public void updateBodyCount(int count) {

    }

    @Override
    public void updateAutoCount(int count) {

    }

    @Override
    public void updateMotorCount(int count) {

    }

    @Override
    public void updateAccessoryCount(int count) {

    }

    @Override
    public void updateGeneralCount(int count) {

    }
}
