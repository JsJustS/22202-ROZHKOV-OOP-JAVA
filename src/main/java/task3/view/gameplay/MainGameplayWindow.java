package task3.view.gameplay;

import task3.controller.ClientController;
import task3.model.GameModel;
import task3.model.abilityInstance.Ability;
import task3.util.ResourceManager;
import task3.util.keyboard.KeyBindManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGameplayWindow extends JPanel implements ActionListener {
    private final FieldPanel field;
    private final KeyBindManager keyBindManager;
    private final GameModel clientModel;
    private final String bombSprite = "img/entity/bomb/bomb.png";
    private final String superBombSprite = "img/entity/bomb/super_bomb.png";

    public MainGameplayWindow(JFrame parent, ClientController controller, GameModel model) {
        Timer timer = new Timer(1000/60, this);
        timer.start();
        this.clientModel = model;

        this.setBackground(Color.DARK_GRAY);
        this.setLayout(new GridBagLayout());

        this.setLayout(new GridBagLayout());
        field = new FieldPanel(model);
        this.add(field);

        keyBindManager = new KeyBindManager(this, controller, model);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (clientModel != null && clientModel.getMainPlayer() != null) {

            g.setFont(new Font("Times New Roman", Font.BOLD, 25));
            int x = 10;
            int y = 30;

            if (clientModel.getMainPlayer().getPoints() >= 0) {
                g.setColor(Color.WHITE);
                g.drawString("POINTS: " + clientModel.getMainPlayer().getPoints(), x, y);
            } else {
                g.setColor(Color.RED);
                g.drawString("YOU DIED!", x, y);
            }

            x = this.getWidth() - 5;
            y = 5;
            int size = 30;

            Ability chosenAbility = clientModel.getMainPlayer().getAbility();
            for (int i = 0; i < clientModel.getMainPlayer().getBombsLeft(); ++i) {
                switch (chosenAbility) {
                    case SIMPLE_BOMB:
                        g.drawImage(ResourceManager.getSprite(bombSprite),x-size*(i+1)-size/5*i, y, size, size, this);
                        break;
                    case SUPER_BOMB:
                        g.drawImage(ResourceManager.getSprite(superBombSprite),x-size*(i+1)-size/5*i, y, size, size, this);
                        break;
                }
            }

        }
    }
}
