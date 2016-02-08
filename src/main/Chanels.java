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
	
	private boolean play = false;
	
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
		
		synth.start();
		lineOut.start();
	}
	
	public void add(int position, UnitOscillator voice) {
		synth.add(voice);
		voice.getOutput().connect(0, lineOut.input, 0);
		voice.getOutput().connect(0, lineOut.input, 1);
		voices[position] = voice;
		voice.amplitude.set(0);
	}
	
	public void play(Sound sound, int speed) {
		double time = synth.getCurrentTime();
		double duration = 1 / ((double)speed / 2);
		
		double frequency = Notes.getFrequency(sound.octave, sound.note);
		double volume = (double)sound.volume / (double)VOLUME_MAX;
		allocator.noteOn(sound.instrument.number, frequency, volume, new TimeStamp(time));
		time += duration;
		allocator.noteOff(sound.instrument.number, new TimeStamp(time));
		play = true;
	}
	
	public void play(Sample sample) {
		double time = synth.getCurrentTime();
		double duration = 1 / ((double)sample.speed / 2);
		
		for(Sound sound : sample.sounds) {
			if(sound != null) {
				double frequency = Notes.getFrequency(sound.octave, sound.note);
				double volume = (double)sound.volume / (double)VOLUME_MAX;
				allocator.noteOn(sound.instrument.number, frequency, volume, new TimeStamp(time));
				time += duration;
				allocator.noteOff(sound.instrument.number, new TimeStamp(time));
			}
			else {
				time += duration;
			}
		}
	}
	
	public void update(boolean stop) {
		if(stop) {
			double time = synth.getCurrentTime();
			allocator.allNotesOff(new TimeStamp(time));
		}
	}
	
	public void setPlay(boolean play) {
		this.play = play;
	}
	
	public boolean isPlay() {
		return play;
	}
}
