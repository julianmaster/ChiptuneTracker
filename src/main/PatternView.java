package main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ui.AsciiPanel;
import ui.AsciiSelectableTerminalButton;
import ui.AsciiTerminalButton;
import ui.CustomAsciiTerminal;

public class PatternView extends View {

	private int sampleCursor = 0;
	private int soundCursor = 0;
	private int soundConfCursor = 0;
	
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
		AsciiPanel asciiPanel = ChiptuneTracker.getInstance().getAsciiPanel();
		
		// Down button
		AsciiTerminalButton buttonDownSample = new AsciiTerminalButton(asciiPanel, String.valueOf((char)17), 9, 2, Color.MAGENTA, Color.ORANGE);
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
		AsciiTerminalButton buttonUpSample = new AsciiTerminalButton(asciiPanel, String.valueOf((char)16), ChiptuneTracker.WINDOW_WIDTH - 6, 2, Color.MAGENTA, Color.ORANGE);
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
		pattern1 = new AsciiSelectableTerminalButton(asciiPanel, String.format("%02d", patternCursor), 11, 2, Color.WHITE, Color.ORANGE, Color.ORANGE);
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
		pattern2 = new AsciiSelectableTerminalButton(asciiPanel, String.format("%02d", patternCursor + 1), 14, 2, Color.WHITE, Color.ORANGE, Color.ORANGE);
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
		pattern3 = new AsciiSelectableTerminalButton(asciiPanel, String.format("%02d", patternCursor + 2), 17, 2, Color.WHITE, Color.ORANGE, Color.ORANGE);
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
		pattern4 = new AsciiSelectableTerminalButton(asciiPanel, String.format("%02d", patternCursor + 3), 20, 2, Color.WHITE, Color.ORANGE, Color.ORANGE);
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
		final Data data = ChiptuneTracker.getInstance().getData();
		AsciiPanel asciiPanel = ChiptuneTracker.getInstance().getAsciiPanel();
		
