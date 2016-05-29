package main;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import ui.AsciiTerminalButton;

public class MenuView extends View {
	
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
				try {
					ChiptuneTracker.dataManager.newFile();
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(ChiptuneTracker.asciiTerminal, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		terminalButtons.add(newButton);
		
		AsciiTerminalButton openFileButton = new AsciiTerminalButton(ChiptuneTracker.asciiPanel, "Open File...", 5, startY + 2, Color.MAGENTA, Color.ORANGE);
		openFileButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					ChiptuneTracker.dataManager.open();
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(ChiptuneTracker.asciiTerminal, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		terminalButtons.add(openFileButton);
		
		AsciiTerminalButton saveButton = new AsciiTerminalButton(ChiptuneTracker.asciiPanel, "Save", 5, startY + 4, Color.MAGENTA, Color.ORANGE);
		saveButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					ChiptuneTracker.dataManager.save();
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(ChiptuneTracker.asciiTerminal, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		terminalButtons.add(saveButton);
		
		AsciiTerminalButton saveAsButton = new AsciiTerminalButton(ChiptuneTracker.asciiPanel, "Save as...", 5, startY + 6, Color.MAGENTA, Color.ORANGE);
		saveAsButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					ChiptuneTracker.dataManager.saveAs();
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(ChiptuneTracker.asciiTerminal, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		terminalButtons.add(saveAsButton);
		
		AsciiTerminalButton exitButton = new AsciiTerminalButton(ChiptuneTracker.asciiPanel, "Exit", 5, startY + 8, Color.MAGENTA, Color.ORANGE);
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					ChiptuneTracker.dataManager.exit();
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(ChiptuneTracker.asciiTerminal, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
	}

	@Override
	public void paint() {
	}
	
	@Override
	public void quit() {
		super.quit();
	}

}
