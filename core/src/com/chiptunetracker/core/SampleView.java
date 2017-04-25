package com.chiptunetracker.core;

import java.util.ArrayList;
import java.util.List;

import com.asciiterminal.ui.AsciiSelectableTerminalButton;
import com.asciiterminal.ui.AsciiTerminal;
import com.asciiterminal.ui.AsciiTerminalButton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class SampleView extends View {
	
	private int sampleCursor = 0;
	private int soundCursor = 0;
	private int soundConfCursor = 0;
	
	// Loop buttons
	private AsciiTerminalButton buttonLoopStartSample;
	private AsciiTerminalButton buttonLoopStopSample;
	
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
	
	private void createSampleButtons() {
		AsciiTerminal asciiTerminal = chiptuneTracker.getAsciiTerminal();
		
		AsciiTerminalButton buttonDownSample = new AsciiTerminalButton(asciiTerminal, String.valueOf((char)17), 1, 2, Color.MAGENTA, Color.ORANGE, Color.ORANGE, asciiTerminal.getDefaultCharacterBackgroundColor());
		buttonDownSample.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				changeSample(sampleCursor - 1);
			}
		});
		terminalButtons.add(buttonDownSample);
		
		AsciiTerminalButton buttonUpSample = new AsciiTerminalButton(asciiTerminal, String.valueOf((char)16), 6, 2, Color.MAGENTA, Color.ORANGE, Color.ORANGE, asciiTerminal.getDefaultCharacterBackgroundColor());
		buttonUpSample.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				changeSample(sampleCursor + 1);
			}
		});
		terminalButtons.add(buttonUpSample);
	}

	private void createSpeedButtons() {
		AsciiTerminal asciiTerminal = chiptuneTracker.getAsciiTerminal();
		
		AsciiTerminalButton reduceButton = new AsciiTerminalButton(asciiTerminal, "-", 12, 2, Color.MAGENTA, Color.ORANGE, Color.ORANGE, asciiTerminal.getDefaultCharacterBackgroundColor());
		reduceButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Sample sample = chiptuneTracker.getData().samples.get(sampleCursor);
				if(sample.speed > 1) {
					sample.speed--;
				}
			}
		});
		terminalButtons.add(reduceButton);
		
		AsciiTerminalButton addButton = new AsciiTerminalButton(asciiTerminal, "+", 15, 2, Color.MAGENTA, Color.ORANGE, Color.ORANGE, asciiTerminal.getDefaultCharacterBackgroundColor());
		addButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Sample sample = chiptuneTracker.getData().samples.get(sampleCursor);
				if(sample.speed < 32) {
					sample.speed++;
				}
			}
		});
		terminalButtons.add(addButton);
	}

	private void createLoopButtons() {
		AsciiTerminal asciiTerminal = chiptuneTracker.getAsciiTerminal();
		
		Sample sample = null;
		if(!chiptuneTracker.getData().samples.isEmpty()) {
			sample = chiptuneTracker.getData().samples.get(sampleCursor);
		}
		
		buttonLoopStartSample = new AsciiTerminalButton(asciiTerminal, String.format("%02d", sample != null ? sample.loopStart : 0), ChiptuneTracker.WINDOW_WIDTH - 6, 2, Color.WHITE, Color.ORANGE, Color.ORANGE, Color.BLACK);
		buttonLoopStartSample.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Sample sample = chiptuneTracker.getData().samples.get(sampleCursor);
				if(event.getButton() == Input.Buttons.LEFT) {
					if(sample.loopStart < sample.loopStop) {
						sample.loopStart++;
					}
				}
				if(event.getButton() == Input.Buttons.RIGHT) {
					if(sample.loopStart > 0) {
						sample.loopStart--;
					}
				}
				buttonLoopStartSample.setName(String.format("%02d", sample.loopStart));
			}
		});
		terminalButtons.add(buttonLoopStartSample);
		
		buttonLoopStopSample = new AsciiTerminalButton(asciiTerminal, String.format("%02d", sample != null ? sample.loopStop : 0), ChiptuneTracker.WINDOW_WIDTH - 3, 2, Color.WHITE, Color.ORANGE, Color.ORANGE, Color.BLACK);
		buttonLoopStopSample.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Sample sample = chiptuneTracker.getData().samples.get(sampleCursor);
				if(event.getButton() == Input.Buttons.LEFT) {
					if(sample.loopStop < Sample.SIZE) {
						sample.loopStop++;
					}
				}
				if(event.getButton() == Input.Buttons.RIGHT) {
					if(sample.loopStop > sample.loopStart) {
						sample.loopStop--;
					}
				}
				buttonLoopStopSample.setName(String.format("%02d", sample.loopStop));
			}
		});
		terminalButtons.add(buttonLoopStopSample);
		
	}

	private void createOctaveButtons() {
		AsciiTerminal asciiTerminal = chiptuneTracker.getAsciiTerminal();
		
		for(int i = 1; i <= 4; i++) {
			AsciiSelectableTerminalButton button = new AsciiSelectableTerminalButton(asciiTerminal, String.valueOf(i), 4 + i, 4, Color.LIGHT_GRAY, Color.WHITE, Color.WHITE, Color.GREEN, asciiTerminal.getDefaultCharacterBackgroundColor());
			button.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					currentOctaveButton.setSelected(false);
					AsciiSelectableTerminalButton button = (AsciiSelectableTerminalButton)event.getTarget();
					button.setSelected(true);
					currentOctaveButton = button;
					changeOctave(button.getLabel());
				}
			});
			
			octaveButtons.add(button);
			if(i == 2) {
				currentOctaveButton = button;
				currentOctaveButton.setSelected(true);
			}
		}
		terminalButtons.addAll(octaveButtons);
	}

	private void createVolumeButtons() {
		AsciiTerminal asciiTerminal = chiptuneTracker.getAsciiTerminal();
		
		for(int i = 0; i <= 7; i++) {
			AsciiSelectableTerminalButton button = new AsciiSelectableTerminalButton(asciiTerminal, String.valueOf(i), 5 + i, 6, Color.LIGHT_GRAY, Color.WHITE, Color.WHITE, Color.CYAN, asciiTerminal.getDefaultCharacterBackgroundColor());
			button.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					currentVolumeButton.setSelected(false);
					AsciiSelectableTerminalButton button = (AsciiSelectableTerminalButton)event.getTarget();
					button.setSelected(true);
					currentVolumeButton = button;
					changeVolume(button.getLabel());
				}
			});
			
			volumeButtons.add(button);
			if(i == 5) {
				currentVolumeButton = button;
				currentVolumeButton.setSelected(true);
			}
		}
		terminalButtons.addAll(volumeButtons);
	}

	private void createOscillatorButtons() {
		AsciiTerminal asciiTerminal = chiptuneTracker.getAsciiTerminal();
		
		for(int i = 0; i <= 7; i++) {
			AsciiSelectableTerminalButton button = new AsciiSelectableTerminalButton(asciiTerminal, String.valueOf((char)(224 + i)), ChiptuneTracker.WINDOW_WIDTH - 8 - 1 + i, 4, Color.LIGHT_GRAY, Color.WHITE, Color.WHITE, Color.MAGENTA, asciiTerminal.getDefaultCharacterBackgroundColor());
			button.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					currentInstrumentButton.setSelected(false);
					AsciiSelectableTerminalButton button = (AsciiSelectableTerminalButton)event.getTarget();
					button.setSelected(true);
					currentInstrumentButton = button;
					changeInstrument(button.getLabel());
				}
			});
			
			instrumentButtons.add(button);
			if(i == 0) {
				currentInstrumentButton = button;
				currentInstrumentButton.setSelected(true);
			}
		}
		terminalButtons.addAll(instrumentButtons);
	}
	
	@Override
	public void init() {
		if(chiptuneTracker.isInitSampleView()) {
			chiptuneTracker.setInitSampleView(false);
			changeSample(0);
			soundCursor = 0;
			soundConfCursor = 0;
		}
		
		buttonMenuView.setSelected(false);
		buttonSampleView.setSelected(true);
		buttonPatternView.setSelected(false);
		
		Sample sample = chiptuneTracker.getData().samples.get(sampleCursor);
		buttonLoopStartSample.setName(String.format("%02d", sample.loopStart));
		buttonLoopStopSample.setName(String.format("%02d", sample.loopStop));
		
		super.init();
	}

	@Override
	public void update(double delta) {
		Chanels chanels = chiptuneTracker.getChanels();

		// Change sample
		if(Gdx.input.isKeyJustPressed(Input.Keys.PLUS)) {
			changeSample(sampleCursor + 1);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.MINUS)) {
			changeSample(sampleCursor - 1);
		}

		// Change position cursor
		else if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
			if(soundConfCursor > 0) {
				soundConfCursor--;
			}
			else if(soundCursor / 8 > 0) {
				soundConfCursor = 4;
				soundCursor = soundCursor - 8;
			}
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
			if(soundConfCursor < 4) {
				soundConfCursor++;
			}
			else if(soundCursor / 8 < 3) {
				soundConfCursor = 0;
				soundCursor = soundCursor + 8;
			}
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			soundCursor--;
			if(soundCursor < 0) {
				soundCursor = Sample.SIZE - 1;
			}
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			soundCursor++;
			if(soundCursor > Sample.SIZE - 1) {
				soundCursor = 0;
			}
		}

		// Write note
		else if(Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
			setSound(Note.C);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
			setSound(Note.C_D);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.W)) {
			setSound(Note.D);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
			setSound(Note.D_D);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.E)) {
			setSound(Note.E);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.R)) {
			setSound(Note.F);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) {
			setSound(Note.F_D);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.T)) {
			setSound(Note.G);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)) {
			setSound(Note.G_D);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.Y)) {
			setSound(Note.A);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_7)) {
			setSound(Note.A_D);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.U)) {
			setSound(Note.B);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_0)) {
			setNumberParameter(0);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_1)) {
			setNumberParameter(1);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_2)) {
			setNumberParameter(2);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_3)) {
			setNumberParameter(3);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_4)) {
			setNumberParameter(4);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_5)) {
			setNumberParameter(5);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_6)) {
			setNumberParameter(6);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_7)) {
			setNumberParameter(7);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_8)) {
			setNumberParameter(8);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_9)) {
			setNumberParameter(9);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.DEL)) {
			deleteSound();
		}

		// Play sample
		else if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			if(!chanels.isPlaySample() && !chanels.isPlayPattern()) {
				chanels.playSample(sampleCursor);
			}
			else {
				chanels.stopSample();
			}
		}
	}
	
	/*
	 * -----------------
	 * Key actions
	 * -----------------
	 */

	private void setNumberParameter(int value) {
		// Octave
		if(soundConfCursor == 1) {
			setOctave(value);
		}
		// Instrument
		else if(soundConfCursor == 2) {
			setInstrument(value);
		}
		// Volume
		else if(soundConfCursor == 3) {
			setVolume(value);
		}
	}
	
	private void setSound(Note note) {
		chiptuneTracker.setChangeData(true);
		Sample sample = chiptuneTracker.getData().samples.get(sampleCursor);
		
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
			
			chiptuneTracker.getChanels().playSound(sound);
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
		chiptuneTracker.setChangeData(true);
		if(octave >= 1 && octave <= 4) {
			Sample sample = chiptuneTracker.getData().samples.get(sampleCursor);
			Sound sound = sample.sounds[soundCursor];
			if(sound != null) {
				sound.octave = octave;
				
				soundCursor++;
				if(soundCursor > Sample.SIZE - 1) {
					soundCursor = 0;
				}
				
				chiptuneTracker.getChanels().playSound(sound);
			}
		}
	}
	
	private void setVolume(int volume) {
		chiptuneTracker.setChangeData(true);
		if(volume >= 0 && volume <= 7) {
			Sample sample = chiptuneTracker.getData().samples.get(sampleCursor);
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
				chiptuneTracker.getChanels().playSound(sound);
			}
		}
	}
	
	private void setInstrument(int instrument) {
		chiptuneTracker.setChangeData(true);
		if(instrument >= 0 && instrument <= 7) {
			Sample sample = chiptuneTracker.getData().samples.get(sampleCursor);
			Sound sound = sample.sounds[soundCursor];
			if(sound != null) {
				sound.instrument = instrument;
				
				soundCursor++;
				if(soundCursor > Sample.SIZE - 1) {
					soundCursor = 0;
				}
				chiptuneTracker.getChanels().playSound(sound);
			}
		}
	}

	private void deleteSound() {
		chiptuneTracker.setChangeData(true);
		Sample sample = chiptuneTracker.getData().samples.get(sampleCursor);
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

	private void changeSample(int i) {
		Data data = chiptuneTracker.getData();
		if(i >= 0 && i < 100) {
			sampleCursor = i;			
		}
		
		if(data.samples.size() < i + 1) {
			data.samples.add(new Sample());
		}
		
		buttonLoopStartSample.setName(String.format("%02d", data.samples.get(sampleCursor).loopStart));
		buttonLoopStopSample.setName(String.format("%02d", data.samples.get(sampleCursor).loopStop));
	}

	private void changeOctave(String octave) {
		octaveCursor = Integer.valueOf(octave);
	}

	private void changeVolume(String volume) {
		volumeCursor = Integer.valueOf(volume);
	}

	private void changeInstrument(String instrument) {
		instrumentCursor = instrument.charAt(0) - 224;
	}

	/*
	 * ----------------
	 * Paint
	 * ----------------
	 */
	
	@Override
	public void paint() {
		AsciiTerminal asciiTerminal = chiptuneTracker.getAsciiTerminal();
		Sample sample = chiptuneTracker.getData().samples.get(sampleCursor);
		
		for(int i = 0; i < ChiptuneTracker.WINDOW_WIDTH; i++) {
			asciiTerminal.write(i, 0, ' ', Color.WHITE, INDIGO);
			asciiTerminal.write(i, ChiptuneTracker.WINDOW_HEIGHT - 1, ' ', Color.WHITE, INDIGO);
		}
		
		// Sample
		asciiTerminal.writeString(3, 2, String.format("%02d", sampleCursor), Color.WHITE);
		
		// Speed
		asciiTerminal.writeString(8, 2, "Spd", Color.LIGHT_GRAY);
		asciiTerminal.writeString(13, 2, String.format("%02d", sample.speed), Color.LIGHT_GRAY, Color.BLACK);
		
		// Loop
		asciiTerminal.writeString(18, 2, "Loop", Color.LIGHT_GRAY);
		
		// Octave
		asciiTerminal.writeString(1, 4, "Oct", Color.LIGHT_GRAY);
		
		// Volume
		asciiTerminal.writeString(1, 6, "Vol", Color.LIGHT_GRAY);
		
		// Effects
		for(int i = 0; i < 8; i++) {
			asciiTerminal.write(ChiptuneTracker.WINDOW_WIDTH - 8 - 1 + i, 6, (char)(240 + i), Color.LIGHT_GRAY, Color.BLACK);
		}
		
		// Sounds
		for(int i = 0; i < Sample.SIZE; i++) {
			int x = i / 8 * 7 + 1;
			int y = i % 8 + 8;
			
			Sound sound = sample.sounds[i];
			
			if(!chiptuneTracker.getChanels().isPlaySample() && i == soundCursor) {
				if(sound != null) {
					printSound(asciiTerminal, x, y,
							sound.note.str, 				Color.WHITE, 	soundConfCursor == 0 ? Color.YELLOW : Color.BLUE,
							sound.octave.toString(), 		Color.GREEN, 	soundConfCursor == 1 ? Color.YELLOW : Color.BLUE,
							sound.instrument.toString(), 	Color.MAGENTA, 	soundConfCursor == 2 ? Color.YELLOW : Color.BLUE,
							sound.volume.toString(), 		Color.CYAN, 	soundConfCursor == 3 ? Color.YELLOW : Color.BLUE,
							DOT, 							Color.GRAY, 	soundConfCursor == 4 ? Color.YELLOW : Color.BLUE);
				}
				else {
					printSound(asciiTerminal, x, y,
							DOT + DOT, 	Color.GRAY, 	soundConfCursor == 0 ? Color.YELLOW : Color.BLUE,
							DOT, 		Color.GRAY, 	soundConfCursor == 1 ? Color.YELLOW : Color.BLUE,
							DOT, 		Color.GRAY, 	soundConfCursor == 2 ? Color.YELLOW : Color.BLUE,
							DOT, 		Color.GRAY, 	soundConfCursor == 3 ? Color.YELLOW : Color.BLUE,
							DOT, 		Color.GRAY, 	soundConfCursor == 4 ? Color.YELLOW : Color.BLUE);
				}
			}
			else {
				Color backgroundColor = Color.BLACK;
				
				int soundPlayCursor = chiptuneTracker.getChanels().getSampleCursor(0);
				if(chiptuneTracker.getChanels().isPlaySample() && i == soundPlayCursor) {
					backgroundColor = Color.YELLOW;
				}
				
				if(sound != null) {
					printSound(asciiTerminal, x, y,
							sound.note.str, 				Color.WHITE, 	backgroundColor,
							sound.octave.toString(), 		Color.GREEN, 	backgroundColor,
							sound.instrument.toString(), 	Color.MAGENTA, 	backgroundColor,
							sound.volume.toString(), 		Color.CYAN, 	backgroundColor,
							DOT, 							Color.GRAY, 	backgroundColor);
				}
				else {
					for(int j = x; j < x + 6; j++) {
						asciiTerminal.write(j, y, (char)239, Color.GRAY, backgroundColor);
					}
				}
			}
		}
	}
	
	private void printSound(AsciiTerminal asciiTerminal, int x, int y,
			String note, Color noteColor, Color noteBackColor,
			String octave, Color octaveColor, Color octaveBackColor,
			String instrument, Color instrumentColor, Color instrumentBackColor,
			String volume, Color volumeColor, Color volumeBackColor,
			String effect, Color effectColor, Color effectBackColor) {
		asciiTerminal.writeString(x, y, note, noteColor, noteBackColor);
		asciiTerminal.writeString(x+2, y, octave, octaveColor, octaveBackColor);
		asciiTerminal.writeString(x+3, y, instrument, instrumentColor, instrumentBackColor);
		asciiTerminal.writeString(x+4, y, volume, volumeColor, volumeBackColor);
		asciiTerminal.writeString(x+5, y, effect, effectColor, effectBackColor);
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
