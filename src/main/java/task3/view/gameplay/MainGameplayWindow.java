package task3.view.gameplay;

import task3.controller.ClientController;
import task3.util.keyboard.KeyBindManager;
import task3.model.ClientModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGameplayWindow extends JPanel implements ActionListener {
    private final FieldPanel field;
    private final KeyBindManager keyBindManager;
    private final ClientModel clientModel;

    public MainGameplayWindow(JFrame parent, ClientController controller, ClientModel model) {
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
            g.setColor(new Color(255, 255, 255));
            g.setFont(new Font("Times New Roman", Font.BOLD, 25));
            int x = 10;
            int y = 30;

            g.drawString("POINTS: " + clientModel.getMainPlayer().getPoints(), x, y);
        }
    }
}
