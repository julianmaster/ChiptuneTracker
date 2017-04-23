package com.chiptunetracker.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ui.AsciiSelectableTerminalButton;
import ui.AsciiTerminal;
import ui.AsciiTerminalButton;

import java.util.ArrayList;
import java.util.List;

public abstract class View {
	
	public static final Color INDIGO = new Color(61f, 81f, 181f, 0f);
	public static final Color DEEP_ORANGE = new Color(255f, 120f, 8f, 0f);
	public static final String DOT = String.valueOf((char)239);
	
	private final ChiptuneTracker chiptuneTracker;
	
	protected List<AsciiTerminalButton> terminalButtons = new ArrayList<>();
	
	// Panels buttons
	protected AsciiSelectableTerminalButton buttonMenuView;
	protected AsciiSelectableTerminalButton buttonSampleView;
	protected AsciiSelectableTerminalButton buttonPatternView;
	
	public View(ChiptuneTracker chiptuneTracker) {
		this.chiptuneTracker = chiptuneTracker;
		createSwitchViewButtons();
	}
	
	public void createSwitchViewButtons() {
		AsciiTerminal asciiTerminal = ChiptuneTracker.getInstance().getAsciiTerminal();

		buttonMenuView = new AsciiSelectableTerminalButton(asciiTerminal, String.valueOf((char)255) + "Menu", 1, 0, Color.WHITE, DEEP_ORANGE, DEEP_ORANGE, INDIGO);
		buttonMenuView.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				chiptuneTracker.changeView(ChiptuneTracker.getInstance().getMenuView());
			}
		});
		terminalButtons.add(buttonMenuView);
		
		buttonSampleView = new AsciiSelectableTerminalButton(asciiTerminal, String.valueOf((char)13) + "Sample", 7, 0, Color.WHITE, DEEP_ORANGE, DEEP_ORANGE, INDIGO);
		buttonSampleView.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				chiptuneTracker.changeView(ChiptuneTracker.getInstance().getSampleView());
			}
		});
		terminalButtons.add(buttonSampleView);
		
		buttonPatternView = new AsciiSelectableTerminalButton(asciiTerminal, String.valueOf((char)14) + "Pattern", 15, 0, Color.WHITE, DEEP_ORANGE, DEEP_ORANGE, INDIGO);
		buttonPatternView.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				chiptuneTracker.changeView(ChiptuneTracker.getInstance().getPatternView());
			}
		});
		terminalButtons.add(buttonPatternView);
	}

	public void init() {
		AsciiTerminal asciiTerminal = ChiptuneTracker.getInstance().getAsciiTerminal();
		
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
