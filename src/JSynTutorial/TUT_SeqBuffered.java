package JSynTutorial;
import java.util.*;
import java.awt.*;
import java.applet.Applet;
import com.softsynth.jsyn.*;
import com.softsynth.jsyn.circuits.*;
import com.softsynth.jsyn.util.*;
import com.softsynth.jsyn.view102.*;

/** Demonstrate how event buffering and absolute sleep solve the timing problems.
 */
public class TUT_SeqBuffered extends Applet implements Tweakable
{
	TickerThread        thread1;
	TickerThread        thread2;
	boolean             addAdvanceFader = false;
	LabelledFader       advanceFader;
	int                 advanceTime; // ticks
	final static double DEFAULT_ADVANCE = 0.5; // seconds\
	boolean             go = false;

	/* Can be run as either an application or as an applet. */
	public static void main(String args[])
	{
/* DO: Change MyJSynProgram to match the name of your class. Must match file name! */
	   TUT_SeqBuffered  applet = new TUT_SeqBuffered();
	   applet.addAdvanceFader = true;
	   AppletFrame frame = new AppletFrame("Play Sequence using Event Buffer", applet);
	   frame.resize(600,120);
	   frame.show();
	   frame.test();
	}

 /*
  * Setup synthesis by overriding start() method.
  */
	public void start()  
	{
		try
		{

			Synth.startEngine(0);
			
			int startTime = Synth.getTickCount() + (int) Synth.getTickRate();
			int duration = (int) (Synth.getTickRate() / 10);
			
			thread1 = makeThread();
			thread2 = makeThread();

			int measure = 2*2*3*4*5;
			thread1.setup( startTime, measure, 2, 1.0 );
			thread2.setup( startTime, measure, 4, (16.0/3.0) );
			
			if( addAdvanceFader )
			{
				add( advanceFader = new LabelledFader( this, 2,
						"Advance Time", DEFAULT_ADVANCE, 0.0, 1.0) );
			}
			
			go = true;
			thread1.start();
			thread2.start();
			
	   } catch(SynthException e) {
		  SynthAlert.showError(this,e);
	   }
	}
	
	TickerThread makeThread()
	{
		return new TickerThread();
	}
/* called by LabelledFaders */
	public void tweak( int targetIndex, double val )
	{		
		switch( targetIndex )
		{
		case 1:
			advanceTime = (int) (Synth.getTickRate() * val);
			break;
		}
	}

  /*
   * Clean up synthesis by overriding stop() method.
   */
	public void stop()  
	{
	// Set flag so that run() loop will exit the next time around.
		go = false; // tell run() to exit peacefully
		System.out.println("Stopping engine" + this);
		Synth.stopEngine();
		System.out.println("Stopped" + this);
   }
	
	
/**************************************************************************/
class TickerThread extends Thread
{
	SynthEnvelope      envData;
	EnvelopePlayer     envPlayer;
	TriangleOscillator osc;
	LineOut            lineOut;
	int                startTime;
	int                measure;
	int                duration;
	int                notesPerMeasure;
	double             fundamental = 400.0;
	double             transpose;
	int                pitchIndex = 0;

	double series[] = { 1.0/1.0, 5.0/4.0, 4.0/3.0, 3.0/2.0 };
	
	public TickerThread()  
	{
		
	 // Create unit generators.
	 	osc = new TriangleOscillator()   ;
	 	envPlayer = new EnvelopePlayer();  // create an envelope player
	 	lineOut = new LineOut();

	 // control oscillator amplitude with envelope
	 	envPlayer.output.connect( osc.amplitude );
		
	 // define shape of envelope as an array of doubles
	 	double[] data =
	 	{
	 		0.005, 1.0, // Attack
	 		0.010, 0.0  // Decay
	 	};
	 	envData = new SynthEnvelope( data );

	 	osc.output.connect( 0, lineOut.input, 0 );
	 	osc.output.connect( 0, lineOut.input, 1 );
			
	 	osc.start();
	 	envPlayer.start();
	 	lineOut.start();
	}
	
	void setup( int startTime, int measure, int notesPerMeasure, double transpose )  
	{
		this.startTime = startTime;
		this.notesPerMeasure = notesPerMeasure;
		this.transpose = transpose;
		measure  = measure;
		duration = measure / notesPerMeasure;
	}

	double nextFrequency()
	{
		double frequency = transpose * fundamental * series[ pitchIndex++ ];
		if( (pitchIndex > notesPerMeasure) || (pitchIndex >= series.length) ) pitchIndex = 0;
		return frequency;
	}
	
	void bang( int time )
	{
		osc.frequency.set( nextFrequency() );
	// set envelopes amplitude which in turn controls oscillator amplitude
		envPlayer.amplitude.set( 0.5 );
		envPlayer.envelopePort.clear( time ); // clear the queue
		envPlayer.envelopePort.queue( time, envData );  // queue the envelope
	}
	
	void bang()
	{
		bang( Synth.getTickCount() );
	}

	public void playNotes()
	{
		advanceTime = (int) (Synth.getTickRate() * DEFAULT_ADVANCE);
	// try to start in sync
		Synth.sleepUntilTick( startTime  - advanceTime);
		int nextTime = startTime;
		
		while( go )
		{
	/* Play a note at the specified time. */
			bang( nextTime );
	/* Advance nextTime by fixed amount. */
			nextTime += duration;
	/* sleep until advanceTime BEFORE we have to play the next note */
			Synth.sleepUntilTick( nextTime  - advanceTime);
		}
	}
	
/* real-time task for thread */
	public void run()
	{
		try
		{
			playNotes();
		}
		catch( SynthException e )
		{
		// Just print message. Do not use Alert because we can easily get an exception
		// when we stop the thread.
			System.err.println("run() caught " + e );
		}
	}
	
}

}