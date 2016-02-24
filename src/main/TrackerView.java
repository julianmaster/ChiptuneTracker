package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import ui.SelectableTerminalButton;
import ui.TerminalButton;

public class TrackerView extends View {
	
	private int sampleCursor = 1;
	private int soundCursor = 0;
	private int soundConfCursor = 0;
	private List<TerminalButton> terminalButtons = new ArrayList<>();
	
	// Octave cursor
	private int octaveCursor = 2;
	// Octave button list
	private List<SelectableTerminalButton> octaveButtons = new ArrayList<>();
	// Current octave button active
	private SelectableTerminalButton currentOctaveButton = null;
	
	// Volume cursor
	private int volumeCursor = 5;
	// Volume button list
	private List<SelectableTerminalButton> volumeButtons = new ArrayList<>();
	// Current volume button active
	private SelectableTerminalButton currentVolumeButton = null;
	
	// Oscillator cursor
	private int instrumentCursor = 0;
	// Oscillator button list
	private List<SelectableTerminalButton> instrumentButtons = new ArrayList<>();
	// Current oscillator button active
	private SelectableTerminalButton currentInstrumentButton = null;
	
	public TrackerView() {
		createSampleButtons();
		createOctaveButtons();
		createVolumeButtons();
		createSpeedButtons();
		createOscillatorButtons();
		changeSample(1);
	}
	
