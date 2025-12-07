package app;

import model.TaskDataModel;

import javax.swing.*;

public class PlannerRunner {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TaskDataModel model = new TaskDataModel();

            PlannerApp appFrame = new PlannerApp(model);

            appFrame.setVisible(true);
        });
    }
}
