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

	// pattern buttons
	private AsciiSelectableTerminalButton pattern1;
	private AsciiSelectableTerminalButton pattern2;
	private AsciiSelectableTerminalButton pattern3;
	private AsciiSelectableTerminalButton pattern4;
	
	// Samples buttons
	private AsciiSelectableTerminalButton sample1;
	private AsciiSelectableTerminalButton sample2;
	private AsciiSelectableTerminalButton sample3;
	private AsciiSelectableTerminalButton sample4;
	
	private int patternOffset = 0;
	private int patternCursor = 0;
	
	public PatternView(ChiptuneTracker chiptuneTracker) {
		super(chiptuneTracker);
		createPatternButtons();
		createSwitchViewButtons();
		createSampleButtons();
		changePatternOffset();
	}
	
	public void createPatternButtons() {
		// Down button
		AsciiTerminalButton buttonDownSample = new AsciiTerminalButton(ChiptuneTracker.asciiPanel, String.valueOf((char)17), 9, 2, Color.MAGENTA, Color.ORANGE);
		buttonDownSample.setCursor(new Cursor(Cursor.HAND_CURSOR));
		buttonDownSample.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				patternOffset--;
				changePatternOffset();
				changePatternButtons();
			}
		});
		terminalButtons.add(buttonDownSample);
		
		// Up button
		AsciiTerminalButton buttonUpSample = new AsciiTerminalButton(ChiptuneTracker.asciiPanel, String.valueOf((char)16), ChiptuneTracker.WINDOW_WIDTH - 6, 2, Color.MAGENTA, Color.ORANGE);
		buttonUpSample.setCursor(new Cursor(Cursor.HAND_CURSOR));
		buttonUpSample.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				patternOffset++;
				changePatternOffset();
				changePatternButtons();
			}
		});
		terminalButtons.add(buttonUpSample);
		
		// pattern1
		pattern1 = new AsciiSelectableTerminalButton(ChiptuneTracker.asciiPanel, String.format("%02d", patternCursor), 11, 2, Color.WHITE, Color.ORANGE, Color.ORANGE);
		pattern1.setCursor(new Cursor(Cursor.HAND_CURSOR));
		pattern1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				patternCursor = patternOffset;
				changePatternButtons();
				changeSampleButtons();
			}
		});
		pattern1.setSelect(true);
		terminalButtons.add(pattern1);
		
		// pattern2
		pattern2 = new AsciiSelectableTerminalButton(ChiptuneTracker.asciiPanel, String.format("%02d", patternCursor + 1), 14, 2, Color.WHITE, Color.ORANGE, Color.ORANGE);
		pattern2.setCursor(new Cursor(Cursor.HAND_CURSOR));
		pattern2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				patternCursor = patternOffset + 1;
				changePatternButtons();
				changeSampleButtons();
			}
		});
		terminalButtons.add(pattern2);
		
		// pattern3
		pattern3 = new AsciiSelectableTerminalButton(ChiptuneTracker.asciiPanel, String.format("%02d", patternCursor + 2), 17, 2, Color.WHITE, Color.ORANGE, Color.ORANGE);
		pattern3.setCursor(new Cursor(Cursor.HAND_CURSOR));
		pattern3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				patternCursor = patternOffset + 2;
				changePatternButtons();
				changeSampleButtons();
			}
		});
		terminalButtons.add(pattern3);
		
		// pattern4
		pattern4 = new AsciiSelectableTerminalButton(ChiptuneTracker.asciiPanel, String.format("%02d", patternCursor + 3), 20, 2, Color.WHITE, Color.ORANGE, Color.ORANGE);
		pattern4.setCursor(new Cursor(Cursor.HAND_CURSOR));
		pattern4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				patternCursor = patternOffset + 3;
				changePatternButtons();
				changeSampleButtons();
			}
		});
		terminalButtons.add(pattern4);
	}
	
	public void createSampleButtons() {
		sample1 = new AsciiSelectableTerminalButton(ChiptuneTracker.asciiPanel, String.valueOf((char)253), 1, 4, Color.WHITE, Color.YELLOW, Color.GREEN);
		sample1.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sample1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(sample1.isSelect()) {
					ChiptuneTracker.patterns.get(patternCursor).sample1 = null;
				}
				else {
					ChiptuneTracker.patterns.get(patternCursor).sample1 = 0;
				}
				changeSampleButtons();
			}
		});
		terminalButtons.add(sample1);
		
		sample2 = new AsciiSelectableTerminalButton(ChiptuneTracker.asciiPanel, String.valueOf((char)253), 8, 4, Color.WHITE, Color.YELLOW, Color.GREEN);
		sample2.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sample2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(sample2.isSelect()) {
					ChiptuneTracker.patterns.get(patternCursor).sample2 = null;
				}
				else {
					ChiptuneTracker.patterns.get(patternCursor).sample2 = 0;
				}
				changeSampleButtons();
			}
		});
		terminalButtons.add(sample2);
		
		sample3 = new AsciiSelectableTerminalButton(ChiptuneTracker.asciiPanel, String.valueOf((char)253), 15, 4, Color.WHITE, Color.YELLOW, Color.GREEN);
		sample3.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sample3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(sample3.isSelect()) {
					ChiptuneTracker.patterns.get(patternCursor).sample3 = null;
				}
				else {
					ChiptuneTracker.patterns.get(patternCursor).sample3 = 0;
				}
				changeSampleButtons();
			}
		});
		terminalButtons.add(sample3);
		
		sample4 = new AsciiSelectableTerminalButton(ChiptuneTracker.asciiPanel, String.valueOf((char)253), 22, 4, Color.WHITE, Color.YELLOW, Color.GREEN);
		sample4.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sample4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(sample4.isSelect()) {
					ChiptuneTracker.patterns.get(patternCursor).sample4 = null;
				}
				else {
					ChiptuneTracker.patterns.get(patternCursor).sample4 = 0;
				}
				changeSampleButtons();
			}
		});
		terminalButtons.add(sample4);
	}
	
	@Override
	public void init() {
		for(int i = 0; i < ChiptuneTracker.WINDOW_WIDTH; i++) {
			ChiptuneTracker.asciiPanel.write(i, 0, ' ', Color.WHITE, ChiptuneTracker.INDIGO);
			ChiptuneTracker.asciiPanel.write(i, ChiptuneTracker.WINDOW_HEIGHT - 1, ' ', Color.WHITE, ChiptuneTracker.INDIGO);
		}
		
		buttonSampleView.setSelect(false);
		buttonPatternView.setSelect(true);
		super.init();
	}
	
	public void changePatternOffset() {
		if(patternOffset < 0) {
			patternOffset = 0;
		}
		if(patternOffset > 95) {
			patternOffset = 95;
		}
		
		for(int i = 0; i <= 4 ; i++) {
			if(ChiptuneTracker.patterns.size() < patternOffset + i) {
				ChiptuneTracker.patterns.add(new Pattern());
			}
		}
	}
	
	public void changePatternButtons() {
		pattern1.setName(String.format("%02d", patternOffset));
		pattern2.setName(String.format("%02d", patternOffset + 1));
		pattern3.setName(String.format("%02d", patternOffset + 2));
		pattern4.setName(String.format("%02d", patternOffset + 3));
		
		if(patternCursor == patternOffset) {
			pattern1.setSelect(true);
			pattern2.setSelect(false);
			pattern3.setSelect(false);
			pattern4.setSelect(false);
		}
		else if(patternCursor == patternOffset + 1) {
			pattern1.setSelect(false);
			pattern2.setSelect(true);
			pattern3.setSelect(false);
			pattern4.setSelect(false);
		}
		else if(patternCursor == patternOffset + 2) {
			pattern1.setSelect(false);
			pattern2.setSelect(false);
			pattern3.setSelect(true);
			pattern4.setSelect(false);
		}
		else if(patternCursor == patternOffset + 3) {
			pattern1.setSelect(false);
			pattern2.setSelect(false);
			pattern3.setSelect(false);
			pattern4.setSelect(true);
		}
		else {
			pattern1.setSelect(false);
			pattern2.setSelect(false);
			pattern3.setSelect(false);
			pattern4.setSelect(false);
		}
	}
	
	public void changeSampleButtons() {
		Pattern pattern = ChiptuneTracker.patterns.get(patternCursor);
		
		if(pattern.sample1 != null) {
			sample1.setName(String.valueOf((char)254));
			sample1.setSelect(true);
		}
		else {
			sample1.setName(String.valueOf((char)253));
			sample1.setSelect(false);
		}
		
		if(pattern.sample2 != null) {
			sample2.setName(String.valueOf((char)254));
			sample2.setSelect(true);
		}
		else {
			sample2.setName(String.valueOf((char)253));
			sample2.setSelect(false);
		}
		
		if(pattern.sample3 != null) {
			sample3.setName(String.valueOf((char)254));
			sample3.setSelect(true);
		}
		else {
			sample3.setName(String.valueOf((char)253));
			sample3.setSelect(false);
		}
		
		if(pattern.sample4 != null) {
			sample4.setName(String.valueOf((char)254));
			sample4.setSelect(true);
		}
		else {
			sample4.setName(String.valueOf((char)253));
			sample4.setSelect(false);
		}
	}
	
	@Override
	public void update(double delta) {
		// TODO Auto-generated method stub
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
		
		Pattern pattern = ChiptuneTracker.patterns.get(patternCursor);
		
		Sample sample1 = null;
		Sample sample2 = null;
		Sample sample3 = null;
		Sample sample4 = null;
		if(pattern.sample1 != null) {
			sample1 = ChiptuneTracker.samples.get(pattern.sample1);
			ChiptuneTracker.asciiPanel.writeString(4, 4, String.format("%02d", pattern.sample1), Color.WHITE);
		}
		else {
			ChiptuneTracker.asciiPanel.writeString(4, 4, "  ", Color.WHITE);
		}
		
		if(pattern.sample2 != null) {
			sample2 = ChiptuneTracker.samples.get(pattern.sample2);
			ChiptuneTracker.asciiPanel.writeString(11, 4, String.format("%02d", pattern.sample2), Color.WHITE);
		}
		else {
			ChiptuneTracker.asciiPanel.writeString(11, 4, "  ", Color.WHITE);
		}
		
		if(pattern.sample3 != null) {
			sample3 = ChiptuneTracker.samples.get(pattern.sample3);
			ChiptuneTracker.asciiPanel.writeString(18, 4, String.format("%02d", pattern.sample3), Color.WHITE);
		}
		else {
			ChiptuneTracker.asciiPanel.writeString(18, 4, "  ", Color.WHITE);
		}
		
		if(pattern.sample4 != null) {
			sample4 = ChiptuneTracker.samples.get(pattern.sample4);
			ChiptuneTracker.asciiPanel.writeString(25, 4, String.format("%02d", pattern.sample4), Color.WHITE);
		}
		else {
			ChiptuneTracker.asciiPanel.writeString(25, 4, "  ", Color.WHITE);
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
