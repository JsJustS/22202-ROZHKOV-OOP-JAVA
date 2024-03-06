package task4.view;

import task4.model.World;
import task4.util.pubsub.ISubscriber;

import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.util.HashMap;

public class StatPanel extends JPanel {
    private StoragePanel storagePanel;
    private CraftedPanel craftedPanel;
    private ThreadPanel threadPanel;
    public StatPanel(World world) {
        this.storagePanel = new StoragePanel(world);
        world.subscribe(this.storagePanel);
        this.storagePanel.onNotification();

        this.craftedPanel = new CraftedPanel(world);
        world.subscribe(this.craftedPanel);
        this.craftedPanel.onNotification();

        this.threadPanel = new ThreadPanel(world);
        world.subscribe(this.threadPanel);
        this.threadPanel.onNotification();

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        storagePanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        panel.add(storagePanel);
        craftedPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        panel.add(craftedPanel);

        this.add(panel, BorderLayout.PAGE_START);
        this.add(threadPanel, BorderLayout.PAGE_END);

        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    private final class ThreadPanel extends JPanel implements ISubscriber {
        private final World world;
        private final JLabel label;
        private final String format = "Tasks waiting: %d";

        public ThreadPanel(World world) {
            this.world = world;
            this.label = new JLabel();
            this.add(this.label);
        }

        @Override
        public void onNotification() {
            SwingUtilities.invokeLater(()->this.label.setText(String.format(this.format, this.world.getTasksWaitingCount())));
        }
    }

    private class StatInnerPanel extends JPanel implements ISubscriber {
        protected final World world;
        protected final HashMap<JLabel, String> FORMAT;

        protected final JLabel bodyLabel;
        protected final JLabel motorLabel;
        protected final JLabel accessoryLabel;
        protected final JLabel carLabel;

        {
            this.FORMAT = new HashMap<>();
        }

        public StatInnerPanel(World world) {
            this.world = world;

            this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

            this.bodyLabel = new JLabel();
            this.motorLabel = new JLabel();
            this.accessoryLabel = new JLabel();
            this.carLabel = new JLabel();

            this.add(bodyLabel);
            this.add(Box.createRigidArea(new Dimension(0, 5)));
            this.add(motorLabel);
            this.add(Box.createRigidArea(new Dimension(0, 5)));
            this.add(accessoryLabel);
            this.add(Box.createRigidArea(new Dimension(0, 5)));
            this.add(carLabel);
        }

        @Override
        public void onNotification() {
            SwingUtilities.invokeLater(()->{
                for (Component component : this.getComponents()) {
                    if (!(component instanceof JLabel)) continue;
                    JLabel label = (JLabel) component;
                    label.setText(String.format(this.FORMAT.getOrDefault(label, "undefined: %d"), this.getValueFromWorld(label)));
                }
            });
        }

        protected int getValueFromWorld(JLabel label) {
            throw new RuntimeException("Unsupported label");
        }
    }

    private final class StoragePanel extends StatInnerPanel {

        public StoragePanel(World world) {
            super(world);
            this.fillFormat();
        }

        private void fillFormat() {
            this.FORMAT.put(this.bodyLabel, "Bodies Stored: %d");
            this.FORMAT.put(this.motorLabel, "Motors Stored: %d");
            this.FORMAT.put(this.accessoryLabel, "Accessories Stored: %d");
            this.FORMAT.put(this.carLabel, "Cars Stored: %d");
        }

        @Override
        protected int getValueFromWorld(JLabel label) {
            if (label.equals(this.bodyLabel)) return this.world.getBodyStoredCount();
            if (label.equals(this.motorLabel)) return this.world.getMotorStoredCount();
            if (label.equals(this.accessoryLabel)) return this.world.getAccessoryStoredCount();
            if (label.equals(this.carLabel)) return this.world.getCarsStoredCount();
            return super.getValueFromWorld(label);
        }
    }

    private final class CraftedPanel extends StatInnerPanel {

        public CraftedPanel(World world) {
            super(world);
            this.fillFormat();
        }

        private void fillFormat() {
            this.FORMAT.put(this.bodyLabel, "Bodies Crafted: %d");
            this.FORMAT.put(this.motorLabel, "Motors Crafted: %d");
            this.FORMAT.put(this.accessoryLabel, "Accessories Crafted: %d");
            this.FORMAT.put(this.carLabel, "Cars Crafted: %d");
        }

        @Override
        protected int getValueFromWorld(JLabel label) {
            if (label.equals(this.bodyLabel)) return this.world.getBodyCraftedCount();
            if (label.equals(this.motorLabel)) return this.world.getMotorCraftedCount();
            if (label.equals(this.accessoryLabel)) return this.world.getAccessoryCraftedCount();
            if (label.equals(this.carLabel)) return this.world.getCarsCraftedCount();
            return super.getValueFromWorld(label);
        }
    }
}
