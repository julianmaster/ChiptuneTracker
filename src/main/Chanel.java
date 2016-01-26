package main;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.UnitGenerator;
import com.jsyn.unitgen.UnitVoice;
import com.softsynth.shared.time.TimeStamp;

public class Chanel implements Runnable {
	
	Synthesizer synth;
	UnitGenerator ugen;
	UnitVoice voice;
	LineOut lineOut;
	Sample sample;
	
	public Chanel(UnitGenerator ugen) {
		this.ugen = ugen;
		// Create a context for the synthesizer.
		synth = JSyn.createSynthesizer();
		
		// Add a tone generator.
//		synth.add( ugen = new TriangleOscillator() );
		synth.add( ugen);
//		synth.add( ugen = new SineOscillator() );
		
		voice = (UnitVoice) ugen;
		// Add an output mixer.
		synth.add( lineOut = new LineOut() );
		
		// Connect the oscillator to the left and right audio output.
		voice.getOutput().connect( 0, lineOut.input, 0 );
		voice.getOutput().connect( 0, lineOut.input, 1 );
	}
	
	public void play(Sample sample) {
		this.sample = sample;
		new Thread(this).start();
	}

	@Override
	public void run() {
		
//		A revoir avec PlayChords
		
//		// Start synthesizer using default stereo output at 44100 Hz.
//		synth.start();
//		
//		// Advance to a near future time so we have a clean start.
//		TimeStamp timeStamp = new TimeStamp(synth.getCurrentTime() + 0.5);
//		
//		double time = timeStamp.getTime();
//		
//		// We only need to start the LineOut. It will pull data from the
//		// oscillator.
//		synth.startUnit( lineOut, timeStamp );
//		
//		double duration = 1 / (sample.speed / 2);
////				double onTime = 1;
//		for(Sound sound : sample.sounds) {
////					synth.add(ugen = sound.instrument.unit);
////					voice = (UnitVoice) ugen;
//			voice.noteOn(sound.note.frequency, 0.5, timeStamp);
//			voice.noteOff( timeStamp.makeRelative( duration ) );
//			timeStamp = timeStamp.makeRelative( duration );
//		}		
//		
//		// Sleep while the song is being generated in the background thread.
//		try
//		{
//			System.out.println("Sleep while synthesizing.");
//			synth.sleepUntil( time + duration * sample.sounds.length);
//			System.out.println("Woke up...");
//		} catch( InterruptedException e )
//		{
//			e.printStackTrace();
//		}
//		
//		// Stop everything.
//		synth.stop();
	}
}
