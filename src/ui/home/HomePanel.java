package ui.home;

import model.TaskDataModel;
import ui.ideadump.IdeaDumpPanel;
import ui.priority.PriorityPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class HomePanel extends JPanel {
    public HomePanel(TaskDataModel model){
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));

        IdeaDumpPanel ideaDumpPanel = new IdeaDumpPanel(model);
        PriorityPanel priorityPanel = new PriorityPanel(model, ideaDumpPanel);

        JPanel leftContainer = createTaskAndPriorityContainer(ideaDumpPanel, priorityPanel);
        JPanel mm = new JPanel();
        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                leftContainer,
                mm
        );
        splitPane.setResizeWeight(0.5);
        SwingUtilities.invokeLater(() -> {
            splitPane.setDividerLocation(0.3);
        });

        add(splitPane, BorderLayout.CENTER);
    }

    private JPanel createTaskAndPriorityContainer(IdeaDumpPanel ideaDumpPanel, PriorityPanel priorityPanel) {
        JPanel container = new JPanel(new GridLayout(2, 1, 10, 10));
        container.setBorder(new EmptyBorder(0, 0, 0, 5));

        container.add(ideaDumpPanel);
        container.add(priorityPanel);

        return container;
    }
}
