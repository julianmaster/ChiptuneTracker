package com.chiptunetracker.menu;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.chiptunetracker.core.ChiptuneTracker;
import com.chiptunetracker.view.View;
import com.kotcrab.vis.ui.util.dialog.Dialogs;
import com.kotcrab.vis.ui.widget.file.FileChooser;

/**
 * Created by Julien on 20/06/2017.
 */
public abstract class ExitListener extends NewFileListener {

    public ExitListener(FileChooser fileChooser, StringBuilder currentFile) {
        super(fileChooser, currentFile);
    }
}
