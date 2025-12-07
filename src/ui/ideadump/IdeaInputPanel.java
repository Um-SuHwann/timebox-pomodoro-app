package ui.ideadump;

import model.TaskDataModel;

import javax.swing.*;
import java.awt.*;

public class IdeaInputPanel extends JPanel {
    private final TaskDataModel model;
    private final JTextField newIdeaField;

    public IdeaInputPanel(TaskDataModel model){
        this.model = model;

        setLayout(new BorderLayout(5, 5));

        newIdeaField = new JTextField();
        JButton addButton = new JButton("Add Idea");

        addButton.addActionListener(e -> addIdea());

        add(newIdeaField, BorderLayout.CENTER);
        add(addButton, BorderLayout.EAST);
    }
    private void addIdea(){
        String content = newIdeaField.getText().trim();
        if(!content.isEmpty()){
            model.addTask(content);
            newIdeaField.setText("");
        }
    }
}
