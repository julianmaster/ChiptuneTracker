package com.chiptunetracker.menu;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.chiptunetracker.core.ChiptuneTracker;
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
    protected StringBuilder currentFile;

    public NewFileListener(FileChooser fileChooser, StringBuilder currentFile) {
        this.fileChooser = fileChooser;
        this.currentFile = currentFile;
    }

    @Override
    public void yes() {
        try {
            if(currentFile != null) {
                File file = new File(currentFile.toString());
                if(file.canWrite()) {
                    Serializer serializer = new Persister();
                    serializer.write(ChiptuneTracker.getInstance().getData(), file);
                    clearAction();
                }
                else {
                    throw new IOException("Unable to write in the file !");
                }
            }
            else {
                fileChooser.setMode(FileChooser.Mode.SAVE);
                fileChooser.setSize(ChiptuneTracker.getInstance().getAsciiTerminal().getFullWidth(), ChiptuneTracker.getInstance().getAsciiTerminal().getFullHeight());
                fileChooser.setListener(new SaveFileListener(currentFile) {
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
        currentFile = null;
        ChiptuneTracker.getInstance().setInitSampleView(true);
        ChiptuneTracker.getInstance().setInitPatternView(true);
        ChiptuneTracker.getInstance().setScreen(ChiptuneTracker.getInstance().getMenuView());
        ChiptuneTracker.getInstance().getData().samples = new LinkedList<>();
        ChiptuneTracker.getInstance().getData().patterns = new LinkedList<>();
        ChiptuneTracker.getInstance().setChangeData(false);
    }



    @Override
    public void no() {
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
