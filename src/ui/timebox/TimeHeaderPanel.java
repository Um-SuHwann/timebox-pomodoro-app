package ui.timebox;

import javax.swing.*;
import java.awt.*;

public class TimeHeaderPanel extends JPanel {
    public static final int LABEL_HEIGHT = 30;

    public TimeHeaderPanel() {
        setLayout(new GridLayout(1, 2));
        setPreferredSize(new Dimension(getPreferredSize().width, LABEL_HEIGHT)); // Only fix the width

        JLabel label1 = new JLabel(":00", SwingConstants.CENTER);
        JLabel label2 = new JLabel(":30", SwingConstants.CENTER);

        label1.setOpaque(true);
        label2.setOpaque(true);
        label1.setBackground(new Color(50, 50, 50));
        label2.setBackground(new Color(50, 50, 50));
        label1.setForeground(Color.WHITE);
        label2.setForeground(Color.WHITE);

        label1.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.GRAY));
        label2.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.GRAY));

        add(label1);
        add(label2);
    }
}
