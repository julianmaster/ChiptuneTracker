package main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import ui.AsciiSelectableTerminalButton;
import ui.AsciiTerminalButton;

public abstract class View {
	
	public static final Color INDIGO = new Color(61, 81, 181);
	public static final Color DEEP_ORANGE = new Color(255, 120, 8);
	
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
		buttonMenuView = new AsciiSelectableTerminalButton(ChiptuneTracker.asciiPanel, String.valueOf((char)255) + "Menu", 1, 0, Color.WHITE, DEEP_ORANGE, DEEP_ORANGE, INDIGO);
		buttonMenuView.setCursor(new Cursor(Cursor.HAND_CURSOR));
		buttonMenuView.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				chiptuneTracker.changeView(ChiptuneTracker.menuView);
			}
		});
		terminalButtons.add(buttonMenuView);
		
		buttonSampleView = new AsciiSelectableTerminalButton(ChiptuneTracker.asciiPanel, String.valueOf((char)13) + "Sample", 7, 0, Color.WHITE, DEEP_ORANGE, DEEP_ORANGE, INDIGO);
		buttonSampleView.setCursor(new Cursor(Cursor.HAND_CURSOR));
		buttonSampleView.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				chiptuneTracker.changeView(ChiptuneTracker.sampleView);
			}
		});
		terminalButtons.add(buttonSampleView);
		
		buttonPatternView = new AsciiSelectableTerminalButton(ChiptuneTracker.asciiPanel, String.valueOf((char)14) + "Pattern", 15, 0, Color.WHITE, DEEP_ORANGE, DEEP_ORANGE, INDIGO);
		buttonPatternView.setCursor(new Cursor(Cursor.HAND_CURSOR));
		buttonPatternView.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				chiptuneTracker.changeView(ChiptuneTracker.patternView);
			}
		});
		terminalButtons.add(buttonPatternView);
	}

	public void init() {
		for(int i = 0; i < ChiptuneTracker.WINDOW_WIDTH; i++) {
			ChiptuneTracker.asciiPanel.write(i, 0, ' ', Color.WHITE, INDIGO);
			ChiptuneTracker.asciiPanel.write(i, ChiptuneTracker.WINDOW_HEIGHT - 1, ' ', Color.WHITE, INDIGO);
		}
		
		for(AsciiTerminalButton terminalButton : terminalButtons) {
			ChiptuneTracker.asciiPanel.add(terminalButton);
		}
	}
	
	public abstract void update(double delta);
	
	public abstract void paint();
	
	public void quit() {
		for(AsciiTerminalButton terminalButton : terminalButtons) {
			ChiptuneTracker.asciiPanel.remove(terminalButton);
		}
	}
}
