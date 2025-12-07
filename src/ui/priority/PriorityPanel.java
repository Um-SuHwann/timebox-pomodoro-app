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
        priorityList.setDragEnabled(true); // Enable dragging from this list
        priorityList.setTransferHandler(new PriorityListTransferHandler(model)); // Set the new handler

        priorityList.setCellRenderer(new PriorityCellCustom());
        priorityList.setFixedCellHeight(40); // Set fixed height for each cell

        JScrollPane scrollPane = new JScrollPane(priorityList);

        // To fix the height of the JScrollPane to 120px (3 items * 40px),
        // we place it inside another panel with a BorderLayout and add the scroll pane to the NORTH.
        // The NORTH region of a BorderLayout respects the component's preferred height.
        JPanel listContainer = new JPanel(new BorderLayout());
        listContainer.add(scrollPane, BorderLayout.NORTH);

        add(listContainer, BorderLayout.CENTER);
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
