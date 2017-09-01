package com.chiptunetracker.menu;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.chiptunetracker.core.ChiptuneTracker;
import com.chiptunetracker.core.DataManager;
import com.chiptunetracker.view.View;
import com.kotcrab.vis.ui.util.dialog.Dialogs;
import com.kotcrab.vis.ui.util.dialog.OptionDialogListener;
import com.kotcrab.vis.ui.widget.file.FileChooser;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by Julien on 19/06/2017.
 */
public class NewFileListener implements OptionDialogListener {

    protected final FileChooser fileChooser;
    protected final DataManager dataManager;

    public NewFileListener(FileChooser fileChooser, DataManager dataManager) {
        this.fileChooser = fileChooser;
        this.dataManager = dataManager;

        if(ChiptuneTracker.getInstance().isChangeData()) {
            String text;
            if(dataManager.getCurrentFile() == null) {
                text = "New file has been modified. Save changes?";
            }
            else {
                text = dataManager.getCurrentFile()+" has been modified. Save changes?";
            }

            Dialogs.showOptionDialog(ChiptuneTracker.getInstance().getAsciiTerminal().getStage(), "Save Resource", text, Dialogs.OptionDialogType.YES_NO_CANCEL, this);

            ((View) ChiptuneTracker.getInstance().getScreen()).setListActorTouchables(Touchable.disabled);
        }
        else {
            clearAction();
            additionalYesActions();
        }
    }

    @Override
    public void yes() {
        try {
            if(dataManager.getCurrentFile() != null) {
                File file = new File(dataManager.getCurrentFile().toString());
                if(file.canWrite()) {
                    Serializer serializer = new Persister();
                    serializer.write(ChiptuneTracker.getInstance().getData(), file);
                    clearAction();
                    additionalYesActions();
                }
                else {
                    throw new IOException("Unable to write in the file !");
                }
            }
            else {
                fileChooser.setMode(FileChooser.Mode.SAVE);
                fileChooser.setSize(ChiptuneTracker.getInstance().getAsciiTerminal().getFullWidth(), ChiptuneTracker.getInstance().getAsciiTerminal().getFullHeight());
                fileChooser.setListener(new SaveFileListener(dataManager) {
                    @Override
                    public void additionalActions() {
                        clearAction();
                        additionalYesActions();
                        ((View) ChiptuneTracker.getInstance().getScreen()).setListActorTouchables(Touchable.enabled);
                    }
                });

                ((View) ChiptuneTracker.getInstance().getScreen()).setListActorTouchables(Touchable.disabled);
                ChiptuneTracker.getInstance().getAsciiTerminal().addActor(fileChooser.fadeIn());
            }

        } catch (Exception e) {
            Dialogs.showErrorDialog(ChiptuneTracker.getInstance().getAsciiTerminal().getStage(), e.getMessage());
            ((View) ChiptuneTracker.getInstance().getScreen()).setListActorTouchables(Touchable.enabled);
        }
    }

    public void clearAction() {
        dataManager.setCurrentFile(null);
        ChiptuneTracker.getInstance().setInitSampleView(true);
        ChiptuneTracker.getInstance().setInitPatternView(true);
        ChiptuneTracker.getInstance().setScreen(ChiptuneTracker.getInstance().getMenuView());
        ChiptuneTracker.getInstance().getData().samples = new LinkedList<>();
        ChiptuneTracker.getInstance().getData().patterns = new LinkedList<>();
        ChiptuneTracker.getInstance().setChangeData(false);
    }



    @Override
    public void no() {
        clearAction();
        additionalNoActions();
        ((View) ChiptuneTracker.getInstance().getScreen()).setListActorTouchables(Touchable.enabled);
    }

    @Override
    public void cancel() {
        additionalCancelActions();
        ((View) ChiptuneTracker.getInstance().getScreen()).setListActorTouchables(Touchable.enabled);
    }



    /*
        Additionals
     */

    public void additionalYesActions() {

    }

    public void additionalNoActions() {

    }

    public void additionalCancelActions() {

    }
}
