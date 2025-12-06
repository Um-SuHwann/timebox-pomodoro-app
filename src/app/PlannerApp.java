package app;

import model.TaskDataModel;
import ui.navigation.NavigationListener;
import ui.navigation.NavigationPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PlannerApp extends JFrame implements NavigationListener {
    public PlannerApp(TaskDataModel model){
        super("XPlanner");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        NavigationPanel navigationPanel = new NavigationPanel(this);
        add(navigationPanel, BorderLayout.SOUTH);

        //save data
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
//                model.saveTasksToFile();
            }
        });
    }

    @Override
    public void navigateTo(String viewName) {

    }
}
