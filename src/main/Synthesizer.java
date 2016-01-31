package main;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Synthesizer {
	private int sampleCursor = 1;
	private int octaveCursor = 2;
	private int volumeCursor = 5;
	private int soundCursor = 0;
	private int soundConfCursor = 0;
	private List<Sample> samples = new ArrayList<>();

	public Synthesizer() {
		changeSample(sampleCursor);
	}
	
	public void run() {
		long lastLoopTime = System.nanoTime();
		
		
		while(true) {
			long now = System.nanoTime();
			double updateLength = now - lastLoopTime;
			lastLoopTime = now;
//			double delta = updateLength / ChiptuneTracker.OPTIMAL_TIME;
			
			KeyEvent event = ChiptuneTracker.terminal.getEvent();
			if(event != null) {
				// Change sample
				if(event.getKeyCode() == KeyEvent.VK_ADD) {
					changeSample(sampleCursor + 1);
				}
				if(event.getKeyCode() == KeyEvent.VK_SUBTRACT) {
					changeSample(sampleCursor - 1);
				}
				
				// Change position cursor
				if(event.getKeyCode() == KeyEvent.VK_LEFT) {
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
				
				ChiptuneTracker.terminal.setEvent(null);
			}
			
			show();
			
			try {
				Thread.sleep((lastLoopTime - System.nanoTime() + ChiptuneTracker.OPTIMAL_TIME) / 1000000);
			} catch (InterruptedException e) {
			}
		}
	}
	
	private void changeSample(int i) {
		if(i > 0 && i < 100) {
			sampleCursor = i;			
		}
		
		if(samples.size() < i) {
			samples.add(new Sample());
		}
	}
	
	private void show() {
		ChiptuneTracker.terminal.clear();
		Sample sample = samples.get(sampleCursor - 1);
		
		// Sample
		ChiptuneTracker.terminal.write(1, 1, (char)17, Color.MAGENTA);
		ChiptuneTracker.terminal.writeString(3, 1, String.format("%02d", sampleCursor), Color.WHITE);
		ChiptuneTracker.terminal.write(6, 1, (char)16, Color.MAGENTA);
		
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
			
			Sound sound = sample.sounds[soundCursor];
			
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
		ChiptuneTracker.terminal.repaint();
	}
	
	private void printSelect(int position, int x, int y, String s, Color color) {
		if(soundConfCursor == position) {
			ChiptuneTracker.terminal.writeString(x, y, s, color, Color.YELLOW);
		}
		else {
			ChiptuneTracker.terminal.writeString(x, y, s, color, Color.BLUE);
		}
	}
}
