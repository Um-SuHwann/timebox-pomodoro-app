package ui.ideadump;

import model.Task;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class IdeaTransferable implements Transferable {

    public static final DataFlavor IDEA_FLAVOR = new DataFlavor(Task.class, "Task");

    public final Task task;

    public IdeaTransferable(Task task) {
        this.task = task;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[] { IDEA_FLAVOR };
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(IDEA_FLAVOR);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if(flavor.equals(IDEA_FLAVOR))
            return task;
        throw new UnsupportedFlavorException(flavor);
    }
}
