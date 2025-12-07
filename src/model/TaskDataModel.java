package model;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDataModel {
    private static final String DATA_FOLDER = "data";
    private static final String TASKS_FILE_NAME = "tasks.txt";

    private static final String TASKS_FILE_PATH = DATA_FOLDER + "/" + TASKS_FILE_NAME;
    private final int TOTAL_TIME_SLOTS = 38; // 5:00 ~ 24:00 (19 hours * 2 slots/hour)

    private final DefaultListModel<Task> ideaDumpModel;
    private final DefaultListModel<Task> priorityModel;
    private final List<Task> timeBoxedTasks;

    public TaskDataModel() {
        ideaDumpModel = new DefaultListModel<>();
        priorityModel = new DefaultListModel<>();
        timeBoxedTasks = new ArrayList<>();

        loadTasksFromFile();

        // Ensure timeBoxedTasks size is always 18, filling with null if needed
        while (timeBoxedTasks.size() < TOTAL_TIME_SLOTS) {
            timeBoxedTasks.add(null);
        }
    }

    public DefaultListModel<Task> getIdeaDumpModel() {
        return ideaDumpModel;
    }

    public DefaultListModel<Task> getPriorityModel() {
        return priorityModel;
    }

    public List<Task> getTimeBoxedTasks() {
        return timeBoxedTasks;
    }

    public void addTask(String content) {
        if (content == null || content.trim().isEmpty()) return;

        Task newTask = new Task(content);
        if (!ideaDumpModel.contains(newTask)) {
            ideaDumpModel.addElement(newTask);
        }
    }

    public void removeTask(Task task) {
        if (task == null) return;

        ideaDumpModel.removeElement(task);
        priorityModel.removeElement(task);

        // Remove from Timebox Schedule (set slot to null)
        for (int i = 0; i < timeBoxedTasks.size(); i++) {
            Task assignedTask = timeBoxedTasks.get(i);
            // Check if the assigned task matches the task to be removed
            if (task.equals(assignedTask)) {
                timeBoxedTasks.set(i, null); // Clear the slot
            }
        }
    }

    public void addPriorityTask(Task task) {
        if (task == null) return;
        if (!priorityModel.contains(task)) {
            priorityModel.addElement(task);
        }
    }

    public void setTimeBoxTask(int slotIndex, Task task) {
        if (slotIndex >= 0 && slotIndex < TOTAL_TIME_SLOTS) {
            timeBoxedTasks.set(slotIndex, task);
        }
    }

    public void saveTasksToFile() {
        try {
            // 1. Check and create 'data' folder
            File dataDir = new File(DATA_FOLDER);
            if (!dataDir.exists()) {
                if (!dataDir.mkdirs()) {
                    System.err.println("Failed to create data folder.");
                    return;
                }
            }

            // 2. Save data to file
            try (PrintWriter writer = new PrintWriter(TASKS_FILE_PATH)) {

                // DUMP_TASKS
                writer.println("[DUMP_TASKS]");
                for (int i = 0; i < ideaDumpModel.getSize(); i++) {
                    // Save Task content string
                    writer.println(ideaDumpModel.getElementAt(i).getContent());
                }

                // PRIORITY_TASKS
                writer.println("[PRIORITY_TASKS]");
                for (int i = 0; i < priorityModel.getSize(); i++) {
                    writer.println(priorityModel.getElementAt(i).getContent());
                }

                // TIMEBOXED_TASKS
                writer.println("[TIMEBOXED_TASKS]");
                for (Task task : timeBoxedTasks) {
                    // If slot is null, save a marker
                    writer.println(task == null ? "---EMPTY---" : task.getContent());
                }

            } catch (IOException ex) {
                System.err.println("Error saving tasks: " + ex.getMessage());
            }

        } catch (SecurityException e) {
            System.err.println("Permission denied for folder creation: " + e.getMessage());
        }
    }

    private void loadTasksFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(TASKS_FILE_PATH))) {
            String line;
            String currentSection = "";
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("[")) {
                    currentSection = line;
                    continue;
                }
                if (line.isEmpty()) continue;

                Task loadedTask = null;
                if (!line.equals("---EMPTY---")) {
                    loadedTask = new Task(line);
                }

                switch (currentSection) {
                    case "[DUMP_TASKS]":
                        ideaDumpModel.addElement(loadedTask);
                        break;
                    case "[PRIORITY_TASKS]":
                        priorityModel.addElement(loadedTask);
                        break;
                    case "[TIMEBOXED_TASKS]":
                        // Add null (empty slot) or the loaded Task
                        timeBoxedTasks.add(loadedTask);
                        break;
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("No existing task file found. Starting fresh.");
        } catch (IOException ex) {
            System.err.println("Error loading tasks: " + ex.getMessage());
        }
    }
}
