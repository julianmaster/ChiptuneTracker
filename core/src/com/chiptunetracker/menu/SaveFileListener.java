package com.chiptunetracker.menu;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;
import com.chiptunetracker.core.ChiptuneTracker;
import com.chiptunetracker.core.DataManager;
import com.chiptunetracker.view.View;
import com.kotcrab.vis.ui.util.dialog.Dialogs;
import com.kotcrab.vis.ui.widget.file.FileChooserListener;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;

/**
 * Created by Julien on 19/06/2017.
 */
public class SaveFileListener implements FileChooserListener {

    private final DataManager dataManager;

    public SaveFileListener(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void selected(Array<FileHandle> files) {
        File file = files.first().file();
        if(!file.exists() || file.canWrite()) {
            Serializer serializer = new Persister();
            try {
                serializer.write(ChiptuneTracker.getInstance().getData(), file);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(dataManager.getCurrentFile() == null) {
                dataManager.setCurrentFile(new StringBuilder());
            }
            dataManager.getCurrentFile().setLength(0);
            dataManager.getCurrentFile().append(file.getAbsolutePath());
            ChiptuneTracker.getInstance().setChangeData(false);
            additionalActions();
        }
        else {
            Dialogs.showErrorDialog(ChiptuneTracker.getInstance().getAsciiTerminal().getStage(), "Unable to write in the file !");
        }
        ((View) ChiptuneTracker.getInstance().getScreen()).setListActorTouchables(Touchable.enabled);
    }

    @Override
    public void canceled() {
        ((View) ChiptuneTracker.getInstance().getScreen()).setListActorTouchables(Touchable.enabled);
    }


    public void additionalActions() {

    }
}
