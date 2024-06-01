package task3.view.gameplay;

import task3.controller.ClientController;
import task3.controller.KeyboardHandler;
import task3.model.ClientModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGameplayWindow extends JPanel implements ActionListener {
    private final FieldPanel field;
    private final KeyboardHandler keyboardHandler;

    public MainGameplayWindow(JFrame parent, ClientController controller, ClientModel model) {
        Timer timer = new Timer(1000/60, this);
        timer.start();

        this.setBackground(Color.DARK_GRAY);
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.insets = new Insets(50, 50, 50, 50);
        gbc.fill = GridBagConstraints.BOTH;

        field = new FieldPanel(model);
        //model.subscribe(field);
        this.add(field, gbc);

        keyboardHandler = new KeyboardHandler(this, controller, model);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

    }
}
