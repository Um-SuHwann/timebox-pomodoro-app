package ui.timebox;

import javax.swing.*;
import java.awt.*;

public class HourLabelPanel extends JPanel {
    public static final int LABEL_WIDTH = 30;
    private static final int START_HOUR = 5;
    private static final int END_HOUR = 23;

    public HourLabelPanel() {
        int hours = END_HOUR - START_HOUR + 1;
        setLayout(new GridLayout(hours + 1, 1));
        setPreferredSize(new Dimension(LABEL_WIDTH, getPreferredSize().height)); // Only fix the width

        JLabel initial = new JLabel();
        initial.setOpaque(true);
        initial.setBackground(new Color(50, 50, 50));
        initial.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.GRAY));
        add(initial);

        for (int i = START_HOUR; i <= END_HOUR; i++) {
            JLabel label = new JLabel(i + "", SwingConstants.CENTER);
            label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.GRAY));
            add(label);
        }
    }
}
