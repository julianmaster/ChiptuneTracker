package main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import ui.AsciiPanel;
import ui.AsciiSelectableTerminalButton;
import ui.AsciiTerminalButton;
import ui.CustomAsciiTerminal;

public class SampleView extends View {
	
	private int sampleCursor = 0;
	private int soundCursor = 0;
	private int soundConfCursor = 0;
	
	// Loop buttons
	AsciiTerminalButton buttonLoopStartSample;
	AsciiTerminalButton buttonLoopStopSample;
	
	// Octave cursor
	private int octaveCursor = 2;
	// Octave button list
	private List<AsciiSelectableTerminalButton> octaveButtons = new ArrayList<>();
	// Current octave button active
	private AsciiSelectableTerminalButton currentOctaveButton = null;
	
	// Volume cursor
	private int volumeCursor = 5;
	// Volume button list
	private List<AsciiSelectableTerminalButton> volumeButtons = new ArrayList<>();
	// Current volume button active
	private AsciiSelectableTerminalButton currentVolumeButton = null;
	
	// Oscillator cursor
	private int instrumentCursor = 0;
	// Oscillator button list
	private List<AsciiSelectableTerminalButton> instrumentButtons = new ArrayList<>();
	// Current oscillator button active
	private AsciiSelectableTerminalButton currentInstrumentButton = null;
	
	public SampleView(ChiptuneTracker chiptuneTracker) {
		super(chiptuneTracker);
		createSampleButtons();
		createSpeedButtons();
		createOctaveButtons();
		createVolumeButtons();
		createOscillatorButtons();
		createLoopButtons();
		changeSample(0);
	}
	
