package task5.client.view.menu;

import task5.client.controller.ClientController;
import task5.model.GameModel;
import task5.util.Pair;
import task5.util.ResourceManager;

import javax.swing.*;
import javax.swing.text.DefaultFormatter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class MainMenu extends JPanel {
    private final String bgImageFilename = "task5/img/menu_bg.png";
    private final BufferedImage bgImage;
    private final double buttonRatioCoefficient = 0.3;
    private final JFormattedTextField addressField;

    public MainMenu(JFrame parent, ClientController controller, GameModel model) {
        bgImage = ResourceManager.getSprite(bgImageFilename);
        this.setLayout(new GridBagLayout());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        buttonsPanel.setLayout(new GridLayout(3, 1, 10, 10));

        int buttonWidth = (int)(parent.getWidth() * buttonRatioCoefficient);

        addressField = new JFormattedTextField(new RegexFormatter("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5]):((6553[0-5])|(655[0-2][0-9])|(65[0-4][0-9]{2})|(6[0-4][0-9]{3})|([1-5][0-9]{4})|([0-5]{0,5})|([0-9]{1,4}))$"));
        addressField.setAlignmentX(Component.CENTER_ALIGNMENT);
        addressField.setText(String.valueOf(model.getCurrentSeed()));
        addressField.addPropertyChangeListener(
                (evt) -> {
                    controller.execute(
                            ClientController.OP.SET_IP, model,
                            this.parseAddress()
                    );
                    System.out.println(this.parseAddress());
                }
        );
        buttonsPanel.add(addressField);

        JButton startButton = new JButton("CONNECT");
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

    private Pair<String, Integer> parseAddress() {
        String addressString = addressField.getText();
        int delIndex = addressString.indexOf(":");
        if (delIndex == -1 || addressString.endsWith(":")) {
            int standardPort = 25500;
            return new Pair<>(addressString, standardPort);
        }

        return new Pair<>(
                addressString.substring(0, delIndex),
                Integer.parseInt(addressString.substring(delIndex+1))
        );
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
    }

    private static class RegexFormatter extends DefaultFormatter {
        private final Pattern pattern;

        public RegexFormatter(String pattern) throws PatternSyntaxException {
            super();
            this.pattern = (Pattern.compile(pattern));
        }

        public Object stringToValue(String text) throws ParseException {
            if (this.pattern != null) {
                Matcher matcher = this.pattern.matcher(text);

                if (matcher.matches()) {
                    return super.stringToValue(text);
                }
                throw new ParseException("Pattern did not match", 0);
            }
            return text;
        }
    }
}
