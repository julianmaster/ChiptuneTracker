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

	private static ChiptuneTracker chiptuneTracker;
	
	private List<AsciiTerminalButton> terminalButtons = new ArrayList<>();
	
	// Panels buttons
	private AsciiSelectableTerminalButton buttonEditorView;
	private AsciiSelectableTerminalButton buttonTrackerView;
	
	public EditorView(ChiptuneTracker chiptuneTracker) {
		this.chiptuneTracker = chiptuneTracker;
		
		createSwitchViewButtons();
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

	@Override
	public void update(double delta) {
		// TODO Auto-generated method stub
	}

	@Override
	public void paint() {
		// TODO Auto-generated method stub
	}

	@Override
	public void quit() {
		for(AsciiTerminalButton terminalButton : terminalButtons) {
			ChiptuneTracker.asciiPanel.remove(terminalButton);
		}
	}
}
