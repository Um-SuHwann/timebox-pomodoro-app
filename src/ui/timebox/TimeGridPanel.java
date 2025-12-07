package ui.timebox;

import model.Task;
import model.TaskDataModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TimeGridPanel extends JPanel {
    private final TaskDataModel model;
    private static final int START_HOUR = 5;
    private static final int END_HOUR = 23;
    private static final int NUM_ROWS = END_HOUR - START_HOUR + 1;
    private static final int NUM_COLS = 2;

    // Colors for the 3 priority tasks
    private static final Color[] TASK_COLORS = {
            new Color(255, 102, 102, 180), // Red with some transparency
            new Color(255, 178, 102, 180), // Orange with some transparency
            new Color(255, 255, 102, 180)  // Yellow with some transparency
    };

    public TimeGridPanel(TaskDataModel model) {
        this.model = model;
        setBackground(Color.WHITE);
        setTransferHandler(new TimeGridTransferHandler(model));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleGridClick(e);
            }
        });
    }

    private void handleGridClick(MouseEvent e) {
        int slotIndex = getSlotIndexFromPoint(e.getPoint());
        if (slotIndex == -1) return;

        Task existingTask = model.getTimeBoxedTasks().get(slotIndex);

        if (existingTask != null) {
            model.setTimeBoxTask(slotIndex, null);
        } else {
            showPriorityTaskPopup(e, slotIndex);
        }
        repaint();
    }

    private void showPriorityTaskPopup(MouseEvent e, int slotIndex) {
        JPopupMenu popupMenu = new JPopupMenu();
        DefaultListModel<Task> priorityTasks = model.getPriorityModel();

        if (priorityTasks.isEmpty()) {
            JMenuItem emptyItem = new JMenuItem("No priority tasks to assign");
            emptyItem.setEnabled(false);
            popupMenu.add(emptyItem);
        } else {
            for (int i = 0; i < priorityTasks.getSize(); i++) {
                Task task = priorityTasks.getElementAt(i);
                if (task == null) continue;

                JMenuItem taskItem = new JMenuItem(task.getContent());
                final int priorityIndex = i;
                taskItem.addActionListener(actionEvent -> {
                    model.setTimeBoxTask(slotIndex, task);
                    repaint();
                });
                popupMenu.add(taskItem);
            }
        }
        popupMenu.show(e.getComponent(), e.getX(), e.getY());
    }

    public int getSlotIndexFromPoint(Point point) {
        if (getHeight() == 0) return -1;
        int cellWidth = getWidth() / NUM_COLS;
        int cellHeight = getHeight() / NUM_ROWS;

        int col = point.x / cellWidth;
        int row = point.y / cellHeight;

        if (col >= 0 && col < NUM_COLS && row >= 0 && row < NUM_ROWS) {
            return row * NUM_COLS + col;
        }
        return -1;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawTasks(g);
        drawGridLines(g);
    }

    private void drawTasks(Graphics g) {
        if (getHeight() == 0) return;
        int cellWidth = getWidth() / NUM_COLS;
        int cellHeight = getHeight() / NUM_ROWS;

        java.util.List<Task> timeBoxedTasks = model.getTimeBoxedTasks();
        DefaultListModel<Task> priorityTasks = model.getPriorityModel();

        for (int i = 0; i < timeBoxedTasks.size(); i++) {
            Task task = timeBoxedTasks.get(i);
            if (task != null) {
                int priorityIndex = priorityTasks.indexOf(task);
                if (priorityIndex != -1 && priorityIndex < TASK_COLORS.length) {
                    g.setColor(TASK_COLORS[priorityIndex]);
                } else {
                    g.setColor(Color.LIGHT_GRAY);
                }

                int row = i / NUM_COLS;
                int col = i % NUM_COLS;
                int x = col * cellWidth;
                int y = row * cellHeight;

                g.fillRect(x, y, cellWidth, cellHeight);

                g.setColor(Color.BLACK);
                g.setFont(getFont().deriveFont(Font.BOLD, 12f));
                String content = task.getContent();
                FontMetrics fm = g.getFontMetrics();
                if (fm.stringWidth(content) > cellWidth - 4) {
                    while(fm.stringWidth(content + "...") > cellWidth - 4 && !content.isEmpty()) {
                        content = content.substring(0, content.length() - 1);
                    }
                    content += "...";
                }
                g.drawString(content, x + 5, y + fm.getAscent() + 5);
            }
        }
    }

    private void drawGridLines(Graphics g) {
        if (getHeight() == 0) return;
        int cellWidth = getWidth() / NUM_COLS;
        int cellHeight = getHeight() / NUM_ROWS;

        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i <= NUM_ROWS; i++) {
            g.drawLine(0, i * cellHeight, getWidth(), i * cellHeight);
        }
        for (int i = 0; i <= NUM_COLS; i++) {
            g.drawLine(i * cellWidth, 0, i * cellWidth, getHeight());
        }
    }
}