		sample1 = new AsciiSelectableTerminalButton(asciiPanel, String.valueOf((char)253), 1, 4, Color.WHITE, Color.YELLOW, Color.GREEN);
		sample1.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sample1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(sample1.isSelect()) {
					data.patterns.get(patternCursor).sample1 = null;
				}
				else {
					data.patterns.get(patternCursor).sample1 = 0;
				}
				changeSampleButtons();
			}
		});
		terminalButtons.add(sample1);
		
		sample2 = new AsciiSelectableTerminalButton(asciiPanel, String.valueOf((char)253), 8, 4, Color.WHITE, Color.YELLOW, Color.GREEN);
		sample2.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sample2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(sample2.isSelect()) {
					data.patterns.get(patternCursor).sample2 = null;
				}
				else {
					data.patterns.get(patternCursor).sample2 = 0;
				}
				changeSampleButtons();
			}
		});
		terminalButtons.add(sample2);
		
		sample3 = new AsciiSelectableTerminalButton(asciiPanel, String.valueOf((char)253), 15, 4, Color.WHITE, Color.YELLOW, Color.GREEN);
		sample3.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sample3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(sample3.isSelect()) {
					data.patterns.get(patternCursor).sample3 = null;
				}
				else {
					data.patterns.get(patternCursor).sample3 = 0;
				}
				changeSampleButtons();
			}
		});
		terminalButtons.add(sample3);
		
		sample4 = new AsciiSelectableTerminalButton(asciiPanel, String.valueOf((char)253), 22, 4, Color.WHITE, Color.YELLOW, Color.GREEN);
		sample4.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sample4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(sample4.isSelect()) {
					data.patterns.get(patternCursor).sample4 = null;
				}
				else {
					data.patterns.get(patternCursor).sample4 = 0;
				}
				changeSampleButtons();
			}
		});
		terminalButtons.add(sample4);
	}
	
	public void createSampleDownButtons() {
		final Data data = ChiptuneTracker.getInstance().getData();
		AsciiPanel asciiPanel = ChiptuneTracker.getInstance().getAsciiPanel();
		
		sample1Down = new AsciiTerminalButton(asciiPanel, "-", 3, 4, Color.MAGENTA, Color.ORANGE);
		sample1Down.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sample1Down.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Pattern pattern = data.patterns.get(patternCursor);
				if(pattern.sample1 != null) {
					changeSample(1, pattern.sample1 - 1);
				}
			}
		});
		
		sample2Down = new AsciiTerminalButton(asciiPanel, "-", 10, 4, Color.MAGENTA, Color.ORANGE);
		sample2Down.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sample2Down.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Pattern pattern = data.patterns.get(patternCursor);
				if(pattern.sample2 != null) {
					changeSample(2, pattern.sample2 - 1);
				}
			}
		});
		
		sample3Down = new AsciiTerminalButton(asciiPanel, "-", 17, 4, Color.MAGENTA, Color.ORANGE);
		sample3Down.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sample3Down.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Pattern pattern = data.patterns.get(patternCursor);
				if(pattern.sample3 != null) {
					changeSample(3, pattern.sample3 - 1);
				}
			}
		});
		
		sample4Down = new AsciiTerminalButton(asciiPanel, "-", 24, 4, Color.MAGENTA, Color.ORANGE);
		sample4Down.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sample4Down.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Pattern pattern = data.patterns.get(patternCursor);
				if(pattern.sample4 != null) {
					changeSample(4, pattern.sample4 - 1);
				}
			}
		});
	}
	
	public void createSampleUpButtons() {
		final Data data = ChiptuneTracker.getInstance().getData();
		AsciiPanel asciiPanel = ChiptuneTracker.getInstance().getAsciiPanel();
		
		sample1Up = new AsciiTerminalButton(asciiPanel, "+", 6, 4, Color.MAGENTA, Color.ORANGE);
		sample1Up.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sample1Up.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Pattern pattern = data.patterns.get(patternCursor);
				if(pattern.sample1 != null) {
					changeSample(1, pattern.sample1 + 1);
				}
			}
		});
		
		sample2Up = new AsciiTerminalButton(asciiPanel, "+", 13, 4, Color.MAGENTA, Color.ORANGE);
		sample2Up.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sample2Up.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Pattern pattern = data.patterns.get(patternCursor);
				if(pattern.sample2 != null) {
					changeSample(2, pattern.sample2 + 1);
				}
			}
		});
		
		sample3Up = new AsciiTerminalButton(asciiPanel, "+", 20, 4, Color.MAGENTA, Color.ORANGE);
		sample3Up.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sample3Up.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Pattern pattern = data.patterns.get(patternCursor);
				if(pattern.sample3 != null) {
					changeSample(3, pattern.sample3 + 1);
				}
			}
		});
		
		sample4Up = new AsciiTerminalButton(asciiPanel, "+", 27, 4, Color.MAGENTA, Color.ORANGE);
		sample4Up.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sample4Up.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Pattern pattern = data.patterns.get(patternCursor);
				if(pattern.sample4 != null) {
					changeSample(4, pattern.sample4 + 1);
				}
			}
		});
	}
	
	@Override
	public void init() {
		if(ChiptuneTracker.getInstance().isInitPatternView()) {
			ChiptuneTracker.getInstance().setInitPatternView(false);
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
		AsciiPanel asciiPanel = ChiptuneTracker.getInstance().getAsciiPanel();
		if(sample1.isSelect()) {
			asciiPanel.add(sample1Down);
			asciiPanel.add(sample1Up);
		}
		if(sample2.isSelect()) {
			asciiPanel.add(sample2Down);
			asciiPanel.add(sample2Up);
		}
		if(sample3.isSelect()) {
			asciiPanel.add(sample3Down);
			asciiPanel.add(sample3Up);
		}
		if(sample4.isSelect()) {
			asciiPanel.add(sample4Down);
			asciiPanel.add(sample4Up);
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
			if(ChiptuneTracker.getInstance().getData().patterns.size() < patternOffset + i) {
				ChiptuneTracker.getInstance().getData().patterns.add(new Pattern());
			}
		}
	}
	
	public void changePatternButtons() {
		Data data = ChiptuneTracker.getInstance().getData();
		pattern1.setName(String.format("%02d", patternOffset));
		pattern2.setName(String.format("%02d", patternOffset + 1));
		pattern3.setName(String.format("%02d", patternOffset + 2));
		pattern4.setName(String.format("%02d", patternOffset + 3));
		
		if(patternCursor == patternOffset) {
			pattern1.setSelect(true);
		}
		else {
			pattern1.setSelect(false);
			Pattern pattern = data.patterns.get(patternOffset);
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
			Pattern pattern = data.patterns.get(patternOffset + 1);
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
			Pattern pattern = data.patterns.get(patternOffset + 2);
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
			Pattern pattern = data.patterns.get(patternOffset + 3);
			if(pattern.sample1 == null && pattern.sample2 == null && pattern.sample3 == null && pattern.sample4 == null) {
				pattern4.setMouseDefaultColor(Color.LIGHT_GRAY);
			}
			else {
				pattern4.setMouseDefaultColor(Color.WHITE);
			}
		}
	}
	
	public void changeSampleButtons() {
		Pattern pattern = ChiptuneTracker.getInstance().getData().patterns.get(patternCursor);
		AsciiPanel asciiPanel = ChiptuneTracker.getInstance().getAsciiPanel();
		
		if(pattern.sample1 != null) {
			sample1.setName(String.valueOf((char)254));
			sample1.setSelect(true);
			asciiPanel.add(sample1Down);
			asciiPanel.add(sample1Up);
		}
		else {
			sample1.setName(String.valueOf((char)253));
			sample1.setSelect(false);
			asciiPanel.remove(sample1Down);
			asciiPanel.remove(sample1Up);
			asciiPanel.clear(3, 4, 4, 1);
		}
		
		if(pattern.sample2 != null) {
			sample2.setName(String.valueOf((char)254));
			sample2.setSelect(true);
			asciiPanel.add(sample2Down);
			asciiPanel.add(sample2Up);
		}
		else {
			sample2.setName(String.valueOf((char)253));
			sample2.setSelect(false);
			asciiPanel.remove(sample2Down);
			asciiPanel.remove(sample2Up);
			asciiPanel.clear(10, 4, 4, 1);
		}
		
		if(pattern.sample3 != null) {
			sample3.setName(String.valueOf((char)254));
			sample3.setSelect(true);
			asciiPanel.add(sample3Down);
			asciiPanel.add(sample3Up);
		}
		else {
			sample3.setName(String.valueOf((char)253));
			sample3.setSelect(false);
			asciiPanel.remove(sample3Down);
			asciiPanel.remove(sample3Up);
			asciiPanel.clear(17, 4, 4, 1);
		}
		
		if(pattern.sample4 != null) {
			sample4.setName(String.valueOf((char)254));
			sample4.setSelect(true);
			asciiPanel.add(sample4Down);
			asciiPanel.add(sample4Up);
		}
		else {
			sample4.setName(String.valueOf((char)253));
			sample4.setSelect(false);
			asciiPanel.remove(sample4Down);
			asciiPanel.remove(sample4Up);
			asciiPanel.clear(24, 4, 4, 1);
		}
	}
	
	public void changeSample(int samplePattern, int sample) {
		Data data = ChiptuneTracker.getInstance().getData();
		if(sample >= 0 && sample < 100) {
			if(samplePattern == 1) {
				data.patterns.get(patternCursor).sample1 = sample;
			}
			if(samplePattern == 2) {
				data.patterns.get(patternCursor).sample2 = sample;
			}
			if(samplePattern == 3) {
				data.patterns.get(patternCursor).sample3 = sample;
			}
			if(samplePattern == 4) {
				data.patterns.get(patternCursor).sample4 = sample;
			}
		}
		
		if(data.samples.size() < sample + 1) {
			data.samples.add(new Sample());
		}
	}
	
	@Override
	public void update(double delta) {
		Chanels chanels = ChiptuneTracker.getInstance().getChanels();
		CustomAsciiTerminal asciiTerminal = ChiptuneTracker.getInstance().getAsciiTerminal();
		Pattern pattern = ChiptuneTracker.getInstance().getData().patterns.get(patternCursor);
		
		KeyEvent event = asciiTerminal.getEvent();
		if(event != null) {
			//TODO
			
			if(event.getKeyCode() == KeyEvent.VK_LEFT) {
				if(soundConfCursor > 0) {
					soundConfCursor--;
				}
				else if(sampleCursor > 0) {
					soundConfCursor = 4;
					int nextSelectSample = 0;
					for(int i = 0; i <= sampleCursor; i++) {
						if(pattern.getList().get(i) != null) {
							nextSelectSample = i;
						}
					}
					sampleCursor = nextSelectSample;
				}
			}
			else if(event.getKeyCode() == KeyEvent.VK_RIGHT) {
				if(soundConfCursor < 4) {
					soundConfCursor++;
				}
				else if(soundCursor < 3) {
					soundConfCursor = 0;
					int nextSelectSample = 3;
					for(int i = 3; i >= sampleCursor; i--) {
						if(pattern.getList().get(i) != null) {
							nextSelectSample = i;
						}
					}
					sampleCursor = nextSelectSample;
				}
			}
			else if(event.getKeyCode() == KeyEvent.VK_UP) {
				soundCursor--;
				if(soundCursor < 0) {
					soundCursor = Sample.SIZE - 1;
				}
			}
			else if(event.getKeyCode() == KeyEvent.VK_DOWN) {
				soundCursor++;
				if(soundCursor > Sample.SIZE - 1) {
					soundCursor = 0;
				}
			}
			
			if(event.getKeyCode() == KeyEvent.VK_SPACE) {
				if(!chanels.isPlaySample() && !chanels.isPlayPattern()) {
					chanels.playPattern(patternCursor);
				}
				else {
					chanels.stopPattern();
				}
			}
			
			asciiTerminal.setEvent(null);
		}
		
		if(chanels.isPlayPattern() && patternCursor != chanels.getPatternCursor()) {
			patternCursor = chanels.getPatternCursor();
			changePatternButtons();
			changeSampleButtons();
		}
	}
	
	@Override
	public void paint() {
		Data data = ChiptuneTracker.getInstance().getData();
		Chanels chanels = ChiptuneTracker.getInstance().getChanels();
		AsciiPanel asciiPanel = ChiptuneTracker.getInstance().getAsciiPanel();
		// Pattern
		asciiPanel.writeString(1, 2, "PATTERN", Color.gray);
		
		Pattern pattern =data.patterns.get(patternCursor);
		
		Sample sample1 = null;
		Sample sample2 = null;
		Sample sample3 = null;
		Sample sample4 = null;
		if(pattern.sample1 != null) {
			sample1 = data.samples.get(pattern.sample1);
			asciiPanel.writeString(4, 4, String.format("%02d", pattern.sample1), Color.WHITE);
		}
		
		if(pattern.sample2 != null) {
			sample2 = data.samples.get(pattern.sample2);
			asciiPanel.writeString(11, 4, String.format("%02d", pattern.sample2), Color.WHITE);
		}
		
		if(pattern.sample3 != null) {
			sample3 = data.samples.get(pattern.sample3);
			asciiPanel.writeString(18, 4, String.format("%02d", pattern.sample3), Color.WHITE);
		}
		
		if(pattern.sample4 != null) {
			sample4 = data.samples.get(pattern.sample4);
			asciiPanel.writeString(25, 4, String.format("%02d", pattern.sample4), Color.WHITE);
		}
		
		boolean isPlayPattern = chanels.isPlayPattern();
		paintSample(sample1, 1, 5, isPlayPattern, chanels.getSampleCursor(0), asciiPanel);
		paintSample(sample2, 8, 5, isPlayPattern, chanels.getSampleCursor(1), asciiPanel);
		paintSample(sample3, 15, 5, isPlayPattern, chanels.getSampleCursor(2), asciiPanel);
		paintSample(sample4, 22, 5, isPlayPattern, chanels.getSampleCursor(3), asciiPanel);
	}
	
	public void paintSample(Sample sample, int offsetX, int offsetY, boolean isPlayPattern, int cursorPosition, AsciiPanel asciiPanel) {
		int relativeCursorPosition = -1;
		int soundOffset = 0;
		if(isPlayPattern && sample != null) {
			if(cursorPosition <= 5) {
				relativeCursorPosition = cursorPosition;
			}
			else if(cursorPosition > 5 && cursorPosition <= 26) {
				relativeCursorPosition = 5;
			}
			else {
				relativeCursorPosition = cursorPosition - 26 + 5;
			}
			
			soundOffset = Math.max(0, Math.min(cursorPosition - 5, 31 - 10));
		}
		else {
			if(soundCursor <= 5) {
				relativeCursorPosition = sampleCursor;
			}
			else if(soundCursor > 5 && soundCursor <= 26) {
				relativeCursorPosition = 5;
			}
			else {
				relativeCursorPosition = soundCursor - 26 + 5;
			}
			
			soundOffset = Math.max(0, Math.min(soundCursor - 5, 31 - 10));
		}
		
		for(int i = 0; i < 11; i++) {
			int x = offsetX;
			int y = offsetY + i;
			
			Sound sound = null;
			
			if(sample != null) {
				sound = sample.sounds[i + soundOffset];
			}
			
			// Default color
			Color backgroundColor = Color.BLACK;
			
			// if this sound is play change the background color
			if(isPlayPattern && i == relativeCursorPosition) {
				backgroundColor = Color.YELLOW;
			}
			
			if(sound != null) {
				asciiPanel.writeString(x, y, sound.note.str, Color.WHITE, backgroundColor);
				asciiPanel.write(x+2, y, Character.forDigit(sound.octave, 10), Color.GREEN, backgroundColor);
				asciiPanel.write(x+3, y,  Character.forDigit(sound.instrument, 10), Color.MAGENTA, backgroundColor);
				asciiPanel.write(x+4, y,  Character.forDigit(sound.volume, 10), Color.CYAN, backgroundColor);
				asciiPanel.write(x+5, y, (char)239, Color.GRAY, backgroundColor);
			}
			else {
				for(int j = x; j < x + 6; j++) {
					asciiPanel.write(j, y, (char)239, Color.GRAY, backgroundColor);
				}
			}
		}
	}

	@Override
	public void quit() {
		super.quit();
		AsciiPanel asciiPanel = ChiptuneTracker.getInstance().getAsciiPanel();
		if(sample1.isSelect()) {
			asciiPanel.remove(sample1Down);
			asciiPanel.remove(sample1Up);
		}
		if(sample2.isSelect()) {
			asciiPanel.remove(sample2Down);
			asciiPanel.remove(sample2Up);
		}
		if(sample3.isSelect()) {
			asciiPanel.remove(sample3Down);
			asciiPanel.remove(sample3Up);
		}
		if(sample4.isSelect()) {
			asciiPanel.remove(sample4Down);
			asciiPanel.remove(sample4Up);
		}
	}
}
