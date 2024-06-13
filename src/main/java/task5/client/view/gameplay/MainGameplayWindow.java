package task5.client.view.gameplay;

import task5.client.controller.ClientController;
import task5.model.GameModel;
import task5.model.abilityInstance.Ability;
import task5.util.ResourceManager;
import task5.client.keyboard.KeyBindManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGameplayWindow extends JPanel implements ActionListener {
    private final FieldPanel field;
    private final KeyBindManager keyBindManager;
    private final GameModel clientModel;
    private final String bombSprite = "task5/img/entity/bomb/bomb_1.png";
    private final String superBombSprite = "task5/img/entity/bomb/super_bomb_1.png";

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
        if (clientModel == null || clientModel.getMainPlayer() == null) return;

        g.setFont(new Font("Times New Roman", Font.BOLD, 25));
        int x = 10;
        int y = 30;

        // Points UI
        if (clientModel.getMainPlayer().isAlive()) {
            g.setColor(Color.WHITE);
            g.drawString("POINTS: " + clientModel.getMainPlayer().getPoints(), x, y);
            x = this.getParent().getWidth() / 2 - 20;
            if (clientModel.getMainPlayer().getPoints() < clientModel.getPointsForWin()) {
                g.drawString("POINTS FOR WIN: " + clientModel.getPointsForWin(), x, y);
            } else {
                g.setColor(Color.GREEN);
                g.drawString("YOU WON!", x, y);
            }
        } else {
            g.setColor(Color.RED);
            g.drawString("YOU DIED!", x, y);
        }

        // Ability UI
        x = this.getWidth() - 5;
        y = 5;
        int size = 30;

        Ability chosenAbility = clientModel.getMainPlayer().getAbility();
        for (int i = 0; i < clientModel.getMainPlayer().getBombsLeft(); ++i) {
            switch (chosenAbility) {
                case SIMPLE_BOMB:
                    g.drawImage(ResourceManager.getSprite(bombSprite), x - size * (i + 1) - size / 5 * i, y, size, size, this);
                    break;
                case SUPER_BOMB:
                    g.drawImage(ResourceManager.getSprite(superBombSprite), x - size * (i + 1) - size / 5 * i, y, size, size, this);
                    break;
            }
        }

        // Seed UI
        x = 10;
        y = this.getParent().getHeight() - 10;
        g.setColor(Color.WHITE);
        g.drawString("SEED: " + clientModel.getCurrentSeed(), x, y);

        // Time UI
        x = this.getParent().getWidth() - 250;
        y = this.getParent().getHeight() - 10;
        g.setColor(Color.WHITE);
        g.drawString("TIME LEFT: " + clientModel.getRoundTicksLeft() / clientModel.getTicksPerSecond(), x, y);
    }
}
