package main;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.FunctionOscillator;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SawtoothOscillatorDPW;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.SquareOscillatorBL;
import com.jsyn.unitgen.TriangleOscillator;
import com.jsyn.util.VoiceAllocator;
import com.softsynth.shared.time.TimeStamp;

public class Chanel {
	public static final int CHANELS = 4;
	public static final int INSTRUMENTS = 4;
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
//			add(i * INSTRUMENTS + 4, new CustomCircuit());
//			add(i * INSTRUMENTS + 5, new CustomCircuit());
//			add(i * INSTRUMENTS + 6, new CustomCircuit(new WhiteNoise()));
//			add(i * INSTRUMENTS + 7, new CustomCircuit(new TriangleOscillator()));
		}
		
		allocator = new VoiceAllocator(voices);
		
		synth.start();
		lineOut.start();
	}
	
	public void add(int position, CustomCircuit circuit) {
		synth.add(circuit);
		circuit.getOutput().connect(0, lineOut.input, 0);
		circuit.getOutput().connect(0, lineOut.input, 1);
		voices[position] = circuit;
	}
	
	public void play(Sound sound, int speed) {
		double time = synth.getCurrentTime();
		double duration = 1 / ((double)speed / 2);
		
		double frequency = Notes.getFrequency(sound.octave, sound.note);
		double volume = (double)sound.volume / (double)VOLUME_MAX;
		allocator.noteOn(sound.instrument, frequency, volume, new TimeStamp(time));
		time += duration;
		allocator.noteOff(sound.instrument, new TimeStamp(time));
	}
	
	public void play(Sample sample) {
		samplePlay = sample; 
		lastSoundTime = synth.getCurrentTime();
		sampleFrequency = 1 / ((double)sample.speed / 2);
		soundCursor = 0;
		play = true;
	}
	
	public void update() {
		if(play) {
			double currentTime = synth.getCurrentTime();
			if(currentTime > lastSoundTime) {
				Sound sound = samplePlay.sounds[soundCursor];
				if(sound != null) {
					double frequency = Notes.getFrequency(sound.octave, sound.note);
					double volume = (double)sound.volume / (double) VOLUME_MAX;
					lastSoundTime += sampleFrequency;
					allocator.noteOn(sound.instrument, frequency, volume, new TimeStamp(lastSoundTime));
					double endSoundTime = lastSoundTime + sampleFrequency;
					allocator.noteOff(sound.instrument, new TimeStamp(endSoundTime));
				}
				else {
					lastSoundTime += sampleFrequency;
				}
				
				
				soundCursor++;
				if(soundCursor >= Sample.SIZE) {
					play = false;
				}
			}
		}
	}
	
	public boolean isPlay() {
		return play;
	}
	
	public void stop() {
		play = false;
		double time = synth.getCurrentTime();
		allocator.allNotesOff(new TimeStamp(time));
	}
}
