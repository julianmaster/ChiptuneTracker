package main;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class DataManager {
	
	private String currentFile = null;
	private final JFileChooser fileChooser = new JFileChooser();
	
	public boolean newFile() throws Exception {
		if(ChiptuneTracker.getInstance().isChangeData()) {
			int option = JOptionPane.NO_OPTION;
			if(currentFile == null) {
				option = JOptionPane.showOptionDialog(ChiptuneTracker.getInstance().getAsciiTerminal(), "New file has been modified. Save changes?", "Save Resource", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
			}
			else {
				option = JOptionPane.showOptionDialog(ChiptuneTracker.getInstance().getAsciiTerminal(), currentFile+" has been modified. Save changes?", "Save Resource", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
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
		ChiptuneTracker.getInstance().setChangeData(false);
		ChiptuneTracker.getInstance().setInitSampleView(true);
		ChiptuneTracker.getInstance().setInitPatternView(true);
		ChiptuneTracker.getInstance().changeView(ChiptuneTracker.getInstance().getMenuView());
		ChiptuneTracker.getInstance().getData().samples = new LinkedList<>();
		ChiptuneTracker.getInstance().getData().patterns = new LinkedList<>();
		return true;
	}
	
	public void open() throws Exception {
		boolean continueOpen = true;
		if(ChiptuneTracker.getInstance().isChangeData()) {
			continueOpen = newFile();
		}
		
		if(continueOpen) {
			int returnValue = fileChooser.showOpenDialog(ChiptuneTracker.getInstance().getAsciiTerminal());
			if(returnValue == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				if(file.canRead()) {
					Serializer serializer = new Persister();
					Data data = new Data();
					serializer.read(data, file);
					ChiptuneTracker.getInstance().setData(data);
					currentFile = file.getAbsolutePath();
				}
				else {
					throw new IOException("Unable to read the file !");
				}
			}
		}
	}
	
	public void openFile(File file) {
		try {
			Serializer serializer = new Persister();
			Data data = new Data();
			serializer.read(data, file);
			ChiptuneTracker.getInstance().setData(data);
			currentFile = file.getAbsolutePath();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(ChiptuneTracker.getInstance().getAsciiTerminal(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public boolean save() throws Exception {
		if(currentFile != null) {
			File file = new File(currentFile);
			if(file.canWrite()) {
				Serializer serializer = new Persister();
				serializer.write(ChiptuneTracker.getInstance().getData(), file);
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
		int returnValue = fileChooser.showSaveDialog(ChiptuneTracker.getInstance().getAsciiTerminal());
		if(returnValue == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			boolean fileExist = false;
			if(file.exists()) {
				fileExist = true;
				int option = JOptionPane.showOptionDialog(ChiptuneTracker.getInstance().getAsciiTerminal(), file.getAbsolutePath()+" already exists. Do you want to replace it?", "Save As", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
				if(option == JOptionPane.NO_OPTION || option == JOptionPane.CANCEL_OPTION) {
					return false;
				}
			}
			
			if(!fileExist || (fileExist && file.canWrite())) {
				Serializer serializer = new Persister();
				serializer.write(ChiptuneTracker.getInstance().getData(), file);
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
		if(ChiptuneTracker.getInstance().isChangeData()) {
			continueExit = newFile();
		}
		if(continueExit) {
			System.exit(0);
		}
	}
}
