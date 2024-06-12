package task3.view.menu;

import task3.controller.ClientController;
import task3.model.GameModel;
import task3.util.ResourceManager;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.NumberFormat;

public class MainMenu extends JPanel {
    private final String bgImageFilename = "img/menu_bg.png";
    private final BufferedImage bgImage;
    private final double buttonRatioCoefficient = 0.3;
    private final JFormattedTextField seedField;

    public MainMenu(JFrame parent, ClientController controller, GameModel model) {
        bgImage = ResourceManager.getSprite(bgImageFilename);
        this.setLayout(new GridBagLayout());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        buttonsPanel.setLayout(new GridLayout(3, 1, 10, 10));

        int buttonWidth = (int)(parent.getWidth() * buttonRatioCoefficient);

        NumberFormat format = NumberFormat.getIntegerInstance();
        format.setGroupingUsed(false);
        NumberFormatter numberFormatter = new NumberFormatter(format);
        numberFormatter.setValueClass(Long.class);
        numberFormatter.setAllowsInvalid(false);
        seedField = new JFormattedTextField(numberFormatter);
        seedField.setAlignmentX(Component.CENTER_ALIGNMENT);
        seedField.setText(String.valueOf(model.getCurrentSeed()));
        seedField.addPropertyChangeListener(
                (evt) -> {
                    controller.execute(
                            ClientController.OP.SET_SEED, model,
                            this.getSeed()
                    );
                }
        );
        buttonsPanel.add(seedField);

        JButton startButton = new JButton("START");
        startButton.setPreferredSize(new Dimension(buttonWidth, (int)(buttonWidth * buttonRatioCoefficient)));
        startButton.addActionListener((event)->{
            controller.execute(ClientController.OP.CHANGE_GAMESTATE, model, GameModel.GAMESTATE.INGAME);
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

    public long getSeed() {
        return Long.parseLong(this.seedField.getText());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
    }
}
