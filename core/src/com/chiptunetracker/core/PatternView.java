package com.chiptunetracker.core;

import com.asciiterminal.ui.AsciiSelectableTerminalButton;
import com.asciiterminal.ui.AsciiTerminal;
import com.asciiterminal.ui.AsciiTerminalButton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

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
		// Down button
		AsciiTerminalButton buttonDownSample = new AsciiTerminalButton(asciiTerminal, String.valueOf((char)17), 9, 2, Color.MAGENTA, Color.ORANGE, Color.ORANGE, asciiTerminal.getDefaultCharacterBackgroundColor());
		buttonDownSample.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				patternOffset--;
				changePatternOffset();
				changePatternButtons();
			}
		});
		terminalButtons.add(buttonDownSample);
		
		// Up button
		AsciiTerminalButton buttonUpSample = new AsciiTerminalButton(asciiTerminal, String.valueOf((char)16), ChiptuneTracker.WINDOW_WIDTH - 6, 2, Color.MAGENTA, Color.ORANGE, Color.ORANGE, asciiTerminal.getDefaultCharacterBackgroundColor());
		buttonUpSample.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				patternOffset++;
				changePatternOffset();
				changePatternButtons();
			}
		});
		terminalButtons.add(buttonUpSample);
		
		// pattern1
		pattern1 = new AsciiSelectableTerminalButton(asciiTerminal, String.format("%02d", patternCursor), 11, 2, Color.WHITE, Color.ORANGE, Color.ORANGE, Color.ORANGE, asciiTerminal.getDefaultCharacterBackgroundColor());
		pattern1.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				patternCursor = patternOffset;
				changePatternButtons();
				changeSampleButtons();
			}
		});
		pattern1.setSelected(true);
		terminalButtons.add(pattern1);
		
		// pattern2
		pattern2 = new AsciiSelectableTerminalButton(asciiTerminal, String.format("%02d", patternCursor + 1), 14, 2, Color.WHITE, Color.ORANGE, Color.ORANGE, Color.ORANGE, asciiTerminal.getDefaultCharacterBackgroundColor());
		pattern2.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				patternCursor = patternOffset + 1;
				changePatternButtons();
				changeSampleButtons();
			}
		});
		terminalButtons.add(pattern2);
		
		// pattern3
		pattern3 = new AsciiSelectableTerminalButton(asciiTerminal, String.format("%02d", patternCursor + 2), 17, 2, Color.WHITE, Color.ORANGE, Color.ORANGE, Color.ORANGE, asciiTerminal.getDefaultCharacterBackgroundColor());
		pattern3.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				patternCursor = patternOffset + 2;
				changePatternButtons();
				changeSampleButtons();
			}
		});
		terminalButtons.add(pattern3);
		
		// pattern4
		pattern4 = new AsciiSelectableTerminalButton(asciiTerminal, String.format("%02d", patternCursor + 3), 20, 2, Color.WHITE, Color.ORANGE, Color.ORANGE, Color.ORANGE, asciiTerminal.getDefaultCharacterBackgroundColor());
		pattern4.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				patternCursor = patternOffset + 3;
				changePatternButtons();
				changeSampleButtons();
			}
		});
		terminalButtons.add(pattern4);
	}
	
	public void createSampleButtons() {
		sample1 = new AsciiSelectableTerminalButton(asciiTerminal, String.valueOf((char)253), 1, 4, Color.WHITE, Color.YELLOW, Color.YELLOW, Color.GREEN, asciiTerminal.getDefaultCharacterBackgroundColor());
		sample1.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(sample1.isSelected()) {
					changeSample(1, 0);
				}
				else {
					chiptuneTracker.getData().patterns.get(patternCursor).sample1 = null;
				}
				changeSampleButtons();
			}
		});
		terminalButtons.add(sample1);
		
		sample2 = new AsciiSelectableTerminalButton(asciiTerminal, String.valueOf((char)253), 8, 4, Color.WHITE, Color.YELLOW, Color.YELLOW, Color.GREEN, asciiTerminal.getDefaultCharacterBackgroundColor());
		sample2.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(sample2.isSelected()) {
					changeSample(2, 0);
				}
				else {
					chiptuneTracker.getData().patterns.get(patternCursor).sample2 = null;
				}
				changeSampleButtons();
			}
		});
		terminalButtons.add(sample2);
		
		sample3 = new AsciiSelectableTerminalButton(asciiTerminal, String.valueOf((char)253), 15, 4, Color.WHITE, Color.YELLOW, Color.YELLOW, Color.GREEN, asciiTerminal.getDefaultCharacterBackgroundColor());
		sample3.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(sample3.isSelected()) {
					changeSample(3, 0);
				}
				else {
					chiptuneTracker.getData().patterns.get(patternCursor).sample3 = null;
				}
				changeSampleButtons();
			}
		});
		terminalButtons.add(sample3);
		
		sample4 = new AsciiSelectableTerminalButton(asciiTerminal, String.valueOf((char)253), 22, 4, Color.WHITE, Color.YELLOW, Color.YELLOW, Color.GREEN, asciiTerminal.getDefaultCharacterBackgroundColor());
		sample4.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(sample4.isSelected()) {
					changeSample(4, 0);
				}
				else {
					chiptuneTracker.getData().patterns.get(patternCursor).sample4 = null;
				}
				changeSampleButtons();
			}
		});
		terminalButtons.add(sample4);
	}
	
	public void createSampleDownButtons() {
		sample1Down = new AsciiTerminalButton(asciiTerminal, "-", 3, 4, Color.MAGENTA, Color.ORANGE, Color.ORANGE, asciiTerminal.getDefaultCharacterBackgroundColor());
		sample1Down.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Pattern pattern = chiptuneTracker.getData().patterns.get(patternCursor);
				if(pattern.sample1 != null) {
					changeSample(1, pattern.sample1 - 1);
				}
			}
		});
		
		sample2Down = new AsciiTerminalButton(asciiTerminal, "-", 10, 4, Color.MAGENTA, Color.ORANGE, Color.ORANGE, asciiTerminal.getDefaultCharacterBackgroundColor());
		sample2Down.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Pattern pattern = chiptuneTracker.getData().patterns.get(patternCursor);
				if(pattern.sample2 != null) {
					changeSample(2, pattern.sample2 - 1);
				}
			}
		});
		
		sample3Down = new AsciiTerminalButton(asciiTerminal, "-", 17, 4, Color.MAGENTA, Color.ORANGE, Color.ORANGE, asciiTerminal.getDefaultCharacterBackgroundColor());
		sample3Down.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Pattern pattern = chiptuneTracker.getData().patterns.get(patternCursor);
				if(pattern.sample3 != null) {
					changeSample(3, pattern.sample3 - 1);
				}
			}
		});
		
		sample4Down = new AsciiTerminalButton(asciiTerminal, "-", 24, 4, Color.MAGENTA, Color.ORANGE, Color.ORANGE, asciiTerminal.getDefaultCharacterBackgroundColor());
		sample4Down.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Pattern pattern = chiptuneTracker.getData().patterns.get(patternCursor);
				if(pattern.sample4 != null) {
					changeSample(4, pattern.sample4 - 1);
				}
			}
		});
	}
	
	public void createSampleUpButtons() {
		AsciiTerminal asciiTerminal = chiptuneTracker.getAsciiTerminal();
		
		sample1Up = new AsciiTerminalButton(asciiTerminal, "+", 6, 4, Color.MAGENTA, Color.ORANGE, Color.ORANGE, asciiTerminal.getDefaultCharacterBackgroundColor());
		sample1Up.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Pattern pattern = chiptuneTracker.getData().patterns.get(patternCursor);
				if(pattern.sample1 != null) {
					changeSample(1, pattern.sample1 + 1);
				}
			}
		});
		
		sample2Up = new AsciiTerminalButton(asciiTerminal, "+", 13, 4, Color.MAGENTA, Color.ORANGE, Color.ORANGE, asciiTerminal.getDefaultCharacterBackgroundColor());
		sample2Up.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Pattern pattern = chiptuneTracker.getData().patterns.get(patternCursor);
				if(pattern.sample2 != null) {
					changeSample(2, pattern.sample2 + 1);
				}
			}
		});
		
		sample3Up = new AsciiTerminalButton(asciiTerminal, "+", 20, 4, Color.MAGENTA, Color.ORANGE, Color.ORANGE, asciiTerminal.getDefaultCharacterBackgroundColor());
		sample3Up.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Pattern pattern = chiptuneTracker.getData().patterns.get(patternCursor);
				if(pattern.sample3 != null) {
					changeSample(3, pattern.sample3 + 1);
				}
			}
		});
		
		sample4Up = new AsciiTerminalButton(asciiTerminal, "+", 27, 4, Color.MAGENTA, Color.ORANGE, Color.ORANGE, asciiTerminal.getDefaultCharacterBackgroundColor());
		sample4Up.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Pattern pattern = chiptuneTracker.getData().patterns.get(patternCursor);
				if(pattern.sample4 != null) {
					changeSample(4, pattern.sample4 + 1);
				}
			}
		});
	}
	
	@Override
	public void init() {
		if(chiptuneTracker.isInitPatternView()) {
			chiptuneTracker.setInitPatternView(false);
			patternCursor = 0;
			patternOffset = 0;
			changePatternOffset();
			changePatternButtons();
			changeSampleButtons();
		}

		Pattern pattern = chiptuneTracker.getData().patterns.get(patternCursor);
		if(pattern.sample1 != null) {
			sample1.setSelected(true);
		}
		if(pattern.sample2 != null) {
			sample2.setSelected(true);
		}
		if(pattern.sample3 != null) {
			sample3.setSelected(true);
		}
		if(pattern.sample4 != null) {
			sample4.setSelected(true);
		}

		buttonMenuView.setSelected(false);
		buttonSampleView.setSelected(false);
		buttonPatternView.setSelected(true);
		super.init();
		AsciiTerminal asciiTerminal = chiptuneTracker.getAsciiTerminal();
		if(sample1.isSelected()) {
			asciiTerminal.addActor(sample1Down);
			asciiTerminal.addActor(sample1Up);
		}
		if(sample2.isSelected()) {
			asciiTerminal.addActor(sample2Down);
			asciiTerminal.addActor(sample2Up);
		}
		if(sample3.isSelected()) {
			asciiTerminal.addActor(sample3Down);
			asciiTerminal.addActor(sample3Up);
		}
		if(sample4.isSelected()) {
			asciiTerminal.addActor(sample4Down);
			asciiTerminal.addActor(sample4Up);
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
			if(chiptuneTracker.getData().patterns.size() < patternOffset + i) {
				chiptuneTracker.getData().patterns.add(new Pattern());
			}
		}
	}
	
	public void changePatternButtons() {
		Data data = chiptuneTracker.getData();
		pattern1.setName(String.format("%02d", patternOffset));
		pattern2.setName(String.format("%02d", patternOffset + 1));
		pattern3.setName(String.format("%02d", patternOffset + 2));
		pattern4.setName(String.format("%02d", patternOffset + 3));
		
		if(patternCursor == patternOffset) {
			pattern1.setSelected(true);
		}
		else {
			pattern1.setSelected(false);
			Pattern pattern = data.patterns.get(patternOffset);
			if(pattern.sample1 == null && pattern.sample2 == null && pattern.sample3 == null && pattern.sample4 == null) {
				pattern1.setMouseDefaultColor(Color.LIGHT_GRAY);
			}
			else {
				pattern1.setMouseDefaultColor(Color.WHITE);
			}
		}
		
		if(patternCursor == patternOffset + 1) {
			pattern2.setSelected(true);
		}
		else {
			pattern2.setSelected(false);
			Pattern pattern = data.patterns.get(patternOffset + 1);
			if(pattern.sample1 == null && pattern.sample2 == null && pattern.sample3 == null && pattern.sample4 == null) {
				pattern2.setMouseDefaultColor(Color.LIGHT_GRAY);
			}
			else {
				pattern2.setMouseDefaultColor(Color.WHITE);
			}
		}
		
		if(patternCursor == patternOffset + 2) {
			pattern3.setSelected(true);
		}
		else {
			pattern3.setSelected(false);
			Pattern pattern = data.patterns.get(patternOffset + 2);
			if(pattern.sample1 == null && pattern.sample2 == null && pattern.sample3 == null && pattern.sample4 == null) {
				pattern3.setMouseDefaultColor(Color.LIGHT_GRAY);
			}
			else {
				pattern3.setMouseDefaultColor(Color.WHITE);
			}
		}
			
		if(patternCursor == patternOffset + 3) {
			pattern4.setSelected(true);
		}
		else {
			pattern4.setSelected(false);
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
		chiptuneTracker.setChangeData(true);
		Pattern pattern = chiptuneTracker.getData().patterns.get(patternCursor);

		if(pattern.sample1 != null) {
			sample1.setLabel(String.valueOf((char)254));
			asciiTerminal.addActor(sample1Down);
			asciiTerminal.addActor(sample1Up);
		}
		else {
			sample1.setLabel(String.valueOf((char)253));
			sample1Down.remove();
			sample1Up.remove();
		}
		
		if(pattern.sample2 != null) {
			sample2.setLabel(String.valueOf((char)254));
			asciiTerminal.addActor(sample2Down);
			asciiTerminal.addActor(sample2Up);
		}
		else {
			sample2.setLabel(String.valueOf((char)253));
			sample2Down.remove();
			sample2Up.remove();
		}
		
		if(pattern.sample3 != null) {
			sample3.setLabel(String.valueOf((char)254));
			asciiTerminal.addActor(sample3Down);
			asciiTerminal.addActor(sample3Up);
		}
		else {
			sample3.setLabel(String.valueOf((char)253));
			sample3Down.remove();
			sample3Up.remove();
		}
		
		if(pattern.sample4 != null) {
			sample4.setLabel(String.valueOf((char)254));
			asciiTerminal.addActor(sample4Down);
			asciiTerminal.addActor(sample4Up);
		}
		else {
			sample4.setLabel(String.valueOf((char)253));
			sample4Down.remove();
			sample4Up.remove();
		}
	}
	
	public void changeSample(int samplePattern, int sample) {
		chiptuneTracker.setChangeData(true);
		Data data = chiptuneTracker.getData();
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
		Chanels chanels = chiptuneTracker.getChanels();
		Pattern pattern = chiptuneTracker.getData().patterns.get(patternCursor);
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
			if(soundConfCursor > 0) {
				soundConfCursor--;
			}
			else if(sampleCursor > 0) {
				int nextSelectSample = sampleCursor;
				for(int i = sampleCursor - 1; i >= 0; i--) {
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
		else if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
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
		else if(Gdx.input.isKeyJustPressed(Input.Keys.Q) && pattern.getList().get(sampleCursor) != null) {
			setSound(Note.C);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_2) && pattern.getList().get(sampleCursor) != null) {
			setSound(Note.C_D);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.W) && pattern.getList().get(sampleCursor) != null) {
			setSound(Note.D);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_3) && pattern.getList().get(sampleCursor) != null) {
			setSound(Note.D_D);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.E) && pattern.getList().get(sampleCursor) != null) {
			setSound(Note.E);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.R) && pattern.getList().get(sampleCursor) != null) {
			setSound(Note.F);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_5) && pattern.getList().get(sampleCursor) != null) {
			setSound(Note.F_D);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.T) && pattern.getList().get(sampleCursor) != null) {
			setSound(Note.G);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_6) && pattern.getList().get(sampleCursor) != null) {
			setSound(Note.G_D);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.Y) && pattern.getList().get(sampleCursor) != null) {
			setSound(Note.A);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_7) && pattern.getList().get(sampleCursor) != null) {
			setSound(Note.A_D);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.U) && pattern.getList().get(sampleCursor) != null) {
			setSound(Note.B);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_0) && pattern.getList().get(sampleCursor) != null) {
			setNumberParameter(0);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_1) && pattern.getList().get(sampleCursor) != null) {
			setNumberParameter(1);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_2) && pattern.getList().get(sampleCursor) != null) {
			setNumberParameter(2);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_3) && pattern.getList().get(sampleCursor) != null) {
			setNumberParameter(3);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_4) && pattern.getList().get(sampleCursor) != null) {
			setNumberParameter(4);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_5) && pattern.getList().get(sampleCursor) != null) {
			setNumberParameter(5);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_6) && pattern.getList().get(sampleCursor) != null) {
			setNumberParameter(6);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_7) && pattern.getList().get(sampleCursor) != null) {
			setNumberParameter(7);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_8) && pattern.getList().get(sampleCursor) != null) {
			setNumberParameter(8);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_9) && pattern.getList().get(sampleCursor) != null) {
			setNumberParameter(9);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.DEL) && pattern.getList().get(sampleCursor) != null) {
			deleteSound();
		}

		// Play pattern
		else if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			if(!chanels.isPlaySample() && !chanels.isPlayPattern()) {
				chanels.playPattern(patternCursor);
			}
			else {
				chanels.stopPattern();
			}
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
		
		if(chiptuneTracker.getSampleView().getVolumeCursor() != 0) {
			Sound sound = sample.sounds[soundCursor];
			if(sound == null) {
				sound = new Sound();
				sample.sounds[soundCursor] = sound;
			}
			
			sound.note = note;
			sound.octave = chiptuneTracker.getSampleView().getOctaveCursor();
			sound.instrument = chiptuneTracker.getSampleView().getInstrumentCursor();
			sound.volume = chiptuneTracker.getSampleView().getVolumeCursor();
			
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
	
	public void deleteSound() {
		chiptuneTracker.setChangeData(true);
		Sample sample = chiptuneTracker.getData().samples.get(sampleCursor);
		sample.sounds[soundCursor] = null;
		
		soundCursor++;
		if(soundCursor > Sample.SIZE - 1) {
			soundCursor = 0;
		}
	}
	
	@Override
	public void paint() {
		Data data = chiptuneTracker.getData();
		Chanels chanels = chiptuneTracker.getChanels();
		AsciiTerminal asciiTerminal = chiptuneTracker.getAsciiTerminal();
		
		for(int i = 0; i < ChiptuneTracker.WINDOW_WIDTH; i++) {
			asciiTerminal.write(i, 0, ' ', Color.WHITE, INDIGO);
			asciiTerminal.write(i, ChiptuneTracker.WINDOW_HEIGHT - 1, ' ', Color.WHITE, INDIGO);
		}
		
		// Pattern
		asciiTerminal.writeString(1, 2, "PATTERN", Color.GRAY);
		
		Pattern pattern = data.patterns.get(patternCursor);
		
		Sample sample1 = null;
		Sample sample2 = null;
		Sample sample3 = null;
		Sample sample4 = null;
		if(pattern.sample1 != null) {
			sample1 = data.samples.get(pattern.sample1);
			asciiTerminal.writeString(4, 4, String.format("%02d", pattern.sample1), Color.WHITE);
		}
		
		if(pattern.sample2 != null) {
			sample2 = data.samples.get(pattern.sample2);
			asciiTerminal.writeString(11, 4, String.format("%02d", pattern.sample2), Color.WHITE);
		}
		
		if(pattern.sample3 != null) {
			sample3 = data.samples.get(pattern.sample3);
			asciiTerminal.writeString(18, 4, String.format("%02d", pattern.sample3), Color.WHITE);
		}
		
		if(pattern.sample4 != null) {
			sample4 = data.samples.get(pattern.sample4);
			asciiTerminal.writeString(25, 4, String.format("%02d", pattern.sample4), Color.WHITE);
		}
		
		paintSample(0, sample1, 1, 5, asciiTerminal);
		paintSample(1, sample2, 8, 5, asciiTerminal);
		paintSample(2, sample3, 15, 5, asciiTerminal);
		paintSample(3, sample4, 22, 5, asciiTerminal);
	}
	
	public void paintSample(int index, Sample sample, int offsetX, int offsetY, AsciiTerminal asciiTerminal) {
		int soundOffset = 0;
		int cursorPosition = -1;
		
		if(chiptuneTracker.getChanels().isPlayPattern() && sample != null) {
			int sampleCursorPosition = chiptuneTracker.getChanels().getSampleCursor(index);
			
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
			
			// Don't play music
			if(!chiptuneTracker.getChanels().isPlayPattern() && index == sampleCursor && i + soundOffset == soundCursor) {
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
			//Play music
			else {
				Color backgroundColor = Color.BLACK;
				
				int soundPlayCursor = chiptuneTracker.getChanels().getSampleCursor(index);
				if(chiptuneTracker.getChanels().isPlayPattern() && i == cursorPosition) {
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
		if(sample1.isSelected()) {
			sample1Down.remove();
			sample1Up.remove();
		}
		if(sample2.isSelected()) {
			sample2Down.remove();
			sample2Up.remove();
		}
		if(sample3.isSelected()) {
			sample3Down.remove();
			sample3Up.remove();
		}
		if(sample4.isSelected()) {
			sample4Down.remove();
			sample4Up.remove();
		}
	}
}
