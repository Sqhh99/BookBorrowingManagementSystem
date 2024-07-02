package GUI;

import javax.swing.*;
import java.awt.*;

public class GroupBox extends JPanel {
    private final JPanel contentPanel;
    public GroupBox(String title) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(title),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(0, 1, 5, 5));
        add(contentPanel, BorderLayout.CENTER);
    }
    public void addComponent(Component component) {
        contentPanel.add(component);
    }
}
