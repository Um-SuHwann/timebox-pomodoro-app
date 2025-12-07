package ui.priority;

import model.Task;
import model.TaskDataModel;
import ui.ideadump.IdeaDumpPanel;
import ui.ideadump.IdeaTransferHandler;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.awt.*;

public class PriorityPanel extends JPanel {
    private final TaskDataModel model;
    private final IdeaDumpPanel ideaDumpPanel;
    private JList<Task> priorityList;
    private final DefaultListModel<Task> priorityViewModel;

    public PriorityPanel(TaskDataModel model, IdeaDumpPanel ideaDumpPanel){
        this.model = model;
        this.ideaDumpPanel = ideaDumpPanel;
        this.priorityViewModel = new DefaultListModel<>();

        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createTitledBorder("2. Priority"));

        setupTaskListArea();
        setupControlButtons();
        addModelListeners();

        updatePriorityViewModel(); // Initial population
    }

    private void setupTaskListArea(){
        priorityList = new JList<>(priorityViewModel); // Use the view model
        priorityList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        priorityList.setTransferHandler(new IdeaTransferHandler(model)); // Set the handler

        priorityList.setFixedCellHeight(40);
        priorityList.setCellRenderer(new PriorityCellCustom());

        add(new JScrollPane(priorityList), BorderLayout.CENTER);
    }

    private void addModelListeners() {
        model.getPriorityModel().addListDataListener(new ListDataListener() {
            @Override
            public void intervalAdded(ListDataEvent e) {
                updatePriorityViewModel();
            }
            @Override
            public void intervalRemoved(ListDataEvent e) {
                updatePriorityViewModel();
            }
            @Override
            public void contentsChanged(ListDataEvent e) {
                updatePriorityViewModel();
            }
        });
    }

    private void updatePriorityViewModel() {
        priorityViewModel.clear();
        DefaultListModel<Task> sourceModel = model.getPriorityModel();

        int size = sourceModel.getSize();

        for (int i = 0; i < size; i++) {
            priorityViewModel.addElement(sourceModel.getElementAt(i));
        }

        while (priorityViewModel.getSize() < 3) {
            priorityViewModel.addElement(null); // Add null placeholders
        }
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
            model.addTask(task.getContent());
            model.getPriorityModel().removeElement(task);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Please select a task to remove from priority.",
                    "Selection Required",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}