	public void createSampleButtons() {
		TerminalButton buttonDownSample = new TerminalButton(String.valueOf((char)17), Color.MAGENTA, Color.ORANGE, 1, 1, ChiptuneTracker.CHARACTER_WIDTH, ChiptuneTracker.CHARACTER_HEIGHT);
		buttonDownSample.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				changeSample(sampleCursor - 1);
			}
		});
		terminalButtons.add(buttonDownSample);
		
		TerminalButton buttonUpSample = new TerminalButton(String.valueOf((char)16), Color.MAGENTA, Color.ORANGE, 6, 1, ChiptuneTracker.CHARACTER_WIDTH, ChiptuneTracker.CHARACTER_HEIGHT);
		buttonUpSample.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				changeSample(sampleCursor + 1);
			}
		});
		terminalButtons.add(buttonUpSample);
	}
	
	public void createOctaveButtons() {
		for(int i = 1; i <= 4; i++) {
			SelectableTerminalButton button = new SelectableTerminalButton(String.valueOf(i), Color.LIGHT_GRAY, Color.WHITE, Color.GREEN, 4 + i, 3, ChiptuneTracker.CHARACTER_WIDTH, ChiptuneTracker.CHARACTER_HEIGHT);
			button.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					currentOctaveButton.select(false);
					SelectableTerminalButton button = (SelectableTerminalButton)e.getSource();
					button.select(true);
					currentOctaveButton = button;
					changeOctave(button.getName());
				}
			});
			
			octaveButtons.add(button);
			if(i == 2) {
				currentOctaveButton = button;
				currentOctaveButton.select(true);
			}
		}
		terminalButtons.addAll(octaveButtons);
	}
	
	public void createVolumeButtons() {
		for(int i = 0; i <= 7; i++) {
			SelectableTerminalButton button = new SelectableTerminalButton(String.valueOf(i), Color.LIGHT_GRAY, Color.WHITE, Color.CYAN, 5 + i, 5, ChiptuneTracker.CHARACTER_WIDTH, ChiptuneTracker.CHARACTER_HEIGHT);
			button.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					currentVolumeButton.select(false);
					SelectableTerminalButton button = (SelectableTerminalButton)e.getSource();
					button.select(true);
					currentVolumeButton = button;
					changeVolume(button.getName());
				}
			});
			
			volumeButtons.add(button);
			if(i == 5) {
				currentVolumeButton = button;
				currentVolumeButton.select(true);
			}
		}
		terminalButtons.addAll(volumeButtons);
	}
	
	public void createOscillatorButtons() {
		for(int i = 0; i <= 7; i++) {
			SelectableTerminalButton button = new SelectableTerminalButton(String.valueOf((char)(224 + i)), Color.LIGHT_GRAY, Color.WHITE, Color.MAGENTA, ChiptuneTracker.WINDOW_WIDTH - 8 - 1 + i, 3, ChiptuneTracker.CHARACTER_WIDTH, ChiptuneTracker.CHARACTER_HEIGHT);
			button.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					currentInstrumentButton.select(false);
					SelectableTerminalButton button = (SelectableTerminalButton)e.getSource();
					button.select(true);
					currentInstrumentButton = button;
					changeInstrument(button.getName());
				}
			});
			
			instrumentButtons.add(button);
			if(i == 0) {
				currentInstrumentButton = button;
				currentInstrumentButton.select(true);
			}
		}
		terminalButtons.addAll(instrumentButtons);
	}
	
	public void createSpeedButtons() {
		TerminalButton reduceButton = new TerminalButton("-", Color.MAGENTA, Color.ORANGE, 13, 1, ChiptuneTracker.CHARACTER_WIDTH, ChiptuneTracker.CHARACTER_HEIGHT);
		reduceButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Sample sample = ChiptuneTracker.samples.get(sampleCursor - 1);
				if(sample.speed > 1) {
					sample.speed--;
				}
			}
		});
		terminalButtons.add(reduceButton);
		
		TerminalButton addButton = new TerminalButton("+", Color.MAGENTA, Color.ORANGE, 16, 1, ChiptuneTracker.CHARACTER_WIDTH, ChiptuneTracker.CHARACTER_HEIGHT);
		addButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Sample sample = ChiptuneTracker.samples.get(sampleCursor - 1);
				if(sample.speed < 32) {
					sample.speed++;
				}
			}
		});
		terminalButtons.add(addButton);
	}

	@Override
	public void init() {
		for(TerminalButton terminalButton : terminalButtons) {
			ChiptuneTracker.asciiPanel.add(terminalButton);
		}
	}

	@Override
	public boolean update(double delta) {
		boolean change = true;
//		KeyEvent event = ChiptuneTracker.asciiTerminal.getEvent();
//		if(event != null) {
//			// Change sample
//			if(event.getKeyCode() == KeyEvent.VK_ADD) {
//				changeSample(sampleCursor + 1);
//			}
//			else if(event.getKeyCode() == KeyEvent.VK_SUBTRACT) {
//				changeSample(sampleCursor - 1);
//			}
//			
//			// Change position cursor
//			else if(event.getKeyCode() == KeyEvent.VK_LEFT) {
//				if(soundConfCursor > 0) {
//					soundConfCursor--;
//				}
//				else if(soundCursor / 8 > 0) {
//					soundConfCursor = 4;
//					soundCursor = soundCursor - 8;
//				}
//			}
//			else if(event.getKeyCode() == KeyEvent.VK_RIGHT) {
//				if(soundConfCursor < 4) {
//					soundConfCursor++;
//				}
//				else if(soundCursor / 8 < 3) {
//					soundConfCursor = 0;
//					soundCursor = soundCursor + 8;
//				}
//			}
//			else if(event.getKeyCode() == KeyEvent.VK_UP) {
//				soundCursor--;
//				if(soundCursor < 0) {
//					soundCursor = Sample.SIZE - 1;
//				}
//			}
//			else if(event.getKeyCode() == KeyEvent.VK_DOWN) {
//				soundCursor++;
//				if(soundCursor > Sample.SIZE - 1) {
//					soundCursor = 0;
//				}
//			}
//			
//			// Write note
//			else if(event.getKeyCode() == KeyEvent.VK_A) {
//				setSound(Note.C);
//			}
//			else if(event.getKeyCode() == KeyEvent.VK_2) {
//				setSound(Note.C_D);
//			}
//			else if(event.getKeyCode() == KeyEvent.VK_Z) {
//				setSound(Note.D);
//			}
//			else if(event.getKeyCode() == KeyEvent.VK_3) {
//				setSound(Note.D_D);
//			}
//			else if(event.getKeyCode() == KeyEvent.VK_E) {
//				setSound(Note.E);
//			}
//			else if(event.getKeyCode() == KeyEvent.VK_R) {
//				setSound(Note.F);
//			}
//			else if(event.getKeyCode() == KeyEvent.VK_5) {
//				setSound(Note.F_D);
//			}
//			else if(event.getKeyCode() == KeyEvent.VK_T) {
//				setSound(Note.G);
//			}
//			else if(event.getKeyCode() == KeyEvent.VK_6) {
//				setSound(Note.G_D);
//			}
//			else if(event.getKeyCode() == KeyEvent.VK_Y) {
//				setSound(Note.A);
//			}
//			else if(event.getKeyCode() == KeyEvent.VK_7) {
//				setSound(Note.A_D);
//			}
//			else if(event.getKeyCode() == KeyEvent.VK_U) {
//				setSound(Note.B);
//			}
//			else if(event.getKeyCode() >= KeyEvent.VK_NUMPAD0 && event.getKeyCode() <= KeyEvent.VK_NUMPAD9) {
//				// Octave
//				if(soundConfCursor == 1) {
//					setOctave(event.getKeyCode() - 96);
//				}
//				// Instrument
//				else if(soundConfCursor == 2) {
//					setInstrument(event.getKeyCode() - 96);
//				}
//				// Volume
//				else if(soundConfCursor == 3) {
//					setVolume(event.getKeyCode() - 96);
//				}
//			}
//			else if(event.getKeyCode() == KeyEvent.VK_DELETE) {
//				deleteSound();
//			}
//			else if(event.getKeyCode() == KeyEvent.VK_SPACE) {
//				if(!ChiptuneTracker.chanel.isPlay()) {
//					ChiptuneTracker.chanel.play(ChiptuneTracker.samples.get(sampleCursor - 1));
//				}
//				else {
//					ChiptuneTracker.chanel.stop();
//				}
//			}
//			else {
//				change = false;
//			}
//			
//			ChiptuneTracker.asciiTerminal.setEvent(null);
//		}
//		else {
//			change = false;
//		}
		
		return change;
	}
	
	/*
	 * -----------------
	 * Key actions
	 * -----------------
	 */
	
	private void setSound(Note note) {
		Sample sample = ChiptuneTracker.samples.get(sampleCursor - 1);
		
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
			
			ChiptuneTracker.chanel.play(sound, sample.speed);
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
			Sample sample = ChiptuneTracker.samples.get(sampleCursor - 1);
			Sound sound = sample.sounds[soundCursor];
			if(sound != null) {
				sound.octave = octave;
				
				soundCursor++;
				if(soundCursor > Sample.SIZE - 1) {
					soundCursor = 0;
				}
				
				ChiptuneTracker.chanel.play(sound, sample.speed);
			}
		}
	}
	
	private void setVolume(int volume) {
		if(volume >= 0 && volume <= 7) {
			Sample sample = ChiptuneTracker.samples.get(sampleCursor - 1);
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
			}
		}
	}
	
	private void setInstrument(int instrument) {
		if(instrument >= 0 && instrument <= 7) {
			Sample sample = ChiptuneTracker.samples.get(sampleCursor - 1);
			Sound sound = sample.sounds[soundCursor];
			if(sound != null) {
				sound.instrument = instrument;
				
				soundCursor++;
				if(soundCursor > Sample.SIZE - 1) {
					soundCursor = 0;
				}
			}
		}
	}
	
	public void deleteSound() {
		Sample sample = ChiptuneTracker.samples.get(sampleCursor - 1);
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
		if(i > 0 && i < 100) {
			sampleCursor = i;			
		}
		
		if(ChiptuneTracker.samples.size() < i) {
			ChiptuneTracker.samples.add(new Sample());
		}
	}
	
	public void changeOctave(String octave) {
		octaveCursor = Integer.valueOf(octave);
	}
	
	public void changeVolume(String volume) {
		volumeCursor = Integer.valueOf(volume);
	}
	
	public void changeInstrument(String instrument) {
		int numIntrument = Integer.valueOf(instrument.charAt(0)) - 224;
		if(numIntrument >= 0 && numIntrument <= 7) {
			Sample sample = ChiptuneTracker.samples.get(sampleCursor - 1);
			Sound sound = sample.sounds[soundCursor];
			if(sound != null) {
				sound.instrument = numIntrument;
				
				soundCursor++;
				if(soundCursor > Sample.SIZE - 1) {
					soundCursor = 0;
				}
			}
		}
	}

	@Override
	public void paint() {
//		for(Component terminalButton : terminalButtons) {
//			terminalButton.repaint();
//		}
		
		Sample sample = ChiptuneTracker.samples.get(sampleCursor - 1);
		
		// Sample
		ChiptuneTracker.asciiPanel.writeString(3, 1, String.format("%02d", sampleCursor), Color.WHITE);
		
		// Speed
		ChiptuneTracker.asciiPanel.writeString(9, 1, "Spd", Color.LIGHT_GRAY);
		ChiptuneTracker.asciiPanel.writeString(14, 1, String.format("%02d", sample.speed), Color.LIGHT_GRAY, Color.BLACK);
		
		// Octave
		ChiptuneTracker.asciiPanel.writeString(1, 3, "Oct", Color.LIGHT_GRAY);
		
		// Volume
		ChiptuneTracker.asciiPanel.writeString(1, 5, "Vol", Color.LIGHT_GRAY);
		
		// Effects
		for(int i = 0; i < 8; i++) {
			ChiptuneTracker.asciiPanel.write(ChiptuneTracker.WINDOW_WIDTH - 8 - 1 + i, 5, (char)(240 + i), Color.LIGHT_GRAY, Color.BLACK);
		}
		
		// Sounds
		for(int i = 0; i < Sample.SIZE; i++) {
			int x = i / 8 * 7 + 1;
			int y = i % 8 + 7;
			
			Sound sound = sample.sounds[i];
			
			if(i != soundCursor) {
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
			else {
				if(sound != null) {
					printSelect(0, x, y, sound.note.str, Color.WHITE);
					printSelect(1, x+2, y, String.valueOf(sound.octave), Color.LIGHT_GRAY);
					printSelect(2, x+3, y, String.valueOf(sound.instrument), Color.MAGENTA);
					printSelect(3, x+4, y, String.valueOf(sound.volume), Color.CYAN);
					printSelect(4, x+5, y, String.valueOf((char)239), Color.GRAY);
				}
				else {
					printSelect(0, x, y, String.valueOf((char)239), Color.GRAY);
					printSelect(0, x+1, y, String.valueOf((char)239), Color.GRAY);
					printSelect(1, x+2, y, String.valueOf((char)239), Color.GRAY);
					printSelect(2, x+3, y, String.valueOf((char)239), Color.GRAY);
					printSelect(3, x+4, y, String.valueOf((char)239), Color.GRAY);
					printSelect(4, x+5, y, String.valueOf((char)239), Color.GRAY);
				}
			}
		}
		
		ChiptuneTracker.asciiTerminal.repaint();
//		ChiptuneTracker.asciiPanel.repaint();
	}
	
	private void printSelect(int position, int x, int y, String s, Color color) {
		if(soundConfCursor == position) {
			ChiptuneTracker.asciiPanel.writeString(x, y, s, color, Color.YELLOW);
		}
		else {
			ChiptuneTracker.asciiPanel.writeString(x, y, s, color, Color.BLUE);
		}
	}

	@Override
	public void quit() {
		for(Component terminalButton : terminalButtons) {
			ChiptuneTracker.asciiPanel.remove(terminalButton);
		}
	}
}