	public void createSampleButtons() {
		AsciiPanel asciiPanel = ChiptuneTracker.getInstance().getAsciiPanel();
		
		AsciiTerminalButton buttonDownSample = new AsciiTerminalButton(asciiPanel, String.valueOf((char)17), 1, 2, Color.MAGENTA, Color.ORANGE);
		buttonDownSample.setCursor(new Cursor(Cursor.HAND_CURSOR));
		buttonDownSample.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				changeSample(sampleCursor - 1);
			}
		});
		terminalButtons.add(buttonDownSample);
		
		AsciiTerminalButton buttonUpSample = new AsciiTerminalButton(asciiPanel, String.valueOf((char)16), 6, 2, Color.MAGENTA, Color.ORANGE);
		buttonUpSample.setCursor(new Cursor(Cursor.HAND_CURSOR));
		buttonUpSample.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				changeSample(sampleCursor + 1);
			}
		});
		terminalButtons.add(buttonUpSample);
	}
	
	public void createSpeedButtons() {
		AsciiPanel asciiPanel = ChiptuneTracker.getInstance().getAsciiPanel();
		
		AsciiTerminalButton reduceButton = new AsciiTerminalButton(asciiPanel, "-", 12, 2, Color.MAGENTA, Color.ORANGE);
		reduceButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		reduceButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Sample sample = ChiptuneTracker.getInstance().getData().samples.get(sampleCursor);
				if(sample.speed > 1) {
					sample.speed--;
				}
			}
		});
		terminalButtons.add(reduceButton);
		
		AsciiTerminalButton addButton = new AsciiTerminalButton(asciiPanel, "+", 15, 2, Color.MAGENTA, Color.ORANGE);
		addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		addButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Sample sample = ChiptuneTracker.getInstance().getData().samples.get(sampleCursor);
				if(sample.speed < 32) {
					sample.speed++;
				}
			}
		});
		terminalButtons.add(addButton);
	}
	
	public void createLoopButtons() {
		AsciiPanel asciiPanel = ChiptuneTracker.getInstance().getAsciiPanel();
		
		Sample sample = null;
		if(!ChiptuneTracker.getInstance().getData().samples.isEmpty()) {
			sample = ChiptuneTracker.getInstance().getData().samples.get(sampleCursor);
		}
		
		buttonLoopStartSample = new AsciiTerminalButton(asciiPanel, String.format("%02d", sample != null ? sample.loopStart : 0), ChiptuneTracker.WINDOW_WIDTH - 6, 2, Color.WHITE, Color.ORANGE, Color.BLACK);
		buttonLoopStartSample.setCursor(new Cursor(Cursor.HAND_CURSOR));
		buttonLoopStartSample.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Sample sample = ChiptuneTracker.getInstance().getData().samples.get(sampleCursor);
				if(e.getButton() == MouseEvent.BUTTON1) {
					if(sample.loopStart < sample.loopStop) {
						sample.loopStart++;
					}
				}
				if(e.getButton() == MouseEvent.BUTTON3) {
					if(sample.loopStart > 0) {
						sample.loopStart--;
					}
				}
				buttonLoopStartSample.setName(String.format("%02d", sample.loopStart));
			}
		});
		terminalButtons.add(buttonLoopStartSample);
		
		buttonLoopStopSample = new AsciiTerminalButton(asciiPanel, String.format("%02d", sample != null ? sample.loopStop : 0), ChiptuneTracker.WINDOW_WIDTH - 3, 2, Color.WHITE, Color.ORANGE, Color.BLACK);
		buttonLoopStopSample.setCursor(new Cursor(Cursor.HAND_CURSOR));
		buttonLoopStopSample.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Sample sample = ChiptuneTracker.getInstance().getData().samples.get(sampleCursor);
				if(e.getButton() == MouseEvent.BUTTON1) {
					if(sample.loopStop < Sample.SIZE) {
						sample.loopStop++;
					}
				}
				if(e.getButton() == MouseEvent.BUTTON3) {
					if(sample.loopStop > sample.loopStart) {
						sample.loopStop--;
					}
				}
				buttonLoopStopSample.setName(String.format("%02d", sample.loopStop));
			}
		});
		terminalButtons.add(buttonLoopStopSample);
		
	}
	
	public void createOctaveButtons() {
		AsciiPanel asciiPanel = ChiptuneTracker.getInstance().getAsciiPanel();
		
		for(int i = 1; i <= 4; i++) {
			AsciiSelectableTerminalButton button = new AsciiSelectableTerminalButton(asciiPanel, String.valueOf(i), 4 + i, 4, Color.LIGHT_GRAY, Color.WHITE, Color.GREEN);
			button.setCursor(new Cursor(Cursor.HAND_CURSOR));
			button.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					currentOctaveButton.setSelect(false);
					AsciiSelectableTerminalButton button = (AsciiSelectableTerminalButton)e.getSource();
					button.setSelect(true);
					currentOctaveButton = button;
					changeOctave(button.getName());
				}
			});
			
			octaveButtons.add(button);
			if(i == 2) {
				currentOctaveButton = button;
				currentOctaveButton.setSelect(true);
			}
		}
		terminalButtons.addAll(octaveButtons);
	}
	
	public void createVolumeButtons() {
		AsciiPanel asciiPanel = ChiptuneTracker.getInstance().getAsciiPanel();
		
		for(int i = 0; i <= 7; i++) {
			AsciiSelectableTerminalButton button = new AsciiSelectableTerminalButton(asciiPanel, String.valueOf(i), 5 + i, 6, Color.LIGHT_GRAY, Color.WHITE, Color.CYAN);
			button.setCursor(new Cursor(Cursor.HAND_CURSOR));
			button.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					currentVolumeButton.setSelect(false);
					AsciiSelectableTerminalButton button = (AsciiSelectableTerminalButton)e.getSource();
					button.setSelect(true);
					currentVolumeButton = button;
					changeVolume(button.getName());
				}
			});
			
			volumeButtons.add(button);
			if(i == 5) {
				currentVolumeButton = button;
				currentVolumeButton.setSelect(true);
			}
		}
		terminalButtons.addAll(volumeButtons);
	}
	
	public void createOscillatorButtons() {
		AsciiPanel asciiPanel = ChiptuneTracker.getInstance().getAsciiPanel();
		
		for(int i = 0; i <= 7; i++) {
			AsciiSelectableTerminalButton button = new AsciiSelectableTerminalButton(asciiPanel, String.valueOf((char)(224 + i)), ChiptuneTracker.WINDOW_WIDTH - 8 - 1 + i, 4, Color.LIGHT_GRAY, Color.WHITE, Color.MAGENTA);
			button.setCursor(new Cursor(Cursor.HAND_CURSOR));
			button.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					currentInstrumentButton.setSelect(false);
					AsciiSelectableTerminalButton button = (AsciiSelectableTerminalButton)e.getSource();
					button.setSelect(true);
					currentInstrumentButton = button;
					changeInstrument(button.getName());
				}
			});
			
			instrumentButtons.add(button);
			if(i == 0) {
				currentInstrumentButton = button;
				currentInstrumentButton.setSelect(true);
			}
		}
		terminalButtons.addAll(instrumentButtons);
	}
	
	@Override
	public void init() {
		if(ChiptuneTracker.getInstance().isInitSampleView()) {
			ChiptuneTracker.getInstance().setInitSampleView(false);
			changeSample(0);
			soundCursor = 0;
			soundConfCursor = 0;
		}
		
		buttonMenuView.setSelect(false);
		buttonSampleView.setSelect(true);
		buttonPatternView.setSelect(false);
		
		Sample sample = ChiptuneTracker.getInstance().getData().samples.get(sampleCursor);
		buttonLoopStartSample.setName(String.format("%02d", sample.loopStart));
		buttonLoopStopSample.setName(String.format("%02d", sample.loopStop));
		
		super.init();
	}

	@Override
	public void update(double delta) {
		Chanels chanels = ChiptuneTracker.getInstance().getChanels();
		CustomAsciiTerminal asciiTerminal = ChiptuneTracker.getInstance().getAsciiTerminal();
		
		KeyEvent event = asciiTerminal.getEvent();
		if(event != null) {
			// Change sample
			if(event.getKeyCode() == KeyEvent.VK_ADD) {
				changeSample(sampleCursor + 1);
			}
			else if(event.getKeyCode() == KeyEvent.VK_SUBTRACT) {
				changeSample(sampleCursor - 1);
			}
			
			// Change position cursor
			else if(event.getKeyCode() == KeyEvent.VK_LEFT) {
				if(soundConfCursor > 0) {
					soundConfCursor--;
				}
				else if(soundCursor / 8 > 0) {
					soundConfCursor = 4;
					soundCursor = soundCursor - 8;
				}
			}
			else if(event.getKeyCode() == KeyEvent.VK_RIGHT) {
				if(soundConfCursor < 4) {
					soundConfCursor++;
				}
				else if(soundCursor / 8 < 3) {
					soundConfCursor = 0;
					soundCursor = soundCursor + 8;
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
			else if(event.getKeyCode() == KeyEvent.VK_A) {
				setSound(Note.C);
			}
			else if(event.getKeyCode() == KeyEvent.VK_2) {
				setSound(Note.C_D);
			}
			else if(event.getKeyCode() == KeyEvent.VK_Z) {
				setSound(Note.D);
			}
			else if(event.getKeyCode() == KeyEvent.VK_3) {
				setSound(Note.D_D);
			}
			else if(event.getKeyCode() == KeyEvent.VK_E) {
				setSound(Note.E);
			}
			else if(event.getKeyCode() == KeyEvent.VK_R) {
				setSound(Note.F);
			}
			else if(event.getKeyCode() == KeyEvent.VK_5) {
				setSound(Note.F_D);
			}
			else if(event.getKeyCode() == KeyEvent.VK_T) {
				setSound(Note.G);
			}
			else if(event.getKeyCode() == KeyEvent.VK_6) {
				setSound(Note.G_D);
			}
			else if(event.getKeyCode() == KeyEvent.VK_Y) {
				setSound(Note.A);
			}
			else if(event.getKeyCode() == KeyEvent.VK_7) {
				setSound(Note.A_D);
			}
			else if(event.getKeyCode() == KeyEvent.VK_U) {
				setSound(Note.B);
			}
			else if(event.getKeyCode() >= KeyEvent.VK_NUMPAD0 && event.getKeyCode() <= KeyEvent.VK_NUMPAD9) {
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
			else if(event.getKeyCode() == KeyEvent.VK_DELETE) {
				deleteSound();
			}
			
			// Play sample
			else if(event.getKeyCode() == KeyEvent.VK_SPACE) {
				if(!chanels.isPlaySample() && !chanels.isPlayPattern()) {
					chanels.playSample(sampleCursor);
				}
				else {
					chanels.stopSample();
				}
			}
			
			asciiTerminal.setEvent(null);
		}
	}
	
	/*
	 * -----------------
	 * Key actions
	 * -----------------
	 */
	
	private void setSound(Note note) {
		ChiptuneTracker.getInstance().setChangeData(true);
		Sample sample = ChiptuneTracker.getInstance().getData().samples.get(sampleCursor);
		
		if(volumeCursor != 0) {
			Sound sound = sample.sounds[soundCursor];
			if(sound == null) {
				sound = new Sound();
				sample.sounds[soundCursor] = sound;
			}
			
			sound.note = note;
			sound.octave = octaveCursor;
			sound.instrument = instrumentCursor;
			sound.volume = volumeCursor;
			
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
		ChiptuneTracker.getInstance().setChangeData(true);
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
		ChiptuneTracker.getInstance().setChangeData(true);
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
		ChiptuneTracker.getInstance().setChangeData(true);
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
		ChiptuneTracker.getInstance().setChangeData(true);
		Sample sample = ChiptuneTracker.getInstance().getData().samples.get(sampleCursor);
		sample.sounds[soundCursor] = null;
		
		soundCursor++;
		if(soundCursor > Sample.SIZE - 1) {
			soundCursor = 0;
		}
	}
	
	/*
	 * ----------------
	 * Buttons
	 * ----------------
	 */
	
	public void changeSample(int i) {
		Data data = ChiptuneTracker.getInstance().getData();
		if(i >= 0 && i < 100) {
			sampleCursor = i;			
		}
		
		if(data.samples.size() < i + 1) {
			data.samples.add(new Sample());
		}
		
		buttonLoopStartSample.setName(String.format("%02d", data.samples.get(sampleCursor).loopStart));
		buttonLoopStopSample.setName(String.format("%02d", data.samples.get(sampleCursor).loopStop));
	}
	
	public void changeOctave(String octave) {
		octaveCursor = Integer.valueOf(octave);
	}
	
	public void changeVolume(String volume) {
		volumeCursor = Integer.valueOf(volume);
	}
	
	public void changeInstrument(String instrument) {
		instrumentCursor = Integer.valueOf(instrument.charAt(0) - 224);
	}

	/*
	 * ----------------
	 * Paint
	 * ----------------
	 */
	
	@Override
	public void paint() {
		AsciiPanel asciiPanel = ChiptuneTracker.getInstance().getAsciiPanel();
		Sample sample = ChiptuneTracker.getInstance().getData().samples.get(sampleCursor);
		
		// Sample
		asciiPanel.writeString(3, 2, String.format("%02d", sampleCursor), Color.WHITE);
		
		// Speed
		asciiPanel.writeString(8, 2, "Spd", Color.LIGHT_GRAY);
		asciiPanel.writeString(13, 2, String.format("%02d", sample.speed), Color.LIGHT_GRAY, Color.BLACK);
		
		// Loop
		asciiPanel.writeString(18, 2, "Loop", Color.LIGHT_GRAY);
		
		// Octave
		asciiPanel.writeString(1, 4, "Oct", Color.LIGHT_GRAY);
		
		// Volume
		asciiPanel.writeString(1, 6, "Vol", Color.LIGHT_GRAY);
		
		// Effects
		for(int i = 0; i < 8; i++) {
			asciiPanel.write(ChiptuneTracker.WINDOW_WIDTH - 8 - 1 + i, 6, (char)(240 + i), Color.LIGHT_GRAY, Color.BLACK);
		}
		
		// Sounds
		for(int i = 0; i < Sample.SIZE; i++) {
			int x = i / 8 * 7 + 1;
			int y = i % 8 + 8;
			
			Sound sound = sample.sounds[i];
			
			if(!ChiptuneTracker.getInstance().getChanels().isPlaySample() && i == soundCursor) {
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
				
				int soundPlayCursor = ChiptuneTracker.getInstance().getChanels().getSampleCursor(0);
				if(ChiptuneTracker.getInstance().getChanels().isPlaySample() && i == soundPlayCursor) {
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
	}
	
	public int getVolumeCursor() {
		return volumeCursor;
	}
	
	public int getOctaveCursor() {
		return octaveCursor;
	}
	
	public int getInstrumentCursor() {
		return instrumentCursor;
	}
}
