package main;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import ui.AsciiPanel;
import ui.AsciiTerminal;
import ui.AsciiTerminalButton;

public class MenuView extends View {
	
	private boolean exportMessage = false;
	private boolean runExport = false;
	
	public MenuView(ChiptuneTracker chiptuneTracker) {
		super(chiptuneTracker);
		createMenuButtons();
	}

	public void createMenuButtons() {
		int startY = 3;
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
		
		AsciiTerminalButton exportButton = new AsciiTerminalButton(asciiPanel, "Export", 5, startY + 8, Color.MAGENTA, Color.ORANGE);
		exportButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					dataManager.initExport(ChiptuneTracker.getInstance().getMenuView());
				} catch(Exception exception) {
					JOptionPane.showMessageDialog(asciiTerminal, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		terminalButtons.add(exportButton);
		
		AsciiTerminalButton exitButton = new AsciiTerminalButton(asciiPanel, "Exit", 5, startY + 10, Color.MAGENTA, Color.ORANGE);
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
	}
	
	public void showExportMessage() {
		exportMessage = true;
		System.out.println("coucou - "+exportMessage);
		ChiptuneTracker.getInstance().getAsciiTerminal().repaint();
	}

	@Override
	public void paint() {
		AsciiPanel asciiPanel = ChiptuneTracker.getInstance().getAsciiPanel();
		
		if(runExport) {
			runExport = false;
			try {
				ChiptuneTracker.getInstance().getDataManager().runExport();
			} catch (Exception exception) {
				JOptionPane.showMessageDialog(ChiptuneTracker.getInstance().getAsciiTerminal(), exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
			for(int i = 0; i < ChiptuneTracker.WINDOW_WIDTH; i++) {
				asciiPanel.write(i, ChiptuneTracker.WINDOW_HEIGHT - 1, ' ', Color.WHITE, INDIGO);
			}
		}
		
		if(exportMessage) {
			runExport = true;
			exportMessage = false;
			asciiPanel.writeString(1, ChiptuneTracker.WINDOW_HEIGHT - 1, "Export the project", Color.WHITE, INDIGO);
		}
	}
	
	@Override
	public void quit() {
		super.quit();
	}

}
