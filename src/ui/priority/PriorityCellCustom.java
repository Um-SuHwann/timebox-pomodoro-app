package ui.priority;

import model.Task;

import javax.swing.*;
import java.awt.*;

public class PriorityCellCustom extends DefaultListCellRenderer {

    private static final Color COLOR_1 = new Color(255, 102, 102); // Red
    private static final Color COLOR_2 = new Color(255, 178, 102); // Orange
    private static final Color COLOR_3 = new Color(255, 255, 102); // Yellow
    private static final Font TASK_FONT = new Font("SansSerif", Font.BOLD, 16);

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        setFont(TASK_FONT);
        setForeground(new Color(10,10,10));

        String text;
        if (value instanceof Task) {
            text = " " + (index + 1) + ". " + ((Task) value).getContent();
        } else {
            text = " " + (index + 1) + ". ---";
        }
        setText(text);

        switch (index) {
            case 0:
                setBackground(COLOR_1);
                break;
            case 1:
                setBackground(COLOR_2);
                break;
            case 2:
                setBackground(COLOR_3);
                break;
            default:
                setBackground(list.getBackground());
                setForeground(list.getForeground());
        }

        setPreferredSize(new Dimension(getPreferredSize().width, 40));

        return this;
    }
}