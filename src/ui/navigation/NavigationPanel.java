package ui.navigation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class NavigationPanel extends JPanel {
    //go to menu
    private final NavigationListener listener;

    public NavigationPanel(NavigationListener listener){
        this.listener = listener;

        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        setupNavigationButtons();
    }

    private void setupNavigationButtons(){
        NavigationButton homeButton = new NavigationButton("Home", this::handleNavigationClick, null);
        NavigationButton calenderButton = new NavigationButton("Calender", this::handleNavigationClick, null);
        NavigationButton pomodoroButton = new NavigationButton("Pomodoro", this::handleNavigationClick, null);

        add(homeButton);
        add(calenderButton);
        add(pomodoroButton);
    }

    private void handleNavigationClick(ActionEvent e) {
        String command = e.getActionCommand();
        if (listener != null) {
            listener.navigateTo(command);
        }
    }
}
