package main;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import ui.TerminalButton;

public class TrackerView extends View {
	
	private Instrument instrumentCursor = Instrument.INSTRUMENT_1;
	private int sampleCursor = 1;
	private int octaveCursor = 2;
	private int volumeCursor = 5;
	private int soundCursor = 0;
	private int soundConfCursor = 0;
	private List<TerminalButton> terminalButtons = new ArrayList<>();
	
	public TrackerView(Synthesizer synthesizer) {
		super(synthesizer);
		createSampleButtons();
		changeSample(1);
	}
	
	public void createSampleButtons() {
		TerminalButton buttonDownSample = new TerminalButton(ChiptuneTracker.terminal, String.valueOf(
				(char)17), Color.MAGENTA, Color.ORANGE, 1, 1, ChiptuneTracker.CHARACTER_WIDTH, ChiptuneTracker.CHARACTER_HEIGHT);
		buttonDownSample.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				changeSample(sampleCursor - 1);
			}
		});
		terminalButtons.add(buttonDownSample);
		
		TerminalButton buttonUpSample = new TerminalButton(ChiptuneTracker.terminal, String.valueOf(
				(char)16), Color.MAGENTA, Color.ORANGE, 6, 1, ChiptuneTracker.CHARACTER_WIDTH, ChiptuneTracker.CHARACTER_HEIGHT);
		buttonUpSample.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				changeSample(sampleCursor + 1);
			}
		});
		terminalButtons.add(buttonUpSample);
	}

	@Override
	public void init() {
		for(TerminalButton terminalButton : terminalButtons) {
			ChiptuneTracker.terminal.add(terminalButton);
		}
	}

	@Override
	public void update(double delta) {
		KeyEvent event = ChiptuneTracker.terminal.getEvent();
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
				else if(soundCursor / 8 < 4) {
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
			
			
			ChiptuneTracker.terminal.setEvent(null);
		}
	}
	
	private void setSound(Note note) {
		Sample sample = synthesizer.samples.get(sampleCursor - 1);
		Sound sound = sample.sounds[soundCursor];
		
		if(sound == null) {
			sound = new Sound();
			sample.sounds[soundCursor] = sound;
		}
		
		sound.note = note;
		sound.octave = octaveCursor;
		sound.instrument = instrumentCursor;
		sound.volume = volumeCursor;
		
		soundCursor++;
		if(soundCursor > Sample.SIZE - 1) {
			soundCursor = 0;
		}
	}
	
	private void changeSample(int i) {
		if(i > 0 && i < 100) {
			sampleCursor = i;			
		}
		
		if(synthesizer.samples.size() < i) {
			synthesizer.samples.add(new Sample());
		}
	}

	@Override
	public void paint() {
		Sample sample = synthesizer.samples.get(sampleCursor - 1);
		
		for(TerminalButton terminalButton : terminalButtons) {
			terminalButton.paint();
		}
		
		// Sample
		ChiptuneTracker.terminal.writeString(3, 1, String.format("%02d", sampleCursor), Color.WHITE);
		
		// Speed
		ChiptuneTracker.terminal.writeString(9, 1, "Spd", Color.LIGHT_GRAY);
		ChiptuneTracker.terminal.writeString(13, 1, String.valueOf(sample.speed), Color.LIGHT_GRAY, Color.BLACK);
		
		// Octave
		ChiptuneTracker.terminal.writeString(1, 3, "Oct", Color.LIGHT_GRAY);
		for(int i = 1; i <= 4; i++) {
			if(i != octaveCursor) {
				ChiptuneTracker.terminal.writeString(5 + i - 1, 3, String.valueOf(i), Color.LIGHT_GRAY);
			}
			else {
				ChiptuneTracker.terminal.writeString(5 + i - 1, 3, String.valueOf(i), Color.GREEN);
			}
		}
		
		// Volume
		ChiptuneTracker.terminal.writeString(1, 5, "Vol", Color.LIGHT_GRAY);
		for(int i = 0; i <= 7; i++) {
			if(i != volumeCursor) {
				ChiptuneTracker.terminal.writeString(5 + i, 5, String.valueOf(i), Color.LIGHT_GRAY);
			}
			else {
				ChiptuneTracker.terminal.writeString(5 + i, 5, String.valueOf(i), Color.CYAN);
			}
		}
		
		// Sounds
		for(int i = 0; i < Sample.SIZE; i++) {
			int x = i / 8 * 7 + 1;
			int y = i % 8 + 7;
			
			Sound sound = sample.sounds[i];
			
			if(i != soundCursor) {
				if(sound != null) {
					ChiptuneTracker.terminal.writeString(x, y, sound.note.str, Color.WHITE, Color.BLACK);
					ChiptuneTracker.terminal.write(x+2, y, Character.forDigit(sound.octave, 10), Color.GREEN, Color.BLACK);
					ChiptuneTracker.terminal.write(x+3, y,  Character.forDigit(sound.instrument.number, 10), Color.MAGENTA, Color.BLACK);
					ChiptuneTracker.terminal.write(x+4, y,  Character.forDigit(sound.volume, 10), Color.CYAN, Color.BLACK);
					ChiptuneTracker.terminal.write(x+5, y, (char)250, Color.GRAY, Color.BLACK);
				}
				else {
					for(int j = x; j < x + 6; j++) {
						ChiptuneTracker.terminal.write(j, y, (char)250, Color.GRAY, Color.BLACK);
					}
				}
			}
			else {
				if(sound != null) {
					printSelect(0, x, y, sound.note.str, Color.WHITE);
					printSelect(1, x+2, y, String.valueOf(sound.octave), Color.LIGHT_GRAY);
					printSelect(2, x+3, y, String.valueOf(sound.instrument.number), Color.MAGENTA);
					printSelect(3, x+4, y, String.valueOf(sound.volume), Color.CYAN);
					printSelect(4, x+5, y, String.valueOf((char)250), Color.GRAY);
				}
				else {
					printSelect(0, x, y, String.valueOf((char)250), Color.GRAY);
					printSelect(0, x+1, y, String.valueOf((char)250), Color.GRAY);
					printSelect(1, x+2, y, String.valueOf((char)250), Color.GRAY);
					printSelect(2, x+3, y, String.valueOf((char)250), Color.GRAY);
					printSelect(3, x+4, y, String.valueOf((char)250), Color.GRAY);
					printSelect(4, x+5, y, String.valueOf((char)250), Color.GRAY);
				}
			}
		}
	}
	
	private void printSelect(int position, int x, int y, String s, Color color) {
		if(soundConfCursor == position) {
			ChiptuneTracker.terminal.writeString(x, y, s, color, Color.YELLOW);
		}
		else {
			ChiptuneTracker.terminal.writeString(x, y, s, color, Color.BLUE);
		}
	}

	@Override
	public void quit() {
		// TODO Auto-generated method stub
		
	}


}
