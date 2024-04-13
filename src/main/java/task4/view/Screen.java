package task4.view;

import task4.controller.UIController;
import task4.model.World;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Screen extends JFrame {

    public Screen(UIController controller, World world) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        this.setTitle("Factory");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(500, 400));

        Container contentPane = getContentPane();

        SpeedPanel speedPanel = new SpeedPanel(world, controller);
        contentPane.add(speedPanel, BorderLayout.PAGE_START);
        StatPanel statPanel = new StatPanel(world);
        contentPane.add(statPanel, BorderLayout.PAGE_END);

        try (InputStream stream = Screen.class.getResourceAsStream("/themostimportantpart.jpg")) {
            if (stream == null) throw new IOException();
            BufferedImage veryImportant = ImageIO.read(stream);
            JLabel picLabel = new JLabel(new ImageIcon(veryImportant));
            contentPane.add(picLabel, BorderLayout.CENTER);
        } catch (IOException ignored) {}

        try (InputStream stream = Screen.class.getResourceAsStream("/icon.png")) {
            if (stream == null) throw new IOException();
            BufferedImage icon = ImageIO.read(stream);
            this.setIconImage(icon);
        } catch (IOException ignored) {}

        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
