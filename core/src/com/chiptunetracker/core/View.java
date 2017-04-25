package com.chiptunetracker.core;

import com.asciiterminal.ui.AsciiSelectableTerminalButton;
import com.asciiterminal.ui.AsciiTerminal;
import com.asciiterminal.ui.AsciiTerminalButton;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;
import java.util.List;

public abstract class View {
	
	public static final Color INDIGO = new Color(0x3D51B5FF);
	public static final Color DEEP_ORANGE = new Color(0xFF7808FF);
	public static final String DOT = String.valueOf((char)239);
	
	protected final ChiptuneTracker chiptuneTracker;
	protected final AsciiTerminal asciiTerminal;
	
	protected List<AsciiTerminalButton> terminalButtons = new ArrayList<>();
	
	// Panels buttons
	protected AsciiSelectableTerminalButton buttonMenuView;
	protected AsciiSelectableTerminalButton buttonSampleView;
	protected AsciiSelectableTerminalButton buttonPatternView;
	
	public View(ChiptuneTracker chiptuneTracker) {
		this.chiptuneTracker = chiptuneTracker;
		this.asciiTerminal = chiptuneTracker.getAsciiTerminal();
		createSwitchViewButtons();
	}
	
	public void createSwitchViewButtons() {
		buttonMenuView = new AsciiSelectableTerminalButton(asciiTerminal, String.valueOf((char)255) + "Menu", 1, 0, Color.WHITE, DEEP_ORANGE, DEEP_ORANGE, DEEP_ORANGE, INDIGO);
		buttonMenuView.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				chiptuneTracker.changeView(chiptuneTracker.getMenuView());
			}
		});
		terminalButtons.add(buttonMenuView);
		
		buttonSampleView = new AsciiSelectableTerminalButton(asciiTerminal, String.valueOf((char)13) + "Sample", 7, 0, Color.WHITE, DEEP_ORANGE, DEEP_ORANGE, DEEP_ORANGE, INDIGO);
		buttonSampleView.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				chiptuneTracker.changeView(chiptuneTracker.getSampleView());
			}
		});
		terminalButtons.add(buttonSampleView);
		
		buttonPatternView = new AsciiSelectableTerminalButton(asciiTerminal, String.valueOf((char)14) + "Pattern", 15, 0, Color.WHITE, DEEP_ORANGE, DEEP_ORANGE, DEEP_ORANGE, INDIGO);
		buttonPatternView.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				chiptuneTracker.changeView(chiptuneTracker.getPatternView());
			}
		});
		terminalButtons.add(buttonPatternView);
	}

	public void init() {
		for(AsciiTerminalButton terminalButton : terminalButtons) {
			asciiTerminal.addActor(terminalButton);
		}
	}
	
	public abstract void update(double delta);
	
	public abstract void paint();
	
	public void quit() {
		for(AsciiTerminalButton terminalButton : terminalButtons) {
			terminalButton.remove();
		}
	}
}
