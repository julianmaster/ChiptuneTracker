package com.chiptunetracker.core;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.chiptunetracker.menu.NewFileListener;
import com.chiptunetracker.menu.OpenFileListener;
import com.chiptunetracker.menu.SaveFileListener;
import com.chiptunetracker.model.Data;
import com.chiptunetracker.view.MenuView;
import com.chiptunetracker.view.View;
import com.kotcrab.vis.ui.FocusManager;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.util.dialog.Dialogs;
import com.kotcrab.vis.ui.util.dialog.InputDialogAdapter;
import com.kotcrab.vis.ui.util.dialog.OptionDialogAdapter;
import com.kotcrab.vis.ui.util.dialog.OptionDialogListener;
import com.kotcrab.vis.ui.widget.file.FileChooser;
import com.kotcrab.vis.ui.widget.file.FileChooserAdapter;
import com.kotcrab.vis.ui.widget.file.FileTypeFilter;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class DataManager {
	
	private StringBuilder currentFile = null;
	private final FileChooser fileChooser = new FileChooser(FileChooser.Mode.OPEN);

	public void newFile() {
		new NewFileListener(fileChooser, currentFile);
	}
	
	public void open() {
		new OpenFileListener(fileChooser, currentFile);
	}

	public void save() throws Exception {
		if(currentFile != null) {
			File file = new File(currentFile.toString());
			if(file.canWrite()) {
				Serializer serializer = new Persister();
				serializer.write(ChiptuneTracker.getInstance().getData(), file);
				ChiptuneTracker.getInstance().setChangeData(false);
			}
			else {
				throw new IOException("Unable to write in the file !");
			}
		}
		else {
			saveAs();
		}
	}
	
	public void saveAs() {
		fileChooser.setMode(FileChooser.Mode.SAVE);
		fileChooser.setSize(ChiptuneTracker.getInstance().getAsciiTerminal().getFullWidth(), ChiptuneTracker.getInstance().getAsciiTerminal().getFullHeight());
		fileChooser.setListener(new SaveFileListener(currentFile));

		((View) ChiptuneTracker.getInstance().getScreen()).setListActorTouchables(Touchable.disabled);
		ChiptuneTracker.getInstance().getAsciiTerminal().addActor(fileChooser.fadeIn());
	}
	
	
	
//	public boolean initExport(MenuView menuView) {
//		continueExport = true;
//
//		FileTypeFilter typeFilter = new FileTypeFilter(false); //allow "All Types" mode where all files are shown
//		typeFilter.addRule("Audio files (*.wav)", "wav");
//		fileChooser.setFileTypeFilter(typeFilter);
//
//		fileChooser.setMode(FileChooser.Mode.SAVE);
//		fileChooser.setSize(ChiptuneTracker.getInstance().getAsciiTerminal().getFullWidth(), ChiptuneTracker.getInstance().getAsciiTerminal().getFullHeight());
//
//		fileChooser.setListener(new FileChooserAdapter() {
//			@Override
//			public void selected(Array<FileHandle> files) {
//				File file = files.first().file();
//
//				if (!getExtension(file.getName()).equalsIgnoreCase("wav")) {
//					file = new File(file.getParentFile(), getBaseName(file.getName())+".wav"); // remove the extension (if any) and replace it with ".wav"
//				}
//
//				if(!file.exists() || file.canWrite()) {
//					menuView.runExport();
//					fileToExport = file.getAbsolutePath();
//					continueExport = true;
//				}
//				else {
//					Dialogs.showErrorDialog(ChiptuneTracker.getInstance().getAsciiTerminal().getStage(), "Unable to write in the file !");
//					continueExport = false;
//				}
//			}
//
//			@Override
//			public void canceled() {
//				continueExport = false;
//			}
//		});
//
//		ChiptuneTracker.getInstance().getAsciiTerminal().addActor(fileChooser.fadeIn());
//
//		return continueExport;
//	}
	
//	public void runExport() throws Exception {
//		FileRecorder fileRecorder = new FileRecorder();
//		fileRecorder.savePattern(fileToExport);
//	}





	
	

	/**
	 * Utils methods
	 */

	
	private Integer indexOfLastSeparator(String filename) {
	    if (filename == null) {
	        return null;
	    }
	    Integer lastUnixPos = filename.lastIndexOf('/');
	    Integer lastWindowsPos = filename.lastIndexOf('\\');
	    return Math.max(lastUnixPos, lastWindowsPos);
	}
	
	private Integer indexOfExtension(String filename) {
		if (filename == null) {
			return null;
		}
		final int extensionPos = filename.lastIndexOf('.');
		final int lastSeparator = indexOfLastSeparator(filename);
		return lastSeparator > extensionPos ? null : extensionPos;
	}
	
	private String getExtension(String filename) {
		if (filename == null) {
			return null;
		}
		Integer index = indexOfExtension(filename);
		if (index == null) {
			return "";
		} else {
			return filename.substring(index + 1);
		}
    }
	
	private void failIfNullBytePresent(String path) {
		int len = path.length();
		for (int i = 0; i < len; i++) {
			if (path.charAt(i) == 0) {
				throw new IllegalArgumentException("Null byte present in file/path name. There are no " +
						"known legitimate use cases for such data, but several injection attacks may use it");
			}
		}
	}
	
	private String getName(String filename) {
		if (filename == null) {
			return null;
		}
		failIfNullBytePresent(filename);
		int index = indexOfLastSeparator(filename);
		return filename.substring(index + 1);
	}
	
	private String removeExtension(String filename) {
		if (filename == null) {
			return null;
		}
		failIfNullBytePresent(filename);
		
		Integer index = indexOfExtension(filename);
		if (index == null) {
			return filename;
		} else {
			return filename.substring(0, index);
		}
	}
	
	public String getBaseName(String filename) {
	   return removeExtension(getName(filename));
	}

	public FileChooser getFileChooser() {
		return fileChooser;
	}

	public StringBuilder getCurrentFile() {
		return currentFile;
	}
}
