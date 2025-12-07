package ui.priority;

import model.Task;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PriorityCellCustom extends JLabel implements ListCellRenderer<Task>{

    private static final Color RED = new Color(255, 150, 150);
    private static final Color ORANGE = new Color(255, 195, 140);
    private static final Color YELLOW = new Color(255, 245, 160);
    private static final Color EMPTY_SLOT_COLOR = new Color(240, 240, 240); // Light Gray
    private static final Color TEXT_COLOR = new Color(50, 50, 50);
    private static final Color[] background = { RED, ORANGE, YELLOW };

    public PriorityCellCustom(){
        setOpaque(true);
        setFont(new Font("SansSerif", Font.BOLD, 14));
        setBorder(new EmptyBorder(10, 8, 10, 8));
    }

    @Override
    public Component getListCellRendererComponent(
            JList<? extends Task> list,
            Task task,
            int index,
            boolean isSelected,
            boolean cellHasFocus
    ) {
        //number
        String prefix = (index + 1) + ". ";

        if(task != null){
            setText(prefix + task.getContent());
            setBackground(background[index]);
            setForeground(Color.WHITE);
        } else {
            setText(prefix + "(Empty)");
            setBackground(EMPTY_SLOT_COLOR);
            setForeground(TEXT_COLOR);
        }

        if(isSelected){
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        }
        return this;
    }
}
