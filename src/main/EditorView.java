package main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import ui.AsciiSelectableTerminalButton;
import ui.AsciiTerminalButton;

public class EditorView extends View {

	private final ChiptuneTracker chiptuneTracker;
	
	private List<AsciiTerminalButton> terminalButtons = new ArrayList<>();
	
	// Panels buttons
	private AsciiSelectableTerminalButton buttonEditorView;
	private AsciiSelectableTerminalButton buttonTrackerView;
	
	private int patternCursor = 0;
	
	public EditorView(ChiptuneTracker chiptuneTracker) {
		this.chiptuneTracker = chiptuneTracker;
		createPatternButtons();
		createSwitchViewButtons();
	}

	public void createPatternButtons() {
		AsciiTerminalButton buttonDownSample = new AsciiTerminalButton(ChiptuneTracker.asciiPanel, String.valueOf((char)17), 9, 1, Color.MAGENTA, Color.ORANGE);
		buttonDownSample.setCursor(new Cursor(Cursor.HAND_CURSOR));
		buttonDownSample.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				patternCursor--;
				if(patternCursor < 0) {
					patternCursor = 0;
				}
			}
		});
		terminalButtons.add(buttonDownSample);
		
		AsciiTerminalButton buttonUpSample = new AsciiTerminalButton(ChiptuneTracker.asciiPanel, String.valueOf((char)16), ChiptuneTracker.WINDOW_WIDTH - 12, 1, Color.MAGENTA, Color.ORANGE);
		buttonUpSample.setCursor(new Cursor(Cursor.HAND_CURSOR));
		buttonUpSample.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				patternCursor++;
				if(patternCursor > 95) {
					patternCursor = 95;
				}
			}
		});
		terminalButtons.add(buttonUpSample);
	}
	
	public void createSwitchViewButtons() {
		buttonTrackerView = new AsciiSelectableTerminalButton(ChiptuneTracker.asciiPanel, String.valueOf((char)13) + "Tra", ChiptuneTracker.WINDOW_WIDTH - 10, 1, Color.MAGENTA, Color.ORANGE, Color.ORANGE);
		buttonTrackerView.setCursor(new Cursor(Cursor.HAND_CURSOR));
		buttonTrackerView.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				chiptuneTracker.changeView(ChiptuneTracker.trackerView);
			}
		});
		terminalButtons.add(buttonTrackerView);
		
		buttonEditorView = new AsciiSelectableTerminalButton(ChiptuneTracker.asciiPanel, String.valueOf((char)14) + "Edi", ChiptuneTracker.WINDOW_WIDTH - 5, 1, Color.MAGENTA, Color.ORANGE, Color.ORANGE);
		buttonEditorView.setCursor(new Cursor(Cursor.HAND_CURSOR));
		terminalButtons.add(buttonEditorView);
	}
	
	@Override
	public void init() {
		buttonTrackerView.setSelect(false);
		buttonEditorView.setSelect(true);
		for(AsciiTerminalButton terminalButton : terminalButtons) {
			ChiptuneTracker.asciiPanel.add(terminalButton);
		}
	}
	
	public void changeButtons() {
		
	}

	@Override
	public void update(double delta) {
		// TODO Auto-generated method stub
	}

	@Override
	public void paint() {
		// Pattern
		
		ChiptuneTracker.asciiPanel.writeString(1, 1, "PATTERN", Color.gray);
		
		ChiptuneTracker.asciiPanel.writeString(11, 1, "PATTERN", Color.gray);
	}

	@Override
	public void quit() {
		for(AsciiTerminalButton terminalButton : terminalButtons) {
			ChiptuneTracker.asciiPanel.remove(terminalButton);
		}
	}
}
