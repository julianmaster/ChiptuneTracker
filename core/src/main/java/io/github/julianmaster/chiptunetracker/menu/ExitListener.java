package io.github.julianmaster.chiptunetracker.menu;

import io.github.julianmaster.chiptunetracker.core.DataManager;
import com.kotcrab.vis.ui.util.dialog.Dialogs;
import com.kotcrab.vis.ui.widget.file.FileChooser;

/**
 * Created by Julien on 20/06/2017.
 */
public abstract class ExitListener extends NewFileListener {

    public ExitListener(FileChooser fileChooser, DataManager dataManager) {
        super(fileChooser, dataManager);
    }
}
