package task3.view.gameplay;

import task3.model.ClientModel;

import javax.swing.*;
import java.awt.*;

public class GUIPanel extends JPanel {
    private final JLabel pointsLabel;
    private final ClientModel clientModel;

    public GUIPanel(ClientModel clientModel) {
        this.clientModel = clientModel;
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        this.pointsLabel = new JLabel();
        this.add(this.pointsLabel, gbc);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(super.getParent().getWidth()/5, super.getParent().getHeight());
    }

    @Override
    public void revalidate() {
        if (clientModel != null && clientModel.getMainPlayer() != null) {
            this.pointsLabel.setText("POINTS: " + clientModel.getMainPlayer().getPoints());
        }
        super.revalidate();
    }
}
