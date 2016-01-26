package main;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.TriangleOscillator;
import com.jsyn.unitgen.UnitGenerator;
import com.jsyn.unitgen.UnitVoice;
import com.jsyn.util.VoiceAllocator;
import com.softsynth.shared.time.TimeStamp;

public class Chanel implements Runnable {
	
	private Synthesizer synth;
	private VoiceAllocator allocator;
	private UnitVoice[] voices;
	private LineOut lineOut;
	private Sample sample;
	
	public Chanel() {
		// Create a context for the synthesizer.
		synth = JSyn.createSynthesizer();
		
		// Add an output mixer.
		synth.add( lineOut = new LineOut() );
		
		voices = new UnitVoice[2];
		SineOscillator sinUnit;
		addGenerator(sinUnit = new SineOscillator());
		addVoice(sinUnit, 0);
		
		TriangleOscillator triUnit;
		addGenerator(triUnit = new TriangleOscillator());
		addVoice(triUnit, 0);
		
		allocator = new VoiceAllocator( voices );
	}
	
	private void addGenerator(UnitGenerator gen) {
		synth.add(gen);
	}
	
	private void addVoice(UnitVoice voice, int index) {
		voice.getOutput().connect(0, lineOut.input, 0);
		voice.getOutput().connect(0, lineOut.input, 1);
		voices[index] = voice;
	}
	
	public void play(Sample sample) {
		this.sample = sample;
		new Thread(this).start();
	}

	@Override
	public void run() {
		
//		A revoir avec PlayChords
		
		// Start synthesizer using default stereo output at 44100 Hz.
		synth.start();
		// We only need to start the LineOut. It will pull data from the
		// voices.
		lineOut.start();

		// Get synthesizer time in seconds.
		double timeNow = synth.getCurrentTime();

		// Advance to a near future time so we have a clean start.
		double time = timeNow + 1.0;
		
		double duration = 1 / (sample.speed / 2);
		for(Sound sound : sample.sounds) {
			allocator.noteOn(sound.instrument.number, sound.note.frequency, 0.5, new TimeStamp(time));
			time += duration;
			allocator.noteOff(sound.instrument.number, new TimeStamp(time));
		}
		
		// Sleep while the song is being generated in the background thread.
		try
		{
			System.out.println("Sleep while synthesizing.");
			synth.sleepUntil( time + duration * sample.sounds.length);
			System.out.println("Woke up...");
		} catch( InterruptedException e )
		{
			e.printStackTrace();
		}
		
		// Stop everything.
		synth.stop();
	}
}
