package com.chiptunetracker.core;

import com.chiptunetracker.model.Notes;
import com.chiptunetracker.model.Sample;
import com.chiptunetracker.model.Sound;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.FunctionOscillator;
import com.jsyn.unitgen.SawtoothOscillatorDPW;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.SquareOscillatorBL;
import com.jsyn.unitgen.TriangleOscillator;
import com.jsyn.unitgen.WhiteNoise;
import com.softsynth.shared.time.ScheduledCommand;
import com.softsynth.shared.time.TimeStamp;

import java.util.Deque;
import java.util.LinkedList;

public class Chanel {
	public static final int GROUP = 2;
	public static final int INSTRUMENTS = 8;
	public static final int VOLUME_MAX = 7;
	
	private final Chanels chanels;
	
	private CustomCircuit[][] voices;
	private int currentGroup = 0;
	private LinkedList<Integer> lastGroup = new LinkedList<>();

	private boolean start = false;
	private boolean finish = false;

	private Sound lastSound = null;
	
	private int pattern;
	private int sample;
	private int sampleSpeed;
	private double sampleFrequency;
	private double lastSoundTime;
	private int soundCursor;
	
	private int UICursor;
	
	public Chanel(Chanels chanels) {
		this.chanels = chanels;

		voices = new CustomCircuit[GROUP][INSTRUMENTS];

		for(int i = 0; i < GROUP; i++) {
			add(i, 0, new OscillatorCircuit(new SineOscillator()));
			FunctionOscillator sineSawtoothOscillator = new FunctionOscillator();
			sineSawtoothOscillator.function.set(new SineSawtoothFunction());
			add(i, 1, new OscillatorCircuit(sineSawtoothOscillator));
			add(i, 2, new OscillatorCircuit(new SawtoothOscillatorDPW()));
			add(i, 3, new OscillatorCircuit(new SquareOscillatorBL()));
			add(i, 4, new OscillatorCircuit(new DemiSquareOscillator()));
			FunctionOscillator mountainOscillator = new FunctionOscillator();
			mountainOscillator.function.set(new MoutainFunction());
			add(i, 5, new OscillatorCircuit(mountainOscillator));
			add(i, 6, new WhiteNoiseCircuit(new WhiteNoise()));
			add(i, 7, new OscillatorCircuit(new TriangleOscillator()));
		}
	}
	
	private void add(int group, int position, CustomCircuit circuit) {
		chanels.getSynth().add(circuit);
		circuit.getOutput().connect(0, chanels.getLineOut().input, 0);
		circuit.getOutput().connect(0, chanels.getLineOut().input, 1);
		voices[group][position] = circuit;
	}
	
	private Synthesizer getSynthesizer(int group, int instrument) {
		return voices[group][instrument].getSynthesizer();
	}
	
	public void play(Sound sound) {
		double time = chanels.getSynth().getCurrentTime();
		playNote(sound, 0, 16, time);
	}
	
	public void playSample(int sampleIndex, int nextPatternIndex) {
		this.sample = sampleIndex;
		this.pattern = nextPatternIndex;
		
		Sample samplePlay = ChiptuneTracker.getInstance().getData().samples.get(sample);
		
		lastSoundTime = chanels.getSynth().getCurrentTime();
		sampleSpeed = samplePlay.speed;
		sampleFrequency = 1 / ((double)samplePlay.speed / 2);
		soundCursor = 0;
		start = true;
		finish = false;

		// Timer UI cursor
		UICursor = 0;
	}
	
