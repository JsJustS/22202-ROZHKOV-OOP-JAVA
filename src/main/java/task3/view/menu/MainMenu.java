package task3.view.menu;

import task3.controller.ClientController;
import task3.model.ClientModel;
import task3.util.ResourceManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MainMenu extends JPanel {
    private final String bgImageFilename = "img/menu_bg.png";
    private final BufferedImage bgImage;
    private final double buttonRatioCoefficient = 0.3;

    public MainMenu(JFrame parent, ClientController controller, ClientModel model) {
        bgImage = ResourceManager.loadImage(bgImageFilename);
        this.setLayout(new GridBagLayout());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        buttonsPanel.setLayout(new GridLayout(2, 1, 10, 10));

        JButton startButton = new JButton("START");
        int buttonWidth = (int)(parent.getWidth() * buttonRatioCoefficient);
        startButton.setPreferredSize(new Dimension(buttonWidth, (int)(buttonWidth * buttonRatioCoefficient)));
        startButton.addActionListener((event)->{
            controller.execute(ClientController.OP.CHANGE_GAMESTATE, model, ClientModel.GAMESTATE.INGAME);
        });
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setHorizontalTextPosition(SwingConstants.CENTER);
        buttonsPanel.add(startButton);

        JButton exitButton = new JButton("EXIT");
        exitButton.addActionListener((event)->{
            System.exit(0);
        });
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setHorizontalTextPosition(SwingConstants.CENTER);
        buttonsPanel.add(exitButton);

        this.add(buttonsPanel);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
    }
}
