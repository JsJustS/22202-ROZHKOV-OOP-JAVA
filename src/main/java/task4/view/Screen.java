package task4.view;

import task4.controller.Controller;
import task4.model.World;
import task4.util.pubsub.ISubscriber;

import javax.swing.*;
import java.awt.*;

public class Screen extends JFrame {

    public Screen(Controller controller, World world) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        this.setTitle("Fabric");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(500, 400));

        JPanel bodyPanel = new SpeedSlider("Body Speed: %d", controller, world, World.Part.BODY);
        controller.execute(Controller.Operation.UPD_SPEED_BODY, world, 1000);
        JPanel motorPanel = new SpeedSlider("Motor Speed: %d", controller, world, World.Part.MOTOR);
        controller.execute(Controller.Operation.UPD_SPEED_MOTOR, world, 1000);
        JPanel accessoryPanel = new SpeedSlider("Accessory Speed: %d", controller, world, World.Part.ACCESSORY);
        controller.execute(Controller.Operation.UPD_SPEED_ACCESSORY, world, 1000);
        world.subscribe((ISubscriber) bodyPanel);
        world.subscribe((ISubscriber) motorPanel);
        world.subscribe((ISubscriber) accessoryPanel);

        Container contentPane = getContentPane();

        JPanel sliderPane = new JPanel();
        sliderPane.setLayout(new BoxLayout(sliderPane, BoxLayout.PAGE_AXIS));
        sliderPane.add(bodyPanel);
        sliderPane.add(Box.createRigidArea(new Dimension(0, 5)));
        sliderPane.add(motorPanel);
        sliderPane.add(Box.createRigidArea(new Dimension(0, 5)));
        sliderPane.add(accessoryPanel);

        contentPane.add(sliderPane, BorderLayout.PAGE_START);
        //contentPane.add(motorPanel, BorderLayout.PAGE_START);
        //contentPane.add(accessoryPanel, BorderLayout.PAGE_START);

        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
