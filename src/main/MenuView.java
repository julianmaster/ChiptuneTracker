package main;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.LinkedList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import ui.AsciiTerminalButton;

public class MenuView extends View {
	
	private final JFileChooser fileChooser = new JFileChooser();
	
	public MenuView(ChiptuneTracker chiptuneTracker) {
		super(chiptuneTracker);
		createMenuButtons();
	}

	public void createMenuButtons() {
		int startY = 4;
		AsciiTerminalButton newButton = new AsciiTerminalButton(ChiptuneTracker.asciiPanel, "New", 5, startY, Color.MAGENTA, Color.ORANGE);
		newButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(ChiptuneTracker.changeData) {
					int option = JOptionPane.showOptionDialog(ChiptuneTracker.asciiTerminal, "New file has been modified, save changes?", "Save changes?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
					if(option == JOptionPane.YES_OPTION) {
						int returnValue = fileChooser.showOpenDialog(ChiptuneTracker.asciiTerminal);
						if(returnValue == JFileChooser.APPROVE_OPTION) {
							File file = fileChooser.getSelectedFile();
							if(file.canWrite()) {
								Serializer serializer = new Persister();
								File result = new File("example.xml");
								try {
									serializer.write(ChiptuneTracker.data, result);
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							}
						}
						else {
							return;
						}
						
					}
					else if(option != JOptionPane.NO_OPTION) {
						return;
					}
				}
				
				ChiptuneTracker.data.samples = new LinkedList<>();
				ChiptuneTracker.data.patterns = new LinkedList<>();
				ChiptuneTracker.initSampleView = true;
				ChiptuneTracker.initPatternView = true;
			}
		});
		terminalButtons.add(newButton);
		
		AsciiTerminalButton openFileButton = new AsciiTerminalButton(ChiptuneTracker.asciiPanel, "Open File...", 5, startY + 2, Color.MAGENTA, Color.ORANGE);
		openFileButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int returnValue = fileChooser.showOpenDialog(ChiptuneTracker.asciiTerminal);
			}
		});
		terminalButtons.add(openFileButton);
		
		AsciiTerminalButton saveButton = new AsciiTerminalButton(ChiptuneTracker.asciiPanel, "Save", 5, startY + 4, Color.MAGENTA, Color.ORANGE);
		saveButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		terminalButtons.add(saveButton);
		
		AsciiTerminalButton saveAsButton = new AsciiTerminalButton(ChiptuneTracker.asciiPanel, "Save as...", 5, startY + 6, Color.MAGENTA, Color.ORANGE);
		saveAsButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		terminalButtons.add(saveAsButton);
		
		AsciiTerminalButton exitButton = new AsciiTerminalButton(ChiptuneTracker.asciiPanel, "Exit", 5, startY + 8, Color.MAGENTA, Color.ORANGE);
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		terminalButtons.add(exitButton);
	}
	
	@Override
	public void init() {
		buttonMenuView.setSelect(true);
		buttonSampleView.setSelect(false);
		buttonPatternView.setSelect(false);
		super.init();
	}
	
	@Override
	public void update(double delta) {
	}

	@Override
	public void paint() {
	}
	
	@Override
	public void quit() {
		super.quit();
	}

}
