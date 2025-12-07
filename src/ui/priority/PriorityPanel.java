package ui.priority;

import model.Task;
import model.TaskDataModel;
import ui.ideadump.IdeaDumpPanel;

import javax.swing.*;
import java.awt.*;

public class PriorityPanel extends JPanel {
    private final TaskDataModel model;
    private final IdeaDumpPanel ideaDumpPanel;
    private JList<Task> priorityList;

    public PriorityPanel(TaskDataModel model, IdeaDumpPanel ideaDumpPanel){
        this.model = model;
        this.ideaDumpPanel = ideaDumpPanel;

        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createTitledBorder("2. Priority"));

        setupTaskListArea();
        setupControlButtons();
    }

    private void setupTaskListArea(){
        priorityList = new JList<>(model.getPriorityModel());
        priorityList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(new JScrollPane(priorityList), BorderLayout.CENTER);
    }

    private void setupControlButtons(){
        JPanel controlPanel = new JPanel(new GridLayout(1, 1, 5, 5));

        JButton removeFromPriorityButton = new JButton("Remove");
        removeFromPriorityButton.addActionListener(e -> removeFromPriority());

        controlPanel.add(removeFromPriorityButton);
        add(controlPanel, BorderLayout.SOUTH);
    }

    private void removeFromPriority() {
        Task task = priorityList.getSelectedValue();

        if (task != null) {
            model.getPriorityModel().removeElement(task);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Please select a task to remove from priority.",
                    "Selection Required",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}