	public void update() {
		if(!finish && sample != -1) {
			double currentTime = chanels.getSynth().getCurrentTime();
			if(currentTime > lastSoundTime) {
				Sound sound = ChiptuneTracker.getInstance().getData().samples.get(sample).sounds[soundCursor];
				
				if(start && sound != null) {
					playNote(sound, soundCursor, sampleSpeed, currentTime);
					lastSoundTime = currentTime;
					start = false;
				}
				else if(start && sound == null) {
					lastSoundTime = currentTime;
					start = false;
				}
				// Note exist
				else if(sound != null) {
					lastSoundTime += sampleFrequency;
					playNote(sound, soundCursor, sampleSpeed, lastSoundTime);
				}
				// Silence
				else {
					lastSoundTime += sampleFrequency;
					playSilence(soundCursor, lastSoundTime);
				}
				
				if(soundCursor < Sample.SIZE) {
					soundCursor++;
				}
				
				if(soundCursor == ChiptuneTracker.getInstance().getData().samples.get(sample).loopStop && soundCursor != ChiptuneTracker.getInstance().getData().samples.get(sample).loopStart) {
					soundCursor = ChiptuneTracker.getInstance().getData().samples.get(sample).loopStart;
				}
				
				if(soundCursor == Sample.SIZE) {
					finish = true;
					stop(lastSoundTime + sampleFrequency);
				}
			}
		}
	}

	private void playNote(final Sound sound, final int position, int speed, double time) {
		// Cap the value to 0.25 (1 chanel on 4 play as same time)
		final double volume = Math.min((double) sound.volume / (double) (VOLUME_MAX * Chanels.CHANELS), 0.25d);
		final double frequency = Notes.getFrequency(sound.octave, sound.note);
		double samplefrequency = 1 / ((double) speed / 2);
		
		final TimeStamp start = new TimeStamp(time);
		TimeStamp end = new TimeStamp(time + samplefrequency);

		getSynthesizer(currentGroup, sound.instrument).scheduleCommand(start, new ScheduledCommand() {
			@Override
			public void run() {
				if(lastSound != null && lastSound.instrument == sound.instrument) {
					boolean change = false;
					if(lastSound.effect == 5 && sound.effect != 4) {
						change = true;
					}
					else if(lastSound.effect == 4 && sound.effect == 4) {
						change = true;
					}
					else if(lastSound.effect != 5 && sound.effect == 4) {
						change = true;
					}

					if(change) {
						currentGroup = currentGroup < GROUP - 1 ? currentGroup+1 : 0;
					}
				}

				lastGroup.add(currentGroup);
				voices[currentGroup][sound.instrument].usePreset(sound.effect, frequency, volume, samplefrequency, getSynthesizer(currentGroup, sound.instrument).createTimeStamp());
				voices[currentGroup][sound.instrument].noteOn(frequency, volume, getSynthesizer(currentGroup, sound.instrument).createTimeStamp());
				UICursor = position;
				lastSound = sound;
			}
		});
		
		getSynthesizer(currentGroup, sound.instrument).scheduleCommand(end, new ScheduledCommand() {
			@Override
			public void run() {
				int group = currentGroup;
				if(!lastGroup.isEmpty()) {
					group = lastGroup.pop();
				}
				voices[group][sound.instrument].noteOff(getSynthesizer(group, sound.instrument).createTimeStamp());
			}
		});
	}
	
	private void playSilence(final int position, double time) {
		final TimeStamp start = new TimeStamp(time);
		chanels.getSynth().scheduleCommand(start, new ScheduledCommand() {
			@Override
			public void run() {
				lastSound = null;
				UICursor = position;
			}
		});
	}
	
	public int getSample() {
		return sample;
	}
	
	public void stop() {
		double time = chanels.getSynth().getCurrentTime();
		for(int i = 0; i < GROUP; i++) {
			for(CustomCircuit circuit : voices[i]) {
				circuit.noteOff(new TimeStamp(time));
				circuit.noteOff(new TimeStamp(lastSoundTime));
			}
		}
		lastGroup.clear();
	}
	
	private void stop(double time) {
		final TimeStamp start = new TimeStamp(time);
		chanels.getSynth().scheduleCommand(start, new ScheduledCommand() {
			@Override
			public void run() {
				synchronized (chanels) {
					if(finish && (pattern == -1 || pattern == chanels.getNextPattern())) {
						chanels.next();
					}
				}
			}
		});
	}
	
	public void clear() {
		pattern = -1;
		sample = -1;
		finish = false;
	}
	
	public int getSoundCursor() {
		return UICursor;
	}

	public void clearLastSound() {
		this.lastSound = null;
	}
}
