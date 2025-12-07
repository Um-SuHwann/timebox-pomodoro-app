package app;

import model.TaskDataModel;
import ui.home.HomePanel;
import ui.navigation.NavigationListener;
import ui.navigation.NavigationPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PlannerApp extends JFrame implements NavigationListener {
    private final TaskDataModel model;
    private final CardLayout cardLayout;

    private JPanel cardPanel;

    public PlannerApp(TaskDataModel model){
        super("XPlanner");
        this.model = model;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        HomePanel homePanel = new HomePanel(model);

        cardPanel.add(homePanel, "Home");

        add(cardPanel, BorderLayout.CENTER);

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
