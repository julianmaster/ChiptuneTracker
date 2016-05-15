package main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

	// Down buttons
	private AsciiTerminalButton sample1Down;
	private AsciiTerminalButton sample2Down;
	private AsciiTerminalButton sample3Down;
	private AsciiTerminalButton sample4Down;
	
	// Up buttons
	private AsciiTerminalButton sample1Up;
	private AsciiTerminalButton sample2Up;
	private AsciiTerminalButton sample3Up;
	private AsciiTerminalButton sample4Up;
	
	private int patternOffset = 0;
	private int patternCursor = 0;
	
	public PatternView(ChiptuneTracker chiptuneTracker) {
		super(chiptuneTracker);
		createPatternButtons();
		createSampleButtons();
		createSampleDownButtons();
		createSampleUpButtons();
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
					ChiptuneTracker.data.patterns.get(patternCursor).sample1 = null;
				}
				else {
					ChiptuneTracker.data.patterns.get(patternCursor).sample1 = 0;
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
					ChiptuneTracker.data.patterns.get(patternCursor).sample2 = null;
				}
				else {
					ChiptuneTracker.data.patterns.get(patternCursor).sample2 = 0;
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
					ChiptuneTracker.data.patterns.get(patternCursor).sample3 = null;
				}
				else {
					ChiptuneTracker.data.patterns.get(patternCursor).sample3 = 0;
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
					ChiptuneTracker.data.patterns.get(patternCursor).sample4 = null;
				}
				else {
					ChiptuneTracker.data.patterns.get(patternCursor).sample4 = 0;
				}
				changeSampleButtons();
			}
		});
		terminalButtons.add(sample4);
	}
	
	public void createSampleDownButtons() {
		sample1Down = new AsciiTerminalButton(ChiptuneTracker.asciiPanel, "-", 3, 4, Color.MAGENTA, Color.ORANGE);
		sample1Down.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sample1Down.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Pattern pattern = ChiptuneTracker.data.patterns.get(patternCursor);
				if(pattern.sample1 != null) {
					changeSample(1, pattern.sample1 - 1);
				}
			}
		});
		
		sample2Down = new AsciiTerminalButton(ChiptuneTracker.asciiPanel, "-", 10, 4, Color.MAGENTA, Color.ORANGE);
		sample2Down.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sample2Down.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Pattern pattern = ChiptuneTracker.data.patterns.get(patternCursor);
				if(pattern.sample2 != null) {
					changeSample(2, pattern.sample2 - 1);
				}
			}
		});
		
		sample3Down = new AsciiTerminalButton(ChiptuneTracker.asciiPanel, "-", 17, 4, Color.MAGENTA, Color.ORANGE);
		sample3Down.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sample3Down.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Pattern pattern = ChiptuneTracker.data.patterns.get(patternCursor);
				if(pattern.sample3 != null) {
					changeSample(3, pattern.sample3 - 1);
				}
			}
		});
		
		sample4Down = new AsciiTerminalButton(ChiptuneTracker.asciiPanel, "-", 24, 4, Color.MAGENTA, Color.ORANGE);
		sample4Down.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sample4Down.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Pattern pattern = ChiptuneTracker.data.patterns.get(patternCursor);
				if(pattern.sample4 != null) {
					changeSample(4, pattern.sample4 - 1);
				}
			}
		});
	}
	
	public void createSampleUpButtons() {
		sample1Up = new AsciiTerminalButton(ChiptuneTracker.asciiPanel, "+", 6, 4, Color.MAGENTA, Color.ORANGE);
		sample1Up.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sample1Up.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Pattern pattern = ChiptuneTracker.data.patterns.get(patternCursor);
				if(pattern.sample1 != null) {
					changeSample(1, pattern.sample1 + 1);
					System.out.println(pattern.sample1 + 1);
				}
			}
		});
		
		sample2Up = new AsciiTerminalButton(ChiptuneTracker.asciiPanel, "+", 13, 4, Color.MAGENTA, Color.ORANGE);
		sample2Up.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sample2Up.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Pattern pattern = ChiptuneTracker.data.patterns.get(patternCursor);
				if(pattern.sample2 != null) {
					changeSample(2, pattern.sample2 + 1);
				}
			}
		});
		
		sample3Up = new AsciiTerminalButton(ChiptuneTracker.asciiPanel, "+", 20, 4, Color.MAGENTA, Color.ORANGE);
		sample3Up.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sample3Up.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Pattern pattern = ChiptuneTracker.data.patterns.get(patternCursor);
				if(pattern.sample3 != null) {
					changeSample(3, pattern.sample3 + 1);
				}
			}
		});
		
		sample4Up = new AsciiTerminalButton(ChiptuneTracker.asciiPanel, "+", 27, 4, Color.MAGENTA, Color.ORANGE);
		sample4Up.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sample4Up.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Pattern pattern = ChiptuneTracker.data.patterns.get(patternCursor);
				if(pattern.sample4 != null) {
					changeSample(4, pattern.sample4 + 1);
				}
			}
		});
	}
	
	@Override
	public void init() {
		if(ChiptuneTracker.initPatternView) {
			ChiptuneTracker.initPatternView = false;
			patternCursor = 0;
			patternOffset = 0;
			changePatternOffset();
			changePatternButtons();
			changeSampleButtons();
		}
		
		buttonMenuView.setSelect(false);
		buttonSampleView.setSelect(false);
		buttonPatternView.setSelect(true);
		super.init();
		if(sample1.isSelect()) {
			ChiptuneTracker.asciiPanel.add(sample1Down);
			ChiptuneTracker.asciiPanel.add(sample1Up);
		}
		if(sample2.isSelect()) {
			ChiptuneTracker.asciiPanel.add(sample2Down);
			ChiptuneTracker.asciiPanel.add(sample2Up);
		}
		if(sample3.isSelect()) {
			ChiptuneTracker.asciiPanel.add(sample3Down);
			ChiptuneTracker.asciiPanel.add(sample3Up);
		}
		if(sample4.isSelect()) {
			ChiptuneTracker.asciiPanel.add(sample4Down);
			ChiptuneTracker.asciiPanel.add(sample4Up);
		}
	}
	
	public void changePatternOffset() {
		if(patternOffset < 0) {
			patternOffset = 0;
		}
		if(patternOffset > 95) {
			patternOffset = 95;
		}
		
		for(int i = 0; i <= 4 ; i++) {
			if(ChiptuneTracker.data.patterns.size() < patternOffset + i) {
				ChiptuneTracker.data.patterns.add(new Pattern());
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
		}
		else {
			pattern1.setSelect(false);
			Pattern pattern = ChiptuneTracker.data.patterns.get(patternOffset);
			if(pattern.sample1 == null && pattern.sample2 == null && pattern.sample3 == null && pattern.sample4 == null) {
				pattern1.setMouseDefaultColor(Color.LIGHT_GRAY);
			}
			else {
				pattern1.setMouseDefaultColor(Color.WHITE);
			}
		}
		
		if(patternCursor == patternOffset + 1) {
			pattern2.setSelect(true);
		}
		else {
			pattern2.setSelect(false);
			Pattern pattern = ChiptuneTracker.data.patterns.get(patternOffset + 1);
			if(pattern.sample1 == null && pattern.sample2 == null && pattern.sample3 == null && pattern.sample4 == null) {
				pattern2.setMouseDefaultColor(Color.LIGHT_GRAY);
			}
			else {
				pattern2.setMouseDefaultColor(Color.WHITE);
			}
		}
		
		if(patternCursor == patternOffset + 2) {
			pattern3.setSelect(true);
		}
		else {
			pattern3.setSelect(false);
			Pattern pattern = ChiptuneTracker.data.patterns.get(patternOffset + 2);
			if(pattern.sample1 == null && pattern.sample2 == null && pattern.sample3 == null && pattern.sample4 == null) {
				pattern3.setMouseDefaultColor(Color.LIGHT_GRAY);
			}
			else {
				pattern3.setMouseDefaultColor(Color.WHITE);
			}
		}
			
		if(patternCursor == patternOffset + 3) {
			pattern4.setSelect(true);
		}
		else {
			pattern4.setSelect(false);
			Pattern pattern = ChiptuneTracker.data.patterns.get(patternOffset + 3);
			System.out.println(patternOffset + 3);
			if(pattern.sample1 == null && pattern.sample2 == null && pattern.sample3 == null && pattern.sample4 == null) {
				pattern4.setMouseDefaultColor(Color.LIGHT_GRAY);
			}
			else {
				pattern4.setMouseDefaultColor(Color.WHITE);
			}
		}
	}
	
	public void changeSampleButtons() {
		Pattern pattern = ChiptuneTracker.data.patterns.get(patternCursor);
		
		if(pattern.sample1 != null) {
			sample1.setName(String.valueOf((char)254));
			sample1.setSelect(true);
			ChiptuneTracker.asciiPanel.add(sample1Down);
			ChiptuneTracker.asciiPanel.add(sample1Up);
		}
		else {
			sample1.setName(String.valueOf((char)253));
			sample1.setSelect(false);
			ChiptuneTracker.asciiPanel.remove(sample1Down);
			ChiptuneTracker.asciiPanel.remove(sample1Up);
			ChiptuneTracker.asciiPanel.clear(3, 4, 4, 1);
		}
		
		if(pattern.sample2 != null) {
			sample2.setName(String.valueOf((char)254));
			sample2.setSelect(true);
			ChiptuneTracker.asciiPanel.add(sample2Down);
			ChiptuneTracker.asciiPanel.add(sample2Up);
		}
		else {
			sample2.setName(String.valueOf((char)253));
			sample2.setSelect(false);
			ChiptuneTracker.asciiPanel.remove(sample2Down);
			ChiptuneTracker.asciiPanel.remove(sample2Up);
			ChiptuneTracker.asciiPanel.clear(10, 4, 4, 1);
		}
		
		if(pattern.sample3 != null) {
			sample3.setName(String.valueOf((char)254));
			sample3.setSelect(true);
			ChiptuneTracker.asciiPanel.add(sample3Down);
			ChiptuneTracker.asciiPanel.add(sample3Up);
		}
		else {
			sample3.setName(String.valueOf((char)253));
			sample3.setSelect(false);
			ChiptuneTracker.asciiPanel.remove(sample3Down);
			ChiptuneTracker.asciiPanel.remove(sample3Up);
			ChiptuneTracker.asciiPanel.clear(17, 4, 4, 1);
		}
		
		if(pattern.sample4 != null) {
			sample4.setName(String.valueOf((char)254));
			sample4.setSelect(true);
			ChiptuneTracker.asciiPanel.add(sample4Down);
			ChiptuneTracker.asciiPanel.add(sample4Up);
		}
		else {
			sample4.setName(String.valueOf((char)253));
			sample4.setSelect(false);
			ChiptuneTracker.asciiPanel.remove(sample4Down);
			ChiptuneTracker.asciiPanel.remove(sample4Up);
			ChiptuneTracker.asciiPanel.clear(24, 4, 4, 1);
		}
	}
	
	public void changeSample(int samplePattern, int sample) {
		if(sample >= 0 && sample < 100) {
			if(samplePattern == 1) {
				ChiptuneTracker.data.patterns.get(patternCursor).sample1 = sample;
			}
			if(samplePattern == 2) {
				ChiptuneTracker.data.patterns.get(patternCursor).sample2 = sample;
			}
			if(samplePattern == 3) {
				ChiptuneTracker.data.patterns.get(patternCursor).sample3 = sample;
			}
			if(samplePattern == 4) {
				ChiptuneTracker.data.patterns.get(patternCursor).sample4 = sample;
			}
		}
		
		if(ChiptuneTracker.data.samples.size() < sample + 1) {
			ChiptuneTracker.data.samples.add(new Sample());
		}
	}
	
	@Override
	public void update(double delta) {
		KeyEvent event = ChiptuneTracker.asciiTerminal.getEvent();
		if(event != null) {
			if(event.getKeyCode() == KeyEvent.VK_SPACE) {
				if(!ChiptuneTracker.chanels.isPlaySample() && !ChiptuneTracker.chanels.isPlayPattern()) {
					ChiptuneTracker.chanels.playPattern(patternCursor);
				}
				else {
					ChiptuneTracker.chanels.stopPattern();
				}
			}
			
			ChiptuneTracker.asciiTerminal.setEvent(null);
		}
	}
	
	@Override
	public void paint() {
		// Pattern
		ChiptuneTracker.asciiPanel.writeString(1, 2, "PATTERN", Color.gray);
		
		Pattern pattern = ChiptuneTracker.data.patterns.get(patternCursor);
		
		Sample sample1 = null;
		Sample sample2 = null;
		Sample sample3 = null;
		Sample sample4 = null;
		if(pattern.sample1 != null) {
			sample1 = ChiptuneTracker.data.samples.get(pattern.sample1);
			ChiptuneTracker.asciiPanel.writeString(4, 4, String.format("%02d", pattern.sample1), Color.WHITE);
		}
		
		if(pattern.sample2 != null) {
			sample2 = ChiptuneTracker.data.samples.get(pattern.sample2);
			ChiptuneTracker.asciiPanel.writeString(11, 4, String.format("%02d", pattern.sample2), Color.WHITE);
		}
		
		if(pattern.sample3 != null) {
			sample3 = ChiptuneTracker.data.samples.get(pattern.sample3);
			ChiptuneTracker.asciiPanel.writeString(18, 4, String.format("%02d", pattern.sample3), Color.WHITE);
		}
		
		if(pattern.sample4 != null) {
			sample4 = ChiptuneTracker.data.samples.get(pattern.sample4);
			ChiptuneTracker.asciiPanel.writeString(25, 4, String.format("%02d", pattern.sample4), Color.WHITE);
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
		super.quit();
		if(sample1.isSelect()) {
			ChiptuneTracker.asciiPanel.remove(sample1Down);
			ChiptuneTracker.asciiPanel.remove(sample1Up);
		}
		if(sample2.isSelect()) {
			ChiptuneTracker.asciiPanel.remove(sample2Down);
			ChiptuneTracker.asciiPanel.remove(sample2Up);
		}
		if(sample3.isSelect()) {
			ChiptuneTracker.asciiPanel.remove(sample3Down);
			ChiptuneTracker.asciiPanel.remove(sample3Up);
		}
		if(sample4.isSelect()) {
			ChiptuneTracker.asciiPanel.remove(sample4Down);
			ChiptuneTracker.asciiPanel.remove(sample4Up);
		}
	}
}
