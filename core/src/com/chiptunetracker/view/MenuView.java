package com.chiptunetracker.view;

import com.asciiterminal.ui.AsciiSelectableTerminalButton;
import com.asciiterminal.ui.AsciiTerminal;
import com.asciiterminal.ui.AsciiTerminalButton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.chiptunetracker.core.ChiptuneTracker;
import com.chiptunetracker.core.DataManager;
import com.kotcrab.vis.ui.util.dialog.Dialogs;

import javax.swing.*;

import static com.sun.scenario.Settings.getBoolean;

public class MenuView extends View {

	private boolean qwertyKeys = true;

	private Preferences prefs;

	AsciiSelectableTerminalButton qwertyButton;
	AsciiSelectableTerminalButton azertyButton;

	public MenuView(ChiptuneTracker chiptuneTracker) {
		super(chiptuneTracker);
		prefs = Gdx.app.getPreferences(ChiptuneTracker.TITLE);
		try {
			loadPreferences();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
				Gdx.app.exit();
			}
		});
		getListActor().add(exitButton);


		qwertyButton = new AsciiSelectableTerminalButton(asciiTerminal, "QWERTY", 16, 16, Color.LIGHT_GRAY, Color.WHITE, Color.WHITE, Color.CORAL, asciiTerminal.getDefaultCharacterBackgroundColor());
		qwertyButton.setSelected(qwertyKeys);
		qwertyButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				qwertyKeys = true;
				azertyButton.setSelected(false);
				qwertyButton.setSelected(true);
				savePreferences();
			}
		});
		getListActor().add(qwertyButton);

		azertyButton = new AsciiSelectableTerminalButton(asciiTerminal, "AZERTY", 23, 16, Color.LIGHT_GRAY, Color.WHITE, Color.WHITE, Color.CORAL, asciiTerminal.getDefaultCharacterBackgroundColor());
		azertyButton.setSelected(!qwertyKeys);
		azertyButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				qwertyKeys = false;
				qwertyButton.setSelected(false);
				azertyButton.setSelected(true);
				savePreferences();
			}
		});
		getListActor().add(azertyButton);
	}
	
	@Override
	public void show() {
		super.show();
		buttonMenuView.setSelected(true);
		buttonSampleView.setSelected(false);
		buttonPatternView.setSelected(false);
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		AsciiTerminal asciiTerminal = chiptuneTracker.getAsciiTerminal();

		asciiTerminal.writeString(6, 16, "Keyboard:", Color.LIGHT_GRAY);
	}

	private void loadPreferences() throws Exception {
		qwertyKeys = prefs.getBoolean("qwerty", true);
	}

	private void savePreferences() {
		prefs.putBoolean("qwerty", qwertyKeys);
		prefs.flush();
	}

	public boolean isQwertyKeys() {
		return qwertyKeys;
	}
}
