package main;

import java.util.Timer;
import java.util.TimerTask;

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
	public static final int CHANELS = 1;
	public static final int INSTRUMENTS = 8;
	public static final int VOLUME_MAX = 7;
	
	private Synthesizer synth;
	private CustomCircuit[] voices;
	private LineOut lineOut;
	
	private Sample samplePlay;
	private double sampleFrequency;
	private double lastSoundTime;
	private boolean play = false;
	private int soundCursor;
	
	private int UICursor;
	private TimerTask cursorTask;
	private Timer timer;
	
	public Chanel() {
		synth = JSyn.createSynthesizer();
		lineOut = new LineOut();
		
		synth.add(lineOut);
		
		voices = new CustomCircuit[CHANELS * INSTRUMENTS];
		
		for(int i = 0; i < CHANELS; i++) {
			add(i * INSTRUMENTS, new OscillatorCircuit(new SineOscillator()));
			FunctionOscillator sineSawtoothOscillator = new FunctionOscillator();
			sineSawtoothOscillator.function.set(new SineSawtoothFunction());
			add(i * INSTRUMENTS + 1, new OscillatorCircuit(sineSawtoothOscillator));
			add(i * INSTRUMENTS + 2, new OscillatorCircuit(new SawtoothOscillatorDPW()));
			add(i * INSTRUMENTS + 3, new OscillatorCircuit(new SquareOscillatorBL()));
			add(i * INSTRUMENTS + 4, new OscillatorCircuit(new DemiSquareOscillator()));
			FunctionOscillator mountainOscillator = new FunctionOscillator();
			mountainOscillator.function.set(new MoutainFunction());
			add(i * INSTRUMENTS + 5, new OscillatorCircuit(mountainOscillator));
			add(i * INSTRUMENTS + 6, new WhiteNoiseCircuit(new WhiteNoise()));
			add(i * INSTRUMENTS + 7, new OscillatorCircuit(new TriangleOscillator()));
		}
		
		synth.start();
		lineOut.start();
		
		timer = new Timer();
	}
	
	public void add(int position, CustomCircuit circuit) {
		synth.add(circuit);
		circuit.getOutput().connect(0, lineOut.input, 0);
		circuit.getOutput().connect(0, lineOut.input, 1);
		voices[position] = circuit;
	}
	
	public Synthesizer getSynthesizer(int chanel, int instrument) {
		return voices[chanel * INSTRUMENTS + instrument].getSynthesizer();
	}
	
	public void play(Sound sound) {
		double time = synth.getCurrentTime();
		play(sound, 0, 16, time);
	}
	
	public void play(Sample sample) {
		samplePlay = sample;
		lastSoundTime = synth.getCurrentTime();
		sampleFrequency = 1 / ((double)sample.speed / 2);
		soundCursor = 0;
		play = true;
		if(samplePlay.sounds[soundCursor] != null) {
			play(samplePlay.sounds[soundCursor], 0, sample.speed, lastSoundTime);
		}

		// Timer UI cursor
		UICursor = 0;
		cursorTask = new TimerTask() {
			@Override
			public void run() {
				if(UICursor <= Sample.SIZE) {
					UICursor++;
				}
				else {
					stop();
				}
			}
		};
		timer.scheduleAtFixedRate(cursorTask, (long)(sampleFrequency*1000), (long)(sampleFrequency*1000));
	}
	
	public void play(final Sound sound, final int chanel, int speed, double time) {
		final double frequency = Notes.getFrequency(sound.octave, sound.note);
		final double volume = (double) sound.volume / (double) VOLUME_MAX;
		double samplefrequency = 1 / ((double) speed / 2);
		System.out.println(sound.instrument);
		
		final TimeStamp start = new TimeStamp(time);
		TimeStamp end = new TimeStamp(time + samplefrequency);
		
		getSynthesizer(chanel, sound.instrument).scheduleCommand(start, new ScheduledCommand() {
			@Override
			public void run() {
				voices[chanel * INSTRUMENTS + sound.instrument].noteOn(frequency, volume, voices[chanel * INSTRUMENTS + sound.instrument].getSynthesizer().createTimeStamp());
			}
		});
		
		getSynthesizer(chanel, sound.instrument).scheduleCommand(end, new ScheduledCommand() {
			@Override
			public void run() {
				voices[chanel * INSTRUMENTS + sound.instrument].noteOff(voices[chanel * INSTRUMENTS + sound.instrument].getSynthesizer().createTimeStamp());
			}
		});
	}
	
	public void update() {
		if(play) {
			double currentTime = synth.getCurrentTime();
			if(currentTime > lastSoundTime) {
				if(soundCursor < Sample.SIZE - 1) {
					soundCursor++;
				}
				else {
					return;
				}
				
				Sound sound = samplePlay.sounds[soundCursor];
				if(sound != null) {
					lastSoundTime += sampleFrequency;
					play(sound, 0, samplePlay.speed, lastSoundTime);
				}
				else {
					lastSoundTime += sampleFrequency;
				}
			}
		}
	}
	
	public boolean isPlay() {
		return play;
	}
	
	public void stop() {
		play = false;
		cursorTask.cancel();
		double time = synth.getCurrentTime();
		for(CustomCircuit circuit : voices) {
			circuit.noteOff(new TimeStamp(time));
			circuit.noteOff(new TimeStamp(lastSoundTime));
		}
	}
	
	public int getSoundCursor() {
		return UICursor;
	}
}
