package main;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.UnitOscillator;
import com.jsyn.unitgen.UnitVoice;
import com.jsyn.util.VoiceAllocator;

public class Chanels {
	public static final int CHANELS = 4;
	public static final int INSTRUMENTS = 2;
	
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
			
		}
	}
	
	public void add(int position, UnitOscillator voice) {
		synth.add(voice);
		voice.getOutput().connect(0, lineOut.input, 0);
		voice.getOutput().connect(0, lineOut.input, 1);
		voices[position] = voice;
		voice.amplitude.set(0);
	}
}
