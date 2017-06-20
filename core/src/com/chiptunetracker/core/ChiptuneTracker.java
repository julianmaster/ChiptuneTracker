package com.chiptunetracker.core;

import com.asciiterminal.ui.AsciiTerminal;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.chiptunetracker.model.Data;
import com.chiptunetracker.view.MenuView;
import com.chiptunetracker.view.PatternView;
import com.chiptunetracker.view.SampleView;
import com.chiptunetracker.view.View;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.util.dialog.Dialogs;

import javax.swing.*;

public class ChiptuneTracker extends Game {
	public static final String TITLE = "ChiptuneTracker";
	public static final int WINDOW_WIDTH = 29;
	public static final int WINDOW_HEIGHT = 18;
	public static final String TILESET_FILE = "wanderlust.png";
	public static final String ICON_FILE = "icon.png";
	public static final int CHARACTER_WIDTH = 12;
	public static final int CHARACTER_HEIGHT = 12;
	public static final int SCALE = 2;

	private static ChiptuneTracker instance = new ChiptuneTracker();

	private AsciiTerminal asciiTerminal;

	private boolean initSampleView = true;
	private boolean initPatternView = true;

	private String currentFile = null;
	private Data data = new Data();
	private DataManager dataManager;
	private boolean changeData = false;
	private Chanels chanels = new Chanels();

	private MenuView menuView;
	private SampleView sampleView;
	private PatternView patternView;

	private ChiptuneTracker() {
	}

	@Override
	public void create () {
		asciiTerminal = new AsciiTerminal(TITLE, WINDOW_WIDTH, WINDOW_HEIGHT, TILESET_FILE, CHARACTER_WIDTH, CHARACTER_HEIGHT, SCALE);
		asciiTerminal.setDefaultCharacterBackgroundColor(Color.DARK_GRAY);
		asciiTerminal.setDefaultCharacterColor(Color.WHITE);

		VisUI.load();

//		com.chiptunetracker.player.Chanel c = new com.chiptunetracker.player.Chanel();

		dataManager = new DataManager();

		menuView = new MenuView(this);
		sampleView = new SampleView(this);
		patternView = new PatternView(this);

		setScreen(sampleView);
	}

	@Override
	public void render () {
		// Sounds update
		chanels.update();

		// Render
		asciiTerminal.render(Gdx.graphics.getDeltaTime());

		// Clear
		asciiTerminal.clear();

		// Update
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		asciiTerminal.resize(width, height);
	}

	@Override
	public void dispose () {
		super.dispose();
		menuView.dispose();
		sampleView.dispose();
		patternView.dispose();
		asciiTerminal.dispose();
		VisUI.dispose();
	}

	public static ChiptuneTracker getInstance() {
		return instance;
	}

	public boolean isInitSampleView() {
		return initSampleView;
	}

	public void setInitSampleView(boolean initSampleView) {
		this.initSampleView = initSampleView;
	}

	public boolean isInitPatternView() {
		return initPatternView;
	}

	public void setInitPatternView(boolean initPatternView) {
		this.initPatternView = initPatternView;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public DataManager getDataManager() {
		return dataManager;
	}

	public void setDataManager(DataManager dataManager) {
		this.dataManager = dataManager;
	}

	public boolean isChangeData() {
		return changeData;
	}

	public void setChangeData(boolean changeData) {
		this.changeData = changeData;
	}

	public AsciiTerminal getAsciiTerminal() {
		return asciiTerminal;
	}

	public MenuView getMenuView() {
		return menuView;
	}

	public SampleView getSampleView() {
		return sampleView;
	}

	public PatternView getPatternView() {
		return patternView;
	}

	public Chanels getChanels() {
		return chanels;
	}
}
