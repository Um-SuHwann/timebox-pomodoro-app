package ui.ideadump;

import model.TaskDataModel;

import javax.swing.*;
import java.awt.*;

public class IdeaDumpPanel extends JPanel {
    private final TaskDataModel model;

    public IdeaDumpPanel(TaskDataModel model){
        this.model = model;

        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createTitledBorder("1. Idea Dump"));

        IdeaInputPanel inputPanel = new IdeaInputPanel(model);
        add(inputPanel, BorderLayout.NORTH);

        IdeaListArea ideaListArea = new IdeaListArea(model);
        add(ideaListArea, BorderLayout.CENTER);
    }

}
