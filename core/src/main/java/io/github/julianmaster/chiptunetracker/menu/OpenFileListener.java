package io.github.julianmaster.chiptunetracker.menu;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import io.github.julianmaster.chiptunetracker.ChiptuneTracker;
import io.github.julianmaster.chiptunetracker.core.DataManager;
import io.github.julianmaster.chiptunetracker.model.Data;
import com.kotcrab.vis.ui.util.dialog.Dialogs;
import com.kotcrab.vis.ui.widget.file.FileChooser;
import com.kotcrab.vis.ui.widget.file.FileChooserAdapter;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;

/**
 * Created by jmaitr03 on 20/06/2017.
 */
public class OpenFileListener extends NewFileListener {

    public OpenFileListener(FileChooser fileChooser, DataManager dataManager) {
        super(fileChooser, dataManager);
    }

    @Override
    public void additionalYesActions() {
        openAction();
    }

    @Override
    public void additionalNoActions() {
        openAction();
    }

    public void openAction() {
        fileChooser.setFileTypeFilter(null);
        fileChooser.setMode(FileChooser.Mode.OPEN);
        fileChooser.setSize(ChiptuneTracker.getInstance().getAsciiTerminal().getFullWidth(), ChiptuneTracker.getInstance().getAsciiTerminal().getFullHeight());
        fileChooser.setListener(new FileChooserAdapter() {
            @Override
            public void selected (Array<FileHandle> files) {
                File file = files.first().file();
                if(file.canRead()) {
                    Serializer serializer = new Persister();
                    Data data = new Data();

                    try {
                        serializer.read(data, file);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    ChiptuneTracker.getInstance().setData(data);
                    if(dataManager.getCurrentFile() == null) {
                        dataManager.setCurrentFile(new StringBuilder());
                    }
                    dataManager.getCurrentFile().setLength(0);
                    dataManager.getCurrentFile().append(file.getAbsolutePath());
                    ChiptuneTracker.getInstance().setChangeData(false);
                    ChiptuneTracker.getInstance().setInitSampleView(true);
                    ChiptuneTracker.getInstance().setInitPatternView(true);
                }
                else {
                    Dialogs.showErrorDialog(ChiptuneTracker.getInstance().getAsciiTerminal().getStage(), "Unable to read the file !");
                }
            }
        });
        ChiptuneTracker.getInstance().getAsciiTerminal().addActor(fileChooser.fadeIn());
    }
}
