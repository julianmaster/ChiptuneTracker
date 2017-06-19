package com.chiptunetracker.menu;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.chiptunetracker.core.ChiptuneTracker;
import com.chiptunetracker.view.View;
import com.kotcrab.vis.ui.util.dialog.Dialogs;
import com.kotcrab.vis.ui.widget.file.FileChooser;

/**
 * Created by Julien on 20/06/2017.
 */
public abstract class ExitListener extends NewFileListener implements Runnable {

    public ExitListener(FileChooser fileChooser, StringBuilder currentFile) {
        super(fileChooser, currentFile);
    }

    @Override
    public void run() {
        if(ChiptuneTracker.getInstance().isChangeData()) {
            String text;
            if(currentFile == null) {
                text = "New file has been modified. Save changes?";
            }
            else {
                text = currentFile+" has been modified. Save changes?";
            }

            Dialogs.showOptionDialog(ChiptuneTracker.getInstance().getAsciiTerminal().getStage(), "Save Resource", text, Dialogs.OptionDialogType.YES_NO_CANCEL, this);

            ((View) ChiptuneTracker.getInstance().getScreen()).setListActorTouchables(Touchable.disabled);
        }
        else {
            additionalYesActions();
        }
    }

    @Override
    public void additionalYesActions() {
        ChiptuneTracker.getInstance().exit();
        endRun();
    }

    @Override
    public void additionalNoActions() {
        additionalYesActions();
    }

    public void endRun() {

    }
}
