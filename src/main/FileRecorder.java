package main;

import java.io.File;
import java.io.IOException;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.FunctionOscillator;
import com.jsyn.unitgen.SawtoothOscillatorDPW;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.SquareOscillatorBL;
import com.jsyn.unitgen.TriangleOscillator;
import com.jsyn.unitgen.WhiteNoise;
import com.jsyn.util.WaveRecorder;
import com.softsynth.shared.time.TimeStamp;

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
				chanels[i].init();
			}
			
			recorder.start();
			synth.start();
			
			currentPattern = 0;
			savePattern = true;
			double startSoundTime = synth.getCurrentTime();
			double endSoundTime = update(startSoundTime);
			
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
	
	public double update(double startSoundTime) {
		double soundTime = startSoundTime;
		
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
		public static final int INSTRUMENTS = Chanel.INSTRUMENTS;
		public static final int VOLUME_MAX = Chanel.VOLUME_MAX;
		
		private final FileRecorder fileRecorder;
		
		private CustomCircuit[] voices;
		
		private boolean start = false;
		private boolean finish = false;
		
		private int sample;
		private int sampleSpeed;
		private double sampleFrequency;
		private double lastSoundTime;
		private int soundCursor;
		
		public ChanelRecorder(FileRecorder fileRecorder) {
			this.fileRecorder = fileRecorder;
			
			voices = new CustomCircuit[INSTRUMENTS];
		}
		
		public void init() {
			add(0, new OscillatorCircuit(new SineOscillator()));
			FunctionOscillator sineSawtoothOscillator = new FunctionOscillator();
			sineSawtoothOscillator.function.set(new SineSawtoothFunction());
			add(1, new OscillatorCircuit(sineSawtoothOscillator));
			add(2, new OscillatorCircuit(new SawtoothOscillatorDPW()));
			add(3, new OscillatorCircuit(new SquareOscillatorBL()));
			add(4, new OscillatorCircuit(new DemiSquareOscillator()));
			FunctionOscillator mountainOscillator = new FunctionOscillator();
			mountainOscillator.function.set(new MoutainFunction());
			add(5, new OscillatorCircuit(mountainOscillator));
			add(6, new WhiteNoiseCircuit(new WhiteNoise()));
			add(7, new OscillatorCircuit(new TriangleOscillator()));
		}
		
		public void add(int position, CustomCircuit circuit) {
			fileRecorder.getSynth().add(circuit);
			circuit.getOutput().connect(0, fileRecorder.getRecorder().getInput(), 0);
			circuit.getOutput().connect(0, fileRecorder.getRecorder().getInput(), 1);
			voices[position] = circuit;
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
			// Cap the value to 0.25 (1 chanel on 4 play as same time)
			final double volume = Math.min((double) sound.volume / (double) (VOLUME_MAX * Chanels.CHANELS), 0.25d);
			final double frequency = Notes.getFrequency(sound.octave, sound.note);
			double samplefrequency = 1 / ((double) speed / 2);
			
			final TimeStamp start = new TimeStamp(time);
			TimeStamp end = new TimeStamp(time + samplefrequency);
			
			voices[sound.instrument].noteOn(frequency, volume, start);
	
			voices[sound.instrument].noteOff(end);
		}
		
		public int getSample() {
			return sample;
		}
		
		public void stop() {
			double time = fileRecorder.getSynth().getCurrentTime();
			for(CustomCircuit circuit : voices) {
				circuit.noteOff(new TimeStamp(time));
				circuit.noteOff(new TimeStamp(lastSoundTime));
			}
		}
		
		public double getLastSoundTime() {
			return lastSoundTime;
		}
		
		public void clear() {
			sample = -1;
			finish = false;
		}
	}
}
