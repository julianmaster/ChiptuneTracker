package com.chiptunetracker.view;

import com.asciiterminal.ui.AsciiTerminalButton;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.chiptunetracker.core.ChiptuneTracker;
import com.chiptunetracker.core.DataManager;
import com.kotcrab.vis.ui.util.dialog.Dialogs;

import javax.swing.*;

public class MenuView extends View {
	
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
				dataManager.newFile();
			}
		});
		getListActor().add(newButton);
		
		AsciiTerminalButton openFileButton = new AsciiTerminalButton(asciiTerminal, "Open File...", 5, startY + 2, Color.MAGENTA, Color.ORANGE, Color.ORANGE, asciiTerminal.getDefaultCharacterBackgroundColor());
		openFileButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dataManager.open();
			}
		});
		getListActor().add(openFileButton);
		
		AsciiTerminalButton saveButton = new AsciiTerminalButton(asciiTerminal, "Save", 5, startY + 4, Color.MAGENTA, Color.ORANGE, Color.ORANGE, asciiTerminal.getDefaultCharacterBackgroundColor());
		saveButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				try {
					dataManager.save();
				} catch (Exception e) {
					Dialogs.showErrorDialog(chiptuneTracker.getAsciiTerminal().getStage(), e.getMessage());
				}
			}
		});
		getListActor().add(saveButton);
		
		AsciiTerminalButton saveAsButton = new AsciiTerminalButton(asciiTerminal, "Save as...", 5, startY + 6, Color.MAGENTA, Color.ORANGE, Color.ORANGE, asciiTerminal.getDefaultCharacterBackgroundColor());
		saveAsButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dataManager.saveAs();
			}
		});
		getListActor().add(saveAsButton);
		
		AsciiTerminalButton exportButton = new AsciiTerminalButton(asciiTerminal, "Export", 5, startY + 8, Color.MAGENTA, Color.ORANGE, Color.ORANGE, asciiTerminal.getDefaultCharacterBackgroundColor());
		exportButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dataManager.initExport(chiptuneTracker.getMenuView());
			}
		});
		getListActor().add(exportButton);
		
		AsciiTerminalButton exitButton = new AsciiTerminalButton(asciiTerminal, "Exit", 5, startY + 10, Color.MAGENTA, Color.ORANGE, Color.ORANGE, asciiTerminal.getDefaultCharacterBackgroundColor());
		exitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				try {
					dataManager.exit();
				} catch (Exception e) {
					Dialogs.showErrorDialog(chiptuneTracker.getAsciiTerminal().getStage(), e.getMessage());
				}
			}
		});
		getListActor().add(exitButton);
	}
	
	@Override
	public void show() {
		super.show();
		buttonMenuView.setSelected(true);
		buttonSampleView.setSelected(false);
		buttonPatternView.setSelected(false);
	}
	
	public void runExport() {
		runExport = true;
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		if(runExport) {
			runExport = false;
			try {
				chiptuneTracker.getDataManager().runExport();
			} catch (Exception e) {
				Dialogs.showErrorDialog(chiptuneTracker.getAsciiTerminal().getStage(), e.getMessage());
			}
		}
	}
}
