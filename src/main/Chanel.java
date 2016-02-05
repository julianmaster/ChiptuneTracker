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
		
		add(new SineOscillator(), 0);
		add(new TriangleOscillator(), 1);
		
		allocator = new VoiceAllocator( voices );
		
//		Sample sample = new Sample();
// 		sample.speed = 16;
// 		sample.sounds = new Sound[12];
// 		sample.sounds[0] = new Sound(Note.C, 1, Instrument.INSTRUMENT_1, 1);
// 		sample.sounds[1] = new Sound(Note.C, 1, Instrument.INSTRUMENT_1, 1);
// 		sample.sounds[2] = new Sound(Note.C, 1, Instrument.INSTRUMENT_1, 1);
// 		sample.sounds[3] = new Sound(Note.C, 1, Instrument.INSTRUMENT_1, 1);
// 		sample.sounds[4] = new Sound(Note.D, 1, Instrument.INSTRUMENT_2, 1);
// 		sample.sounds[5] = new Sound(Note.D, 1, Instrument.INSTRUMENT_2, 1);
// 		sample.sounds[6] = new Sound(Note.E, 1, Instrument.INSTRUMENT_2, 1);
// 		sample.sounds[7] = new Sound(Note.E, 1, Instrument.INSTRUMENT_2, 1);
// 		sample.sounds[8] = new Sound(Note.F, 1, Instrument.INSTRUMENT_1, 1);
// 		sample.sounds[9] = new Sound(Note.F, 1, Instrument.INSTRUMENT_1, 1);
// 		sample.sounds[10] = new Sound(Note.F, 1, Instrument.INSTRUMENT_1, 1);
// 		sample.sounds[11] = new Sound(Note.F, 1, Instrument.INSTRUMENT_1, 1);
// 		
// 		this.play(sample);
	}
	
	public void add(UnitOscillator voice, int i) {
		synth.add( voice );
		voice.getOutput().connect( 0, lineOut.input, 0 );
		voice.getOutput().connect( 0, lineOut.input, 1 );
		voices[i] = voice;
		voice.amplitude.set(0);
	}
	
	public void play(Sample sample) {
		this.sample = sample;
		run();
	}

	public void run() {
		
		// Start synthesizer using default stereo output at 44100 Hz.
		synth.start();
		// We only need to start the LineOut. It will pull data from the
		// voices.
		lineOut.start();

		// Get synthesizer time in seconds.
		double timeNow = synth.getCurrentTime();

		// Advance to a near future time so we have a clean start.
		double time = timeNow + 1.0;
		
		double duration = 1 / ((double)sample.speed / 2);
		for(Sound sound : sample.sounds) {
			double frequency = Notes.getFrequency(sound.octave, sound.note);
			allocator.noteOn(sound.instrument.number, frequency, 0.5, new TimeStamp(time));
			time += duration;
			allocator.noteOff(sound.instrument.number, new TimeStamp(time));
		}
		
		// Sleep while the song is being generated in the background thread.
		try
		{
			System.out.println("Sleep while synthesizing.");
			synth.sleepUntil( time + duration * sample.sounds.length);
			System.out.println("Woke up...");
			// Stop everything.
			synth.stop();
		} catch( InterruptedException e )
		{
			e.printStackTrace();
		}
	}
}
