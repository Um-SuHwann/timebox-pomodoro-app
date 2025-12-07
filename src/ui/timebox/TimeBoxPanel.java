package ui.timebox;

import model.TaskDataModel;

import javax.swing.*;
import java.awt.*;

public class TimeBoxPanel extends JPanel {
    private final TaskDataModel model;

    public TimeBoxPanel(TaskDataModel model) {
        this.model = model;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("3. Time Box"));

        HourLabelPanel hourLabelPanel = new HourLabelPanel();
        add(hourLabelPanel, BorderLayout.WEST);


        TimeHeaderPanel headerPanel = new TimeHeaderPanel();
        TimeGridPanel timeGridPanel = new TimeGridPanel(model);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(timeGridPanel, BorderLayout.CENTER);

        add(panel, BorderLayout.CENTER);
    }
}
