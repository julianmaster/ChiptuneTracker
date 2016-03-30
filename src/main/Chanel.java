package main;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.FunctionOscillator;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SawtoothOscillatorDPW;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.SquareOscillatorBL;
import com.jsyn.unitgen.TriangleOscillator;
import com.jsyn.unitgen.WhiteNoise;
import com.softsynth.shared.time.ScheduledCommand;
import com.softsynth.shared.time.TimeStamp;

public class Chanel {
	public static final int INSTRUMENTS = 8;
	public static final int VOLUME_MAX = 7;
	
	private final Chanels chanels;
	
	private Synthesizer synth;
	private CustomCircuit[] voices;
	private LineOut lineOut;
	
	private boolean start = false;
	private boolean finish = false;
	
	private int sample;
	private int sampleSpeed;
	private double sampleFrequency;
	private double lastSoundTime;
	private int soundCursor;
	
	private int UICursor;
	
	public Chanel(Chanels chanels) {
		this.chanels = chanels;
		synth = JSyn.createSynthesizer();
		lineOut = new LineOut();
		
		synth.add(lineOut);
		
		voices = new CustomCircuit[INSTRUMENTS];
		
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
		
		synth.start();
		lineOut.start();
	}
	
	private void add(int position, CustomCircuit circuit) {
		synth.add(circuit);
		circuit.getOutput().connect(0, lineOut.input, 0);
		circuit.getOutput().connect(0, lineOut.input, 1);
		voices[position] = circuit;
	}
	
	private Synthesizer getSynthesizer(int chanel, int instrument) {
		return voices[chanel * INSTRUMENTS + instrument].getSynthesizer();
	}
	
	public void play(Sound sound) {
		double time = synth.getCurrentTime();
		play(sound, 0, 0, 16, time);
	}
	
	public void playSample(int sampleIndex) {
		this.sample = sampleIndex;
		
		Sample samplePlay = ChiptuneTracker.samples.get(sample);
		
		lastSoundTime = synth.getCurrentTime();
		sampleSpeed = samplePlay.speed;
		sampleFrequency = 1 / ((double)samplePlay.speed / 2);
		soundCursor = 0;
		start = true;
		finish = false;

		// Timer UI cursor
		UICursor = -1;
	}
	
	public void update() {
		if(!finish) {
			double currentTime = synth.getCurrentTime();
			if(currentTime > lastSoundTime) {
				Sound sound = ChiptuneTracker.samples.get(sample).sounds[soundCursor];
				if(start) {
					play(sound, 0, soundCursor, sampleSpeed, currentTime);
					lastSoundTime = currentTime;
					start = false;
				}
				else if(sound != null) {
					lastSoundTime += sampleFrequency;
					play(sound, 0, soundCursor, sampleSpeed, lastSoundTime);
				}
				else {
					lastSoundTime += sampleFrequency;
					tickUICursor(lastSoundTime, soundCursor);
				}
				
				if(soundCursor < Sample.SIZE) {
					soundCursor++;
				}
				
				if(soundCursor == ChiptuneTracker.samples.get(sample).loopStop && soundCursor != ChiptuneTracker.samples.get(sample).loopStart) {
					soundCursor = ChiptuneTracker.samples.get(sample).loopStart;
				}
				
				if(soundCursor == Sample.SIZE) {
					stop(lastSoundTime + sampleFrequency);
				}
			}
		}
	}
	
	private void play(final Sound sound, final int chanel, final int position, int speed, double time) {
		final double frequency = Notes.getFrequency(sound.octave, sound.note);
		final double volume = (double) sound.volume / (double) VOLUME_MAX;
		double samplefrequency = 1 / ((double) speed / 2);
		
		final TimeStamp start = new TimeStamp(time);
		TimeStamp end = new TimeStamp(time + samplefrequency);
		
		getSynthesizer(chanel, sound.instrument).scheduleCommand(start, new ScheduledCommand() {
			@Override
			public void run() {
				voices[chanel * INSTRUMENTS + sound.instrument].noteOn(frequency, volume, voices[chanel * INSTRUMENTS + sound.instrument].getSynthesizer().createTimeStamp());
				UICursor = position;
			}
		});
		
		getSynthesizer(chanel, sound.instrument).scheduleCommand(end, new ScheduledCommand() {
			@Override
			public void run() {
				voices[chanel * INSTRUMENTS + sound.instrument].noteOff(voices[chanel * INSTRUMENTS + sound.instrument].getSynthesizer().createTimeStamp());
			}
		});
	}
	
	private void tickUICursor(double time, final int position) {
		final TimeStamp start = new TimeStamp(time);
		synth.scheduleCommand(start, new ScheduledCommand() {
			@Override
			public void run() {
				UICursor = position;
			}
		});
	}
	
	public int getSample() {
		return sample;
	}
	
	public void stop() {
		double time = synth.getCurrentTime();
		for(CustomCircuit circuit : voices) {
			circuit.noteOff(new TimeStamp(time));
			circuit.noteOff(new TimeStamp(lastSoundTime));
		}
	}
	
	private void stop(double time) {
		finish = true;
		final TimeStamp start = new TimeStamp(time);
		synth.scheduleCommand(start, new ScheduledCommand() {
			@Override
			public void run() {
				chanels.stopSample();
			}
		});
	}
	
	public int getSoundCursor() {
		return UICursor;
	}
}
