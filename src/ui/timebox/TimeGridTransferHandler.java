package ui.timebox;

import model.Task;
import model.TaskDataModel;
import ui.ideadump.IdeaTransferHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Transferable;

public class TimeGridTransferHandler extends TransferHandler {

    private final TaskDataModel model;

    public TimeGridTransferHandler(TaskDataModel model) {
        this.model = model;
    }

    // This handler only supports importing (dropping)
    @Override
    public boolean canImport(TransferSupport support) {
        if (!support.isDrop()) {
            return false;
        }
        return support.isDataFlavorSupported(IdeaTransferHandler.TASK_FLAVOR);
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

        TimeGridPanel gridPanel = (TimeGridPanel) support.getComponent();
        Point dropPoint = support.getDropLocation().getDropPoint();

        int slotIndex = gridPanel.getSlotIndexFromPoint(dropPoint);
        if (slotIndex != -1) {
            model.setTimeBoxTask(slotIndex, task);
            gridPanel.repaint(); // Repaint to show the new task
            return true;
        }
        return false;
    }
}
