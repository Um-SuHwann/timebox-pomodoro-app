package ui.ideadump;

import model.Task;
import model.TaskDataModel;

import javax.swing.*;
import java.awt.*;

public class IdeaListArea extends JPanel {
    private final TaskDataModel model;
    private JList<Task> taskList;

    public IdeaListArea(TaskDataModel model){
        this.model = model;

        setLayout(new BorderLayout());

        taskList = new JList<>(model.getIdeaDumpModel());
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        taskList.setDragEnabled(true);
        taskList.setTransferHandler(new IdeaTransferHandler(model));
        add(new JScrollPane(taskList), BorderLayout.CENTER);
    }
    public Task getSelectedTask() {
        return taskList.getSelectedValue();
    }
    public JList<Task> getTaskList() {
        return taskList;
    }
}
