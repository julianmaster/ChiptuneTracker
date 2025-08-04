package io.github.julianmaster.chiptunetracker.core;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.FunctionOscillator;
import com.jsyn.util.WaveRecorder;
import com.softsynth.shared.time.TimeStamp;
import io.github.julianmaster.chiptunetracker.ChiptuneTracker;
import io.github.julianmaster.chiptunetracker.model.Notes;
import io.github.julianmaster.chiptunetracker.model.Pattern;
import io.github.julianmaster.chiptunetracker.model.Sample;
import io.github.julianmaster.chiptunetracker.model.Sound;
import io.github.julianmaster.chiptunetracker.osc.*;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class FileRecorder {
	public final static int CHANELS = Chanels.CHANELS;
	private Synthesizer synth;
	private ChanelRecorder[] chanels;
	private WaveRecorder recorder;

	private int currentPattern;
	private boolean savePattern = false;

	public FileRecorder() {
		synth = JSyn.createSynthesizer();
		synth.setRealTime(false);

		chanels = new ChanelRecorder[CHANELS];

		for(int i = 0; i < CHANELS; i++) {
			chanels[i] = new ChanelRecorder(this);
		}
	}





	/**
	 * ----------
	 * Pattern
	 * ----------
	 */

	public void savePattern(String filename) throws IOException, InterruptedException {
		File waveFile = new File(filename);
		if(waveFile.canWrite() || !waveFile.exists()) {
			recorder = new WaveRecorder(getSynth(), waveFile);

			for(int i = 0; i < CHANELS; i++) {
				chanels[i].clearLastSound();
			}

			for(int i = 0; i < CHANELS; i++) {
				chanels[i].init();
			}

			recorder.start();
			synth.start();

			currentPattern = 0;
			savePattern = true;
			double startSoundTime = synth.getCurrentTime()+synth.getFramePeriod();
			next(startSoundTime);
			double endSoundTime = update();

			synth.sleepUntil(endSoundTime);

			recorder.stop();
			recorder.close();

			synth.stop();
		}
		else {
			throw new IOException("Unable to write in the file !");
		}
	}






	/**
	 * ----------
	 * Update
	 * ----------
	 */

	public double update() {
		double soundTime = 0;

		do {
			for(int i = 0; i < CHANELS; i++) {
				boolean change = chanels[i].update();
				if(change) {
					soundTime = chanels[i].getLastSoundTime();
					next(soundTime);
					i = 0;
				}
			}
		} while(savePattern);

		return soundTime;
	}







	/**
	 * ----------
	 * Next
	 * ----------
	 */

	public void next(double startSoundTime) {
		for(int i = 0; i < CHANELS; i++) {
			chanels[i].stop();
		}

		for(int i = 0; i < CHANELS; i++) {
			chanels[i].clear();
		}

		if(currentPattern >= ChiptuneTracker.getInstance().getData().patterns.size()) {
			savePattern = false;
			return;
		}

		Pattern pattern = ChiptuneTracker.getInstance().getData().patterns.get(currentPattern);
		boolean finish = true;
		if(pattern.sample1 != null) {
			chanels[0].playSample(pattern.sample1, startSoundTime);
			finish = false;
		}
		if(pattern.sample2 != null) {
			chanels[1].playSample(pattern.sample2, startSoundTime);
			finish = false;
		}
		if(pattern.sample3 != null) {
			chanels[2].playSample(pattern.sample3, startSoundTime);
			finish = false;
		}
		if(pattern.sample4 != null) {
			chanels[3].playSample(pattern.sample4, startSoundTime);
			finish = false;
		}

		savePattern = !finish;
		if(savePattern) {
			currentPattern++;
		}
	}






	/**
	 * ----------
	 * Getters / Setters
	 * ----------
	 */

	public Synthesizer getSynth() {
		return synth;
	}

	public WaveRecorder getRecorder() {
		return recorder;
	}




	/**
	 * ----------
	 * ChanelRecorder class
	 * ----------
	 */

	class ChanelRecorder {
		public static final int GROUP = Chanel.GROUP;
		public static final int INSTRUMENTS = Chanel.INSTRUMENTS;
		public static final int VOLUME_MAX = Chanel.VOLUME_MAX;

		private final FileRecorder fileRecorder;

		private CustomCircuit[][] voices;
		private int currentGroup = 0;

		private boolean start = false;
		private boolean finish = false;

		private Sound lastSound = null;

		private int sample;
		private int sampleSpeed;
		private double sampleFrequency;
		private double lastSoundTime;
		private int soundCursor;

		public ChanelRecorder(FileRecorder fileRecorder) {
			this.fileRecorder = fileRecorder;

			voices = new CustomCircuit[GROUP][INSTRUMENTS];
		}

		public void init() {
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

			lastSound = null;
		}

		private void add(int group, int position, CustomCircuit circuit) {
			fileRecorder.getSynth().add(circuit);
			circuit.getOutput().connect(0, fileRecorder.getRecorder().getInput(), 0);
			circuit.getOutput().connect(0, fileRecorder.getRecorder().getInput(), 1);
			voices[group][position] = circuit;
		}

		public void playSample(int sampleIndex, double startSoundTime) {
			this.sample = sampleIndex;

			Sample samplePlay = ChiptuneTracker.getInstance().getData().samples.get(sample);

			lastSoundTime = startSoundTime;
			sampleSpeed = samplePlay.speed;
			sampleFrequency = 1 / ((double)samplePlay.speed / 2);
			soundCursor = 0;
			start = true;
			finish = false;
		}

		public boolean update() {
			if(finish) {
				return true;
			}

			if(!finish && sample != -1) {
				Sound sound = ChiptuneTracker.getInstance().getData().samples.get(sample).sounds[soundCursor];

				if(start && sound != null) {
					playNote(sound, soundCursor, sampleSpeed, lastSoundTime);
					lastSoundTime += sampleFrequency;
					start = false;
				}
				else if(start && sound == null) {
					lastSoundTime += sampleFrequency;
					start = false;
				}
				// Note exist
				else if(sound != null) {
					playNote(sound, soundCursor, sampleSpeed, lastSoundTime);
					lastSoundTime += sampleFrequency;
				}
				// Silence
				else {
					lastSoundTime += sampleFrequency;
				}

				if(soundCursor < Sample.SIZE) {
					soundCursor++;
				}

				if(soundCursor == ChiptuneTracker.getInstance().getData().samples.get(sample).loopStop && soundCursor != ChiptuneTracker.getInstance().getData().samples.get(sample).loopStart) {
					soundCursor = ChiptuneTracker.getInstance().getData().samples.get(sample).loopStart;
				}

				if(soundCursor == Sample.SIZE) {
					finish = true;
				}
			}
			return false;
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
				Sound[] sounds = new Sound[4];
				for(int i = 0; i < 4; i++) {
					sounds[i] = ChiptuneTracker.getInstance().getData().samples.get(sample).sounds[position / 4 * 4 + i];
				}

				int minNote = 0;
				for(int i = 0; i < 4; i++) {
					if(sounds[i] != null) {
						minNote = i;
						break;
					}
				}
				int maxNote = 4;
				for(int i = 4; i >= 0; i--) {
					if(sounds[i-1] != null) {
						maxNote = i;
						break;
					}
				}

				if(maxNote - minNote == 1) {
					soundToPlay.add(sounds[minNote]);
				}
				else if(maxNote - minNote == 2) {
					for(int i = minNote; i <= maxNote; i++) {
						soundToPlay.add(sounds[i]);
					}
				}
				else {
					int max = position % 2 == 0 ? 2 : 4;
					for(int i = position % 2 == 0 ? 0 : 2; i < max; i++) {
						if(sounds[i] != null) {
							soundToPlay.add(sounds[i]);
						}
					}
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


					// Start note
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

					voices[currentGroup][currentSound.instrument].usePreset(lastSound, currentSound.effect, frequency, volume, samplefrequency / (double)soundToPlay.size(), start);
					voices[currentGroup][currentSound.instrument].noteOn(frequency, volume, start);

					voices[currentGroup][currentSound.instrument].noteOff(end);

					lastSound = currentSound;
				}
			}
		}

		public void stop() {
			double time = fileRecorder.getSynth().getCurrentTime();
			for(int i = 0; i < GROUP; i++) {
				for(CustomCircuit circuit : voices[i]) {
					circuit.noteOff(new TimeStamp(time));
					circuit.noteOff(new TimeStamp(lastSoundTime));
				}
			}
		}

		public double getLastSoundTime() {
			return lastSoundTime;
		}

		public void clear() {
			sample = -1;
			finish = false;
		}

		public void clearLastSound() {
			this.lastSound = null;
		}
	}
}
