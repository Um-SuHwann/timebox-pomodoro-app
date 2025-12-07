package ui.ideadump;

import model.Task;
import model.TaskDataModel;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class IdeaTransferHandler extends TransferHandler {
    private final TaskDataModel model;
    public static final DataFlavor TASK_FLAVOR = new DataFlavor(Task.class, "Task");

    public IdeaTransferHandler(TaskDataModel model) {
        this.model = model;
    }

    @Override
    public int getSourceActions(JComponent c) {
        return MOVE;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        JList<Task> sourceList = (JList<Task>) c;
        Task task = sourceList.getSelectedValue();
        return new TaskTransferable(task);
    }

    @Override
    public boolean canImport(TransferSupport support) {
        if (!support.isDrop()) {
            return false;
        }
        if (!support.isDataFlavorSupported(TASK_FLAVOR)) {
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

        Transferable transferable = support.getTransferable();
        Task task;
        try {
            task = (Task) transferable.getTransferData(TASK_FLAVOR);
        } catch (UnsupportedFlavorException | IOException e) {
            return false;
        }

        model.addPriorityTask(task);
        return true;
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
        if (action == MOVE) {
            JList<Task> sourceList = (JList<Task>) source;
            try {
                Task movedTask = (Task) data.getTransferData(TASK_FLAVOR);
                model.getIdeaDumpModel().removeElement(movedTask);
            } catch (UnsupportedFlavorException | IOException e) {
                //
            }
        }
    }

    public static class TaskTransferable implements Transferable {
        private final Task task;

        public TaskTransferable(Task task) {
            this.task = task;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{TASK_FLAVOR};
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return TASK_FLAVOR.equals(flavor);
        }

        @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
            if (!isDataFlavorSupported(flavor)) {
                throw new UnsupportedFlavorException(flavor);
            }
            return task;
        }
    }
}