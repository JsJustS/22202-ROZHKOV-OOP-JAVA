package task4.view;

import task4.controller.Controller;
import task4.model.World;

import javax.swing.*;
import java.awt.*;

public class Screen extends JFrame {

    public Screen(Controller controller, World world) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        this.setTitle("Factory");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(500, 400));

        Container contentPane = getContentPane();

        SpeedPanel speedPanel = new SpeedPanel(world, controller);
        contentPane.add(speedPanel, BorderLayout.PAGE_START);

        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
