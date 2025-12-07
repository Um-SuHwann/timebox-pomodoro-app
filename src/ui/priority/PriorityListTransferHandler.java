package ui.priority;

import model.Task;
import model.TaskDataModel;
import ui.ideadump.IdeaTransferHandler;

import javax.swing.*;
import java.awt.datatransfer.Transferable;

public class PriorityListTransferHandler extends TransferHandler {
    private final TaskDataModel model;

    public PriorityListTransferHandler(TaskDataModel model) {
        this.model = model;
    }

    // --- EXPORT (Drag from PriorityList) ---
    @Override
    public int getSourceActions(JComponent c) {
        return COPY;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        JList<Task> sourceList = (JList<Task>) c;
        Task task = sourceList.getSelectedValue();
        if (task != null) {
            return new IdeaTransferHandler.TaskTransferable(task);
        }
        return null;
    }

    // --- IMPORT (Drop onto PriorityList) ---
    @Override
    public boolean canImport(TransferSupport support) {
        if (!support.isDrop()) {
            return false;
        }
        // Can only import Tasks
        if (!support.isDataFlavorSupported(IdeaTransferHandler.TASK_FLAVOR)) {
            return false;
        }
        // Priority list can't have more than 3 tasks
        if (model.getPriorityModel().size() >= 3) {
            return false;
        }
        return true;
    }

    @Override
    public boolean importData(TransferSupport support) {
        if (!canImport(support)) {
            return false;
        }

        Task task;
        try {
            task = (Task) support.getTransferable().getTransferData(IdeaTransferHandler.TASK_FLAVOR);
        } catch (Exception e) {
            return false;
        }

        // This logic is from the old IdeaTransferHandler
        model.addPriorityTask(task);

        // If the drop is a MOVE action (from IdeaDump), we need to remove it from the source.
        // The removal is handled by exportDone in the source's TransferHandler (IdeaTransferHandler).
        return true;
    }
}
