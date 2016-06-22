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
		AsciiPanel asciiPanel = ChiptuneTracker.getInstance().getAsciiPanel();
		
		sample1 = new AsciiSelectableTerminalButton(asciiPanel, String.valueOf((char)253), 1, 4, Color.WHITE, Color.YELLOW, Color.GREEN);
		sample1.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sample1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(sample1.isSelect()) {
					ChiptuneTracker.getInstance().getData().patterns.get(patternCursor).sample1 = null;
				}
				else {
					ChiptuneTracker.getInstance().getData().patterns.get(patternCursor).sample1 = 0;
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
					ChiptuneTracker.getInstance().getData().patterns.get(patternCursor).sample2 = null;
				}
				else {
					ChiptuneTracker.getInstance().getData().patterns.get(patternCursor).sample2 = 0;
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
					ChiptuneTracker.getInstance().getData().patterns.get(patternCursor).sample3 = null;
				}
				else {
					ChiptuneTracker.getInstance().getData().patterns.get(patternCursor).sample3 = 0;
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
					ChiptuneTracker.getInstance().getData().patterns.get(patternCursor).sample4 = null;
				}
				else {
					ChiptuneTracker.getInstance().getData().patterns.get(patternCursor).sample4 = 0;
				}
				changeSampleButtons();
			}
		});
		terminalButtons.add(sample4);
	}
	
	public void createSampleDownButtons() {
		AsciiPanel asciiPanel = ChiptuneTracker.getInstance().getAsciiPanel();
		
		sample1Down = new AsciiTerminalButton(asciiPanel, "-", 3, 4, Color.MAGENTA, Color.ORANGE);
		sample1Down.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sample1Down.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Pattern pattern = ChiptuneTracker.getInstance().getData().patterns.get(patternCursor);
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
				Pattern pattern = ChiptuneTracker.getInstance().getData().patterns.get(patternCursor);
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
				Pattern pattern = ChiptuneTracker.getInstance().getData().patterns.get(patternCursor);
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
				Pattern pattern = ChiptuneTracker.getInstance().getData().patterns.get(patternCursor);
				if(pattern.sample4 != null) {
					changeSample(4, pattern.sample4 - 1);
				}
			}
		});
	}
	
	public void createSampleUpButtons() {
		AsciiPanel asciiPanel = ChiptuneTracker.getInstance().getAsciiPanel();
		
		sample1Up = new AsciiTerminalButton(asciiPanel, "+", 6, 4, Color.MAGENTA, Color.ORANGE);
		sample1Up.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sample1Up.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Pattern pattern = ChiptuneTracker.getInstance().getData().patterns.get(patternCursor);
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
				Pattern pattern = ChiptuneTracker.getInstance().getData().patterns.get(patternCursor);
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
				Pattern pattern = ChiptuneTracker.getInstance().getData().patterns.get(patternCursor);
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
				Pattern pattern = ChiptuneTracker.getInstance().getData().patterns.get(patternCursor);
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
			if(event.getKeyCode() == KeyEvent.VK_LEFT) {
				if(soundConfCursor > 0) {
					soundConfCursor--;
				}
				else if(sampleCursor > 0) {
					int nextSelectSample = sampleCursor;
					for(int i = sampleCursor - 1; i > 0; i--) {
						if(pattern.getList().get(i) != null) {
							nextSelectSample = i;
							break;
						}
					}
					
					if(nextSelectSample != sampleCursor) {
						sampleCursor = nextSelectSample;
						soundConfCursor = 4;
					}
				}
			}
			else if(event.getKeyCode() == KeyEvent.VK_RIGHT) {
				if(soundConfCursor < 4) {
					soundConfCursor++;
				}
				else if(sampleCursor < 3) {
					
					int nextSelectSample = sampleCursor;
					for(int i = sampleCursor + 1; i < 4; i++) {
						if(pattern.getList().get(i) != null) {
							nextSelectSample = i;
							break;
						}
					}
					if(nextSelectSample != sampleCursor) {
						sampleCursor = nextSelectSample;
						soundConfCursor = 0;
					}
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
			
			// Write note
			else if(event.getKeyCode() == KeyEvent.VK_A && pattern.getList().get(sampleCursor) != null) {
				setSound(Note.C);
			}
			else if(event.getKeyCode() == KeyEvent.VK_2 && pattern.getList().get(sampleCursor) != null) {
				setSound(Note.C_D);
			}
			else if(event.getKeyCode() == KeyEvent.VK_Z && pattern.getList().get(sampleCursor) != null) {
				setSound(Note.D);
			}
			else if(event.getKeyCode() == KeyEvent.VK_3 && pattern.getList().get(sampleCursor) != null) {
				setSound(Note.D_D);
			}
			else if(event.getKeyCode() == KeyEvent.VK_E && pattern.getList().get(sampleCursor) != null) {
				setSound(Note.E);
			}
			else if(event.getKeyCode() == KeyEvent.VK_R && pattern.getList().get(sampleCursor) != null) {
				setSound(Note.F);
			}
			else if(event.getKeyCode() == KeyEvent.VK_5 && pattern.getList().get(sampleCursor) != null) {
				setSound(Note.F_D);
			}
			else if(event.getKeyCode() == KeyEvent.VK_T && pattern.getList().get(sampleCursor) != null) {
				setSound(Note.G);
			}
			else if(event.getKeyCode() == KeyEvent.VK_6 && pattern.getList().get(sampleCursor) != null) {
				setSound(Note.G_D);
			}
			else if(event.getKeyCode() == KeyEvent.VK_Y && pattern.getList().get(sampleCursor) != null) {
				setSound(Note.A);
			}
			else if(event.getKeyCode() == KeyEvent.VK_7 && pattern.getList().get(sampleCursor) != null) {
				setSound(Note.A_D);
			}
			else if(event.getKeyCode() == KeyEvent.VK_U && pattern.getList().get(sampleCursor) != null) {
				setSound(Note.B);
			}
			else if(event.getKeyCode() >= KeyEvent.VK_NUMPAD0 && event.getKeyCode() <= KeyEvent.VK_NUMPAD9 && pattern.getList().get(sampleCursor) != null) {
				// Octave
				if(soundConfCursor == 1) {
					setOctave(event.getKeyCode() - 96);
				}
				// Instrument
				else if(soundConfCursor == 2) {
					setInstrument(event.getKeyCode() - 96);
				}
				// Volume
				else if(soundConfCursor == 3) {
					setVolume(event.getKeyCode() - 96);
				}
			}
			else if(event.getKeyCode() == KeyEvent.VK_DELETE && pattern.getList().get(sampleCursor) != null) {
				deleteSound();
			}
			
			// Play pattern
			else if(event.getKeyCode() == KeyEvent.VK_SPACE) {
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
	
	/*
	 * -----------------
	 * Key actions
	 * -----------------
	 */
	
	private void setSound(Note note) {
		Sample sample = ChiptuneTracker.getInstance().getData().samples.get(sampleCursor);
		
		if(ChiptuneTracker.getInstance().getSampleView().getVolumeCursor() != 0) {
			Sound sound = sample.sounds[soundCursor];
			if(sound == null) {
				sound = new Sound();
				sample.sounds[soundCursor] = sound;
			}
			
			sound.note = note;
			sound.octave = ChiptuneTracker.getInstance().getSampleView().getOctaveCursor();
			sound.instrument = ChiptuneTracker.getInstance().getSampleView().getInstrumentCursor();
			sound.volume = ChiptuneTracker.getInstance().getSampleView().getVolumeCursor();
			
			ChiptuneTracker.getInstance().getChanels().playSound(sound);
		}
		else {
			sample.sounds[soundCursor] = null;
		}
		
		soundCursor++;
		if(soundCursor > Sample.SIZE - 1) {
			soundCursor = 0;
		}
	}
	
	private void setOctave(int octave) {
		if(octave >= 1 && octave <= 4) {
			Sample sample = ChiptuneTracker.getInstance().getData().samples.get(sampleCursor);
			Sound sound = sample.sounds[soundCursor];
			if(sound != null) {
				sound.octave = octave;
				
				soundCursor++;
				if(soundCursor > Sample.SIZE - 1) {
					soundCursor = 0;
				}
				
				ChiptuneTracker.getInstance().getChanels().playSound(sound);
			}
		}
	}
	
	private void setVolume(int volume) {
		if(volume >= 0 && volume <= 7) {
			Sample sample = ChiptuneTracker.getInstance().getData().samples.get(sampleCursor);
			Sound sound = sample.sounds[soundCursor];
			if(sound != null) {
				if(volume == 0) {
					sample.sounds[soundCursor] = null;
				}
				else {
					sound.volume = volume;
				}
				
				soundCursor++;
				if(soundCursor > Sample.SIZE - 1) {
					soundCursor = 0;
				}
				ChiptuneTracker.getInstance().getChanels().playSound(sound);
			}
		}
	}
	
	private void setInstrument(int instrument) {
		if(instrument >= 0 && instrument <= 7) {
			Sample sample = ChiptuneTracker.getInstance().getData().samples.get(sampleCursor);
			Sound sound = sample.sounds[soundCursor];
			if(sound != null) {
				sound.instrument = instrument;
				
				soundCursor++;
				if(soundCursor > Sample.SIZE - 1) {
					soundCursor = 0;
				}
				ChiptuneTracker.getInstance().getChanels().playSound(sound);
			}
		}
	}
	
	public void deleteSound() {
		Sample sample = ChiptuneTracker.getInstance().getData().samples.get(sampleCursor);
		sample.sounds[soundCursor] = null;
		
		soundCursor++;
		if(soundCursor > Sample.SIZE - 1) {
			soundCursor = 0;
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
		
		paintSample(0, sample1, 1, 5, asciiPanel);
		paintSample(1, sample2, 8, 5, asciiPanel);
		paintSample(2, sample3, 15, 5, asciiPanel);
		paintSample(3, sample4, 22, 5, asciiPanel);
	}
	
	public void paintSample(int index, Sample sample, int offsetX, int offsetY, AsciiPanel asciiPanel) {
		int soundOffset = 0;
		int cursorPosition = -1;
		
		if(ChiptuneTracker.getInstance().getChanels().isPlayPattern() && sample != null) {
			int sampleCursorPosition = ChiptuneTracker.getInstance().getChanels().getSampleCursor(index);
			
			soundOffset = Math.max(0, Math.min(sampleCursorPosition - 5, 31 - 10));
			if(sampleCursorPosition < 5) {
				cursorPosition = sampleCursorPosition;
			}
			else if(sampleCursorPosition >= 5 && sampleCursorPosition < 26) {
				cursorPosition = 5;
			}
			else {
				cursorPosition = sampleCursorPosition - 21;
			}
		}
		else {
			soundOffset = Math.max(0, Math.min(soundCursor - 5, 31 - 10));
		}
		
		
		for(int i = 0; i < 11; i++) {
			int x = offsetX;
			int y = offsetY + i;
			
			Sound sound = null;
			
			if(sample != null) {
				sound = sample.sounds[i + soundOffset];
			}
			
			if(!ChiptuneTracker.getInstance().getChanels().isPlayPattern() && index == sampleCursor && i + soundOffset == soundCursor) {
				if(sound != null) {
					printSound(asciiPanel, x, y,
							sound.note.str, 				Color.WHITE, 	soundConfCursor == 0 ? Color.YELLOW : Color.BLUE,
							sound.octave.toString(), 		Color.GREEN, 	soundConfCursor == 1 ? Color.YELLOW : Color.BLUE,
							sound.instrument.toString(), 	Color.MAGENTA, 	soundConfCursor == 2 ? Color.YELLOW : Color.BLUE,
							sound.volume.toString(), 		Color.CYAN, 	soundConfCursor == 3 ? Color.YELLOW : Color.BLUE,
							DOT, 							Color.GRAY, 	soundConfCursor == 4 ? Color.YELLOW : Color.BLUE);
				}
				else {
					printSound(asciiPanel, x, y,
							DOT + DOT, 	Color.GRAY, 	soundConfCursor == 0 ? Color.YELLOW : Color.BLUE,
							DOT, 		Color.GRAY, 	soundConfCursor == 1 ? Color.YELLOW : Color.BLUE,
							DOT, 		Color.GRAY, 	soundConfCursor == 2 ? Color.YELLOW : Color.BLUE,
							DOT, 		Color.GRAY, 	soundConfCursor == 3 ? Color.YELLOW : Color.BLUE,
							DOT, 		Color.GRAY, 	soundConfCursor == 4 ? Color.YELLOW : Color.BLUE);
				}
			}
			else {
				Color backgroundColor = Color.BLACK;
				
				int soundPlayCursor = ChiptuneTracker.getInstance().getChanels().getSampleCursor(index);
				if(ChiptuneTracker.getInstance().getChanels().isPlayPattern() && i == cursorPosition) {
					backgroundColor = Color.YELLOW;
				}
				
				if(sound != null) {
					printSound(asciiPanel, x, y,
							sound.note.str, 				Color.WHITE, 	backgroundColor,
							sound.octave.toString(), 		Color.GREEN, 	backgroundColor,
							sound.instrument.toString(), 	Color.MAGENTA, 	backgroundColor,
							sound.volume.toString(), 		Color.CYAN, 	backgroundColor,
							DOT, 							Color.GRAY, 	backgroundColor);
				}
				else {
					for(int j = x; j < x + 6; j++) {
						asciiPanel.write(j, y, (char)239, Color.GRAY, backgroundColor);
					}
				}
			}
		}
	}
		
	private void printSound(AsciiPanel asciiPanel, int x, int y,
			String note, Color noteColor, Color noteBackColor,
			String octave, Color octaveColor, Color octaveBackColor,
			String instrument, Color instrumentColor, Color instrumentBackColor,
			String volume, Color volumeColor, Color volumeBackColor,
			String effect, Color effectColor, Color effectBackColor) {
		asciiPanel.writeString(x, y, note, noteColor, noteBackColor);
		asciiPanel.writeString(x+2, y, octave, octaveColor, octaveBackColor);
		asciiPanel.writeString(x+3, y, instrument, instrumentColor, instrumentBackColor);
		asciiPanel.writeString(x+4, y, volume, volumeColor, volumeBackColor);
		asciiPanel.writeString(x+5, y, effect, effectColor, effectBackColor);
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
