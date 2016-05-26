package main;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class DataManager {
	
	private final ChiptuneTracker chiptuneTracker;
	private String currentFile = null;
	private final JFileChooser fileChooser = new JFileChooser();
	
	public DataManager(ChiptuneTracker chiptuneTracker) {
		this.chiptuneTracker = chiptuneTracker;
	}

	public boolean newFile() throws Exception {
		if(ChiptuneTracker.changeData) {
			int option = JOptionPane.NO_OPTION;
			if(currentFile == null) {
				option = JOptionPane.showOptionDialog(ChiptuneTracker.asciiTerminal, "New file has been modified. Save changes?", "Save Resource", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
			}
			else {
				option = JOptionPane.showOptionDialog(ChiptuneTracker.asciiTerminal, currentFile+" has been modified. Save changes?", "Save Resource", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
			}
			
			if(option == JOptionPane.YES_OPTION) {
				if(!save()) {
					return false;
				}
			}
			else if(option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
				return false;
			}
		}
		
		currentFile = null;
		ChiptuneTracker.changeData = false;
		ChiptuneTracker.initSampleView = true;
		ChiptuneTracker.initPatternView = true;
		chiptuneTracker.changeView(ChiptuneTracker.menuView);
		ChiptuneTracker.data.samples = new LinkedList<>();
		ChiptuneTracker.data.patterns = new LinkedList<>();
		return true;
	}
	
	public void open() throws Exception {
		boolean continueOpen = true;
		if(ChiptuneTracker.changeData) {
			continueOpen = newFile();
		}
		
		if(continueOpen) {
			int returnValue = fileChooser.showOpenDialog(ChiptuneTracker.asciiTerminal);
			if(returnValue == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				if(file.canRead()) {
					Serializer serializer = new Persister();
					serializer.read(ChiptuneTracker.data, file);
					currentFile = file.getAbsolutePath();
				}
				else {
					throw new IOException("Unable to read the file !");
				}
			}
		}
	}

	public boolean save() throws Exception {
		if(currentFile != null) {
			File file = new File(currentFile);
			if(file.canWrite()) {
				Serializer serializer = new Persister();
				serializer.write(ChiptuneTracker.data, file);
				return true;
			}
			else {
				throw new IOException("Unable to write in the file !");
			}
		}
		else {
			return saveAs();
		}
	}
	
	public boolean saveAs() throws Exception {
		int returnValue = fileChooser.showSaveDialog(ChiptuneTracker.asciiTerminal);
		if(returnValue == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			boolean fileExist = false;
			if(file.exists()) {
				fileExist = true;
				int option = JOptionPane.showOptionDialog(ChiptuneTracker.asciiTerminal, file.getAbsolutePath()+" already exists. Do you want to replace it?", "Save As", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
				if(option == JOptionPane.NO_OPTION || option == JOptionPane.CANCEL_OPTION) {
					return false;
				}
			}
			
			if(!fileExist || (fileExist && file.canWrite())) {
				Serializer serializer = new Persister();
				serializer.write(ChiptuneTracker.data, file);
				return true;
			}
			else {
				throw new IOException("Unable to write in the file !");
			}
		}
		else {
			return false;
		}
	}
	
	public void exit() throws Exception {
		boolean continueExit = true;
		if(ChiptuneTracker.changeData) {
			continueExit = newFile();
		}
		if(continueExit) {
			System.exit(0);
		}
	}
}
