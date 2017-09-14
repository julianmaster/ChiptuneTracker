package com.chiptunetracker.core;

import com.chiptunetracker.model.Notes;
import com.chiptunetracker.model.Sample;
import com.chiptunetracker.model.Sound;
import com.chiptunetracker.osc.*;
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
			FunctionOscillator triOscillator = new FunctionOscillator();
			triOscillator.function.set(new TriFunction());
			add(i, 0, new OscillatorCircuit(triOscillator));

			FunctionOscillator unevenTriOscillator = new FunctionOscillator();
			unevenTriOscillator.function.set(new UnevenTriFunction());
			add(i, 1, new OscillatorCircuit(unevenTriOscillator));

			FunctionOscillator sawOscillator = new FunctionOscillator();
			sawOscillator.function.set(new SawFunction());
			add(i, 2, new OscillatorCircuit(sawOscillator));

			FunctionOscillator sqrOscillator = new FunctionOscillator();
			sqrOscillator.function.set(new SqrFunction());
			add(i, 3, new OscillatorCircuit(sqrOscillator));

			FunctionOscillator pulseOscillator = new FunctionOscillator();
			pulseOscillator.function.set(new PulseFunction());
			add(i, 4, new OscillatorCircuit(pulseOscillator));

			FunctionOscillator demiTriOscillator = new FunctionOscillator();
			demiTriOscillator.function.set(new DemiTriFunction());
			add(i, 5, new OscillatorCircuit(demiTriOscillator));

			FunctionOscillator noiseOscillator = new FunctionOscillator();
			noiseOscillator.function.set(new NoiseFunction());
			add(i, 6, new OscillatorCircuit(noiseOscillator));

			FunctionOscillator detunedTri1Oscillator = new FunctionOscillator();
			detunedTri1Oscillator.function.set(new DetunedTriFunction1());
			FunctionOscillator detunedTri2Oscillator = new FunctionOscillator();
			detunedTri2Oscillator.function.set(new DetunedTriFunction2());
			add(i, 7, new DualOscillatorCircuit(detunedTri1Oscillator, detunedTri2Oscillator));
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
		double samplefrequency = 1 / ((double) speed / 2);

		LinkedList<Sound> soundToPlay = new LinkedList<>();
		if(sound.effect != 6 && sound.effect != 7) {
			soundToPlay.push(sound);
		}
		else if(sound.effect == 6) {
			for(int soundArpeggio = 0; soundArpeggio < 4; soundArpeggio++) {
				soundToPlay.add(ChiptuneTracker.getInstance().getData().samples.get(sample).sounds[position / 4 * 4 + soundArpeggio]);
			}
		}
		else {
			boolean empty = true;
			int arpeggioIndex = position % 2 == 0 ? position / 4 * 4 : position / 4 * 4 + 2;
			for(int soundArpeggio = arpeggioIndex; soundArpeggio < arpeggioIndex + 2; soundArpeggio++) {
				Sound arpeggioSound = ChiptuneTracker.getInstance().getData().samples.get(sample).sounds[soundArpeggio];
				if(arpeggioSound != null) {
					empty = false;
				}
				soundToPlay.add(arpeggioSound);
			}
			if(empty) {
				playSilence(position, time);
				return;
			}
		}

		for(int soundIndex = 0; soundIndex < soundToPlay.size(); soundIndex++) {
			Sound currentSound = soundToPlay.get(soundIndex);
			if(currentSound != null) {

				// Cap the value to 0.25 (1 chanel on 4 play as same time)
				final double volume = Math.min((double) currentSound.volume / (double) (VOLUME_MAX * Chanels.CHANELS), 0.25d);
				final double frequency = Notes.getFrequency(currentSound.octave, currentSound.note);


				final TimeStamp start = new TimeStamp(time + samplefrequency / (double)soundToPlay.size() * soundIndex);
				TimeStamp end = new TimeStamp(time + samplefrequency / (double)soundToPlay.size() * (soundIndex + 1));


				getSynthesizer(currentGroup, currentSound.instrument).scheduleCommand(start, new ScheduledCommand() {
					@Override
					public void run() {
						if(lastSound != null && lastSound.instrument == currentSound.instrument) {
							boolean change = false;
							if(lastSound.effect == 5 && currentSound.effect != 4) {
								change = true;
							}
							else if(lastSound.effect == 4 && currentSound.effect == 4) {
								change = true;
							}
							else if(lastSound.effect != 5 && currentSound.effect == 4) {
								change = true;
							}

							if(change) {
								currentGroup = currentGroup < GROUP - 1 ? currentGroup+1 : 0;
							}
						}

						lastGroup.add(currentGroup);
						voices[currentGroup][currentSound.instrument].usePreset(lastSound, currentSound.effect, frequency, volume, samplefrequency / (double)soundToPlay.size(), getSynthesizer(currentGroup, currentSound.instrument).createTimeStamp());
						voices[currentGroup][currentSound.instrument].noteOn(frequency, volume, getSynthesizer(currentGroup, currentSound.instrument).createTimeStamp());
						UICursor = position;
						lastSound = currentSound;
					}
				});

				getSynthesizer(currentGroup, currentSound.instrument).scheduleCommand(end, new ScheduledCommand() {
					@Override
					public void run() {
						int group = currentGroup;
						if(!lastGroup.isEmpty()) {
							group = lastGroup.pop();
						}
						voices[group][currentSound.instrument].noteOff(getSynthesizer(group, currentSound.instrument).createTimeStamp());
					}
				});
			}
		}
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
