package ui.navigation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class NavigationButton extends JButton {
    public NavigationButton(String text, ActionListener listener, Icon icon){
        super(text, icon);
        setActionCommand(text);
        addActionListener(listener);
        initStyle();
    }
    private void initStyle() {
        setFocusPainted(false);
        setBackground(new Color(230, 230, 230));
        setFont(new Font("Dialog", Font.BOLD, 14));
        setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
    }
}
