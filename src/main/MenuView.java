package main;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JOptionPane;

import ui.AsciiPanel;
import ui.AsciiTerminal;
import ui.AsciiTerminalButton;
import ui.CustomAsciiTerminal;

public class MenuView extends View {
	
	public MenuView(ChiptuneTracker chiptuneTracker) {
		super(chiptuneTracker);
		createMenuButtons();
	}

	public void createMenuButtons() {
		int startY = 4;
		final AsciiTerminal asciiTerminal = ChiptuneTracker.getInstance().getAsciiTerminal();
		final DataManager dataManager = ChiptuneTracker.getInstance().getDataManager();
		AsciiPanel asciiPanel = ChiptuneTracker.getInstance().getAsciiPanel();
		
		AsciiTerminalButton newButton = new AsciiTerminalButton(asciiPanel, "New", 5, startY, Color.MAGENTA, Color.ORANGE);
		newButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					dataManager.newFile();
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(asciiTerminal, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		terminalButtons.add(newButton);
		
		AsciiTerminalButton openFileButton = new AsciiTerminalButton(asciiPanel, "Open File...", 5, startY + 2, Color.MAGENTA, Color.ORANGE);
		openFileButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					dataManager.open();
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(asciiTerminal, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		terminalButtons.add(openFileButton);
		
		AsciiTerminalButton saveButton = new AsciiTerminalButton(asciiPanel, "Save", 5, startY + 4, Color.MAGENTA, Color.ORANGE);
		saveButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					dataManager.save();
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(asciiTerminal, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		terminalButtons.add(saveButton);
		
		AsciiTerminalButton saveAsButton = new AsciiTerminalButton(asciiPanel, "Save as...", 5, startY + 6, Color.MAGENTA, Color.ORANGE);
		saveAsButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					dataManager.saveAs();
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(asciiTerminal, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		terminalButtons.add(saveAsButton);
		
		AsciiTerminalButton exitButton = new AsciiTerminalButton(asciiPanel, "Exit", 5, startY + 8, Color.MAGENTA, Color.ORANGE);
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					dataManager.exit();
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(asciiTerminal, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
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
		CustomAsciiTerminal asciiTerminal = ChiptuneTracker.getInstance().getAsciiTerminal();
		KeyEvent event = asciiTerminal.getEvent();
		
		if(event != null) {
			if(event.getKeyCode() == KeyEvent.VK_SPACE) {
				FileRecorder fileRecorder = new FileRecorder();
				try {
					fileRecorder.savePattern("test.wav");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			asciiTerminal.setEvent(null);
		}
	}

	@Override
	public void paint() {
	}
	
	@Override
	public void quit() {
		super.quit();
	}

}
