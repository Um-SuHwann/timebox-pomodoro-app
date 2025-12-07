package ui.home;

import model.TaskDataModel;
import ui.ideadump.IdeaDumpPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class HomePanel extends JPanel {
    public HomePanel(TaskDataModel model){
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));

        IdeaDumpPanel ideaDumpPanel = new IdeaDumpPanel(model);

        JPanel leftContainer = createTaskAndPriorityContainer(ideaDumpPanel);

        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                leftContainer,
                ideaDumpPanel
        );
        splitPane.setResizeWeight(0.5);
        splitPane.setDividerLocation(600);

        add(splitPane, BorderLayout.CENTER);
    }

    private JPanel createTaskAndPriorityContainer(IdeaDumpPanel ideaDumpPanel) {
        JPanel container = new JPanel(new GridLayout(2, 1, 10, 10));
        container.setBorder(new EmptyBorder(0, 0, 0, 5));

        container.add(ideaDumpPanel);
        container.add(ideaDumpPanel);

        return container;
    }
}
