package main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import ui.AsciiSelectableTerminalButton;
import ui.AsciiTerminalButton;

public class PatternView extends View {

	private final ChiptuneTracker chiptuneTracker;
	
	private List<AsciiTerminalButton> terminalButtons = new ArrayList<>();
	
	// Panels buttons
	private AsciiSelectableTerminalButton buttonMenuView;
	private AsciiSelectableTerminalButton buttonEditorView;
	private AsciiSelectableTerminalButton buttonTrackerView;
	
	// Samples buttons
	private AsciiSelectableTerminalButton sample1;
	private AsciiSelectableTerminalButton sample2;
	private AsciiSelectableTerminalButton sample3;
	private AsciiSelectableTerminalButton sample4;
	
	// pattern buttons
	private AsciiSelectableTerminalButton pattern1;
	private AsciiSelectableTerminalButton pattern2;
	private AsciiSelectableTerminalButton pattern3;
	private AsciiSelectableTerminalButton pattern4;
	
	private int patternCursor = 0;
	
	public PatternView(ChiptuneTracker chiptuneTracker) {
		this.chiptuneTracker = chiptuneTracker;
		createPatternButtons();
		createSwitchViewButtons();
		createSampleButtons();
		changePattern(0);
	}

	public void createSwitchViewButtons() {
		buttonMenuView = new AsciiSelectableTerminalButton(ChiptuneTracker.asciiPanel, String.valueOf((char)255) + "Menu", 1, 0, Color.WHITE, ChiptuneTracker.DEEP_ORANGE, ChiptuneTracker.DEEP_ORANGE, ChiptuneTracker.INDIGO);
		buttonMenuView.setCursor(new Cursor(Cursor.HAND_CURSOR));
		terminalButtons.add(buttonMenuView);
		
		buttonTrackerView = new AsciiSelectableTerminalButton(ChiptuneTracker.asciiPanel, String.valueOf((char)13) + "Sample", 7, 0, Color.WHITE, ChiptuneTracker.DEEP_ORANGE, ChiptuneTracker.DEEP_ORANGE, ChiptuneTracker.INDIGO);
		buttonTrackerView.setCursor(new Cursor(Cursor.HAND_CURSOR));
		buttonTrackerView.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				chiptuneTracker.changeView(ChiptuneTracker.sampleView);
			}
		});
		terminalButtons.add(buttonTrackerView);
		
		buttonEditorView = new AsciiSelectableTerminalButton(ChiptuneTracker.asciiPanel, String.valueOf((char)14) + "Pattern", 15, 0, Color.WHITE, ChiptuneTracker.DEEP_ORANGE, ChiptuneTracker.DEEP_ORANGE, ChiptuneTracker.INDIGO);
		buttonEditorView.setCursor(new Cursor(Cursor.HAND_CURSOR));
		terminalButtons.add(buttonEditorView);
	}
	
	public void createPatternButtons() {
		// Down button
		AsciiTerminalButton buttonDownSample = new AsciiTerminalButton(ChiptuneTracker.asciiPanel, String.valueOf((char)17), 9, 2, Color.MAGENTA, Color.ORANGE);
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
		
		// Up button
		AsciiTerminalButton buttonUpSample = new AsciiTerminalButton(ChiptuneTracker.asciiPanel, String.valueOf((char)16), ChiptuneTracker.WINDOW_WIDTH - 6, 2, Color.MAGENTA, Color.ORANGE);
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
		
		// pattern1
//		pattern1 = new AsciiTerminalButton(ChiptuneTracker.asciiPanel, String.valueOf((char)16), ChiptuneTracker.WINDOW_WIDTH - 6, 2, Color.MAGENTA, Color.ORANGE);
//		buttonUpSample.setCursor(new Cursor(Cursor.HAND_CURSOR));
//		buttonUpSample.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				patternCursor++;
//				if(patternCursor > 95) {
//					patternCursor = 95;
//				}
//			}
//		});
//		terminalButtons.add(buttonUpSample);
	}
	
	public void createSampleButtons() {
		sample1 = new AsciiSelectableTerminalButton(ChiptuneTracker.asciiPanel, String.valueOf((char)253), 1, 4, Color.WHITE, Color.YELLOW, Color.GREEN);
		sample1.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sample1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(sample1.isSelect()) {
					sample1.setName(String.valueOf((char)253));
					sample1.setSelect(false);
				}
				else {
					sample1.setName(String.valueOf((char)254));
					sample1.setSelect(true);
				}
			}
		});
		terminalButtons.add(sample1);
	}
	
	@Override
	public void init() {
		for(int i = 0; i < ChiptuneTracker.WINDOW_WIDTH; i++) {
			ChiptuneTracker.asciiPanel.write(i, 0, ' ', Color.WHITE, ChiptuneTracker.INDIGO);
			ChiptuneTracker.asciiPanel.write(i, ChiptuneTracker.WINDOW_HEIGHT - 1, ' ', Color.WHITE, ChiptuneTracker.INDIGO);
		}
		
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
	
	public void changePattern(int i) {
		if(i >= 0 && i < 100) {
			patternCursor = i;
		}
		
		if(ChiptuneTracker.patterns.size() < i + 1) {
			ChiptuneTracker.patterns.add(new Pattern());
		}
	}
	
//	public void changeSample(int i) {
//		if(i > 0 && i < 100) {
//			sampleCursor = i;			
//		}
//		
//		if(ChiptuneTracker.samples.size() < i) {
//			ChiptuneTracker.samples.add(new Sample());
//		}
//	}

	@Override
	public void paint() {
		// Pattern
		ChiptuneTracker.asciiPanel.writeString(1, 2, "PATTERN", Color.gray);
		
		ChiptuneTracker.asciiPanel.writeString(11, 2, "AA AA AA AA", Color.gray);

		Pattern pattern = ChiptuneTracker.patterns.get(patternCursor);
		
		Sample sample1 = null;
		Sample sample2 = null;
		Sample sample3 = null;
		Sample sample4 = null;
		if(pattern.sample1 != null) {
			sample1 = chiptuneTracker.samples.get(pattern.sample1);
		}
		if(pattern.sample1 != null) {
			sample2 = chiptuneTracker.samples.get(pattern.sample2);
		}
		if(pattern.sample1 != null) {
			sample3 = chiptuneTracker.samples.get(pattern.sample3);
		}
		if(pattern.sample1 != null) {
			sample4 = chiptuneTracker.samples.get(pattern.sample4);
		}
		
		
		paintSample(sample1, 1, 5);
		paintSample(sample2, 8, 5);
		paintSample(sample3, 15, 5);
		paintSample(sample4, 22, 5);
	}
	
	public void paintSample(Sample sample, int offsetX, int offsetY) {
		for(int i = 0; i < 11; i++) {
			int x = offsetX;
			int y = offsetY + i;
			
			Sound sound = null;
			
			if(sample != null) {
				sound = sample.sounds[i];
			}
			
			if(sound != null) {
				ChiptuneTracker.asciiPanel.writeString(x, y, sound.note.str, Color.WHITE, Color.BLACK);
				ChiptuneTracker.asciiPanel.write(x+2, y, Character.forDigit(sound.octave, 10), Color.GREEN, Color.BLACK);
				ChiptuneTracker.asciiPanel.write(x+3, y,  Character.forDigit(sound.instrument, 10), Color.MAGENTA, Color.BLACK);
				ChiptuneTracker.asciiPanel.write(x+4, y,  Character.forDigit(sound.volume, 10), Color.CYAN, Color.BLACK);
				ChiptuneTracker.asciiPanel.write(x+5, y, (char)239, Color.GRAY, Color.BLACK);
			}
			else {
				for(int j = x; j < x + 6; j++) {
					ChiptuneTracker.asciiPanel.write(j, y, (char)239, Color.GRAY, Color.BLACK);
				}
			}
		}
	}

	@Override
	public void quit() {
		for(AsciiTerminalButton terminalButton : terminalButtons) {
			ChiptuneTracker.asciiPanel.remove(terminalButton);
		}
	}
}
