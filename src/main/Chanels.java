package main;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.TriangleOscillator;
import com.jsyn.unitgen.UnitOscillator;
import com.jsyn.unitgen.UnitVoice;
import com.jsyn.util.VoiceAllocator;
import com.softsynth.shared.time.TimeStamp;

public class Chanels {
	public static final int CHANELS = 4;
	public static final int INSTRUMENTS = 2;
	public static final int VOLUME_MAX = 7;
	
	private Synthesizer synth;
	private VoiceAllocator allocator;
	private UnitVoice[] voices;
	private LineOut lineOut;
	
	public Chanels() {
		synth = JSyn.createSynthesizer();
		lineOut = new LineOut();
		
		synth.add(lineOut);
		
		voices = new UnitVoice[CHANELS * INSTRUMENTS];
		
		for(int i = 0; i < CHANELS; i++) {
			add(i * INSTRUMENTS, new SineOscillator());
			add(i * INSTRUMENTS + 1, new TriangleOscillator());
		}
		
		allocator = new VoiceAllocator(voices);
	}
	
	public void add(int position, UnitOscillator voice) {
		synth.add(voice);
		voice.getOutput().connect(0, lineOut.input, 0);
		voice.getOutput().connect(0, lineOut.input, 1);
		voices[position] = voice;
		voice.amplitude.set(0);
	}
	
	public void play(Sound sound, int speed) {
		synth.start();
		lineOut.start();
		
		double time = synth.getCurrentTime() + 0.2;
		double duration = 1 / (speed / 2);
		
		double frequency = Notes.getFrequency(sound.octave, sound.note);
		double volume = sound.volume / VOLUME_MAX;
		allocator.noteOn(sound.instrument.number, frequency, volume, new TimeStamp(time));
		time += duration;
		allocator.noteOff(sound.instrument.number, new TimeStamp(time));
		
		synth.stop();
	}
	
	public void play(Sample sample) {
		
	}
}
