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
import com.jsyn.util.VoiceAllocator;
import com.softsynth.shared.time.TimeStamp;

public class Chanel {
	public static final int CHANELS = 4;
	public static final int INSTRUMENTS = 6;
	public static final int VOLUME_MAX = 7;
	
	private Synthesizer synth;
	private VoiceAllocator allocator;
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
			add(i * INSTRUMENTS, new CustomCircuit(new SineOscillator()));
			FunctionOscillator sineSawtoothOscillator = new FunctionOscillator();
			sineSawtoothOscillator.function.set(new SineSawtoothFunction());
			add(i * INSTRUMENTS + 1, new CustomCircuit(sineSawtoothOscillator));
			add(i * INSTRUMENTS + 2, new CustomCircuit(new SawtoothOscillatorDPW()));
			add(i * INSTRUMENTS + 3, new CustomCircuit(new SquareOscillatorBL()));
			add(i * INSTRUMENTS + 4, new CustomCircuit(new DemiSquareOscillator()));
			FunctionOscillator mountainOscillator = new FunctionOscillator();
			sineSawtoothOscillator.function.set(new MoutainFunction());
			add(i * INSTRUMENTS + 5, new CustomCircuit(mountainOscillator));
//			add(i * INSTRUMENTS + 6, new CustomCircuit(new WhiteNoise()));
//			add(i * INSTRUMENTS + 7, new CustomCircuit(new TriangleOscillator()));
		}
		allocator = new VoiceAllocator(voices);
		
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
	
	public void play(Sound sound, int chanel, int speed, double time) {
		double frequency = Notes.getFrequency(sound.octave, sound.note);
		double volume = (double) sound.volume / (double) VOLUME_MAX;
		double samplefrequency = 1 / ((double) speed / 2);
		allocator.noteOn(chanel* INSTRUMENTS + sound.instrument, frequency, volume, new TimeStamp(time));
		double endTime = time + samplefrequency;
		allocator.noteOff(chanel * INSTRUMENTS + sound.instrument, new TimeStamp(endTime));
	}
	
	public boolean isPlay() {
		return play;
	}
	
	public void stop() {
		play = false;
		cursorTask.cancel();
		double time = synth.getCurrentTime();
		allocator.allNotesOff(new TimeStamp(time));
		allocator.allNotesOff(new TimeStamp(lastSoundTime));
	}
	
	public int getSoundCursor() {
		return UICursor;
	}
	
	
}
