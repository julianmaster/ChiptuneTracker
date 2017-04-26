package com.chiptunetracker.core;

import com.asciiterminal.ui.AsciiTerminalButton;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import javax.swing.*;

public class MenuView extends View {
	
	private boolean exportMessage = false;
	private boolean runExport = false;
	
	public MenuView(ChiptuneTracker chiptuneTracker) {
		super(chiptuneTracker);
		createMenuButtons();
	}

	public void createMenuButtons() {
		int startY = 3;
		final DataManager dataManager = chiptuneTracker.getDataManager();

		AsciiTerminalButton newButton = new AsciiTerminalButton(asciiTerminal, "New", 5, startY, Color.MAGENTA, Color.ORANGE, Color.ORANGE, asciiTerminal.getDefaultCharacterBackgroundColor());
		newButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				try {
					dataManager.newFile();
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(null, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		terminalButtons.add(newButton);
		
		AsciiTerminalButton openFileButton = new AsciiTerminalButton(asciiTerminal, "Open File...", 5, startY + 2, Color.MAGENTA, Color.ORANGE, Color.ORANGE, asciiTerminal.getDefaultCharacterBackgroundColor());
		openFileButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				try {
					dataManager.open();
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(null, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		terminalButtons.add(openFileButton);
		
		AsciiTerminalButton saveButton = new AsciiTerminalButton(asciiTerminal, "Save", 5, startY + 4, Color.MAGENTA, Color.ORANGE, Color.ORANGE, asciiTerminal.getDefaultCharacterBackgroundColor());
		saveButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				try {
					dataManager.save();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		terminalButtons.add(saveButton);
		
		AsciiTerminalButton saveAsButton = new AsciiTerminalButton(asciiTerminal, "Save as...", 5, startY + 6, Color.MAGENTA, Color.ORANGE, Color.ORANGE, asciiTerminal.getDefaultCharacterBackgroundColor());
		saveAsButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				try {
					dataManager.saveAs();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		terminalButtons.add(saveAsButton);
		
		AsciiTerminalButton exportButton = new AsciiTerminalButton(asciiTerminal, "Export", 5, startY + 8, Color.MAGENTA, Color.ORANGE, Color.ORANGE, asciiTerminal.getDefaultCharacterBackgroundColor());
		exportButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				try {
					dataManager.initExport(chiptuneTracker.getMenuView());
				} catch(Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		terminalButtons.add(exportButton);
		
		AsciiTerminalButton exitButton = new AsciiTerminalButton(asciiTerminal, "Exit", 5, startY + 10, Color.MAGENTA, Color.ORANGE, Color.ORANGE, asciiTerminal.getDefaultCharacterBackgroundColor());
		exitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				try {
					dataManager.exit();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		terminalButtons.add(exitButton);
	}
	
	@Override
	public void init() {
		buttonMenuView.setSelected(true);
		buttonSampleView.setSelected(false);
		buttonPatternView.setSelected(false);
		super.init();
	}
	
	@Override
	public void update(double delta) {
	}
	
	public void showExportMessage() {
		exportMessage = true;
	}

	@Override
	public void paint() {
		for(int i = 0; i < ChiptuneTracker.WINDOW_WIDTH; i++) {
			asciiTerminal.write(i, 0, ' ', Color.WHITE, INDIGO);
			asciiTerminal.write(i, ChiptuneTracker.WINDOW_HEIGHT - 1, ' ', Color.WHITE, INDIGO);
		}
		
		if(runExport) {
			runExport = false;
			try {
				chiptuneTracker.getDataManager().runExport();
			} catch (Exception exception) {
				JOptionPane.showMessageDialog(null, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
			for(int i = 0; i < ChiptuneTracker.WINDOW_WIDTH; i++) {
				asciiTerminal.write(i, ChiptuneTracker.WINDOW_HEIGHT - 1, ' ', Color.WHITE, INDIGO);
			}
		}
		
		if(exportMessage) {
			runExport = true;
			exportMessage = false;
			asciiTerminal.writeString(1, ChiptuneTracker.WINDOW_HEIGHT - 1, "Export the project", Color.WHITE, INDIGO);
		}
	}
	
	@Override
	public void quit() {
		super.quit();
	}

}
