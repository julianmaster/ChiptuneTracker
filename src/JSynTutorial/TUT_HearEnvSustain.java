/**
 * Use an envelope to control the amplitude of a sine wave.
 * Play attack-sustain-release parts of envelope.
 * This version uses the obsolete but universal 1.0.2 AWT API for user interfaces.
 *
 * @author (C) 2000 Phil Burk, All Rights Reserved
 */

package JSynTutorial;  /* Put resulting class into tutorial package. */

import java.util.*;
import java.awt.*;
import java.applet.Applet;
import com.softsynth.jsyn.*;

public class TUT_HearEnvSustain extends Applet
{
/* DO: Declare Synthesis Objects here */
	SineOscillator     sineOsc;
	SynthEnvelope      envData;
	EnvelopePlayer     envPlayer;
	LineOut            lineOut;
	
	Button             attackButton;
	Button             releaseButton;
	
/* Can be run as either an application or as an applet. */
	public static void main(String args[])
	{
	   TUT_HearEnvSustain  applet = new TUT_HearEnvSustain();
	   AppletFrame frame = new AppletFrame("Hear Envelope Sustain", applet);
	   frame.resize(300,100);
	   frame.show();
 /* Begin test after frame opened so that DirectSound will use Java window. */
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
		// Create unit generators.
			sineOsc = new SineOscillator()   ;
			envPlayer = new EnvelopePlayer();  // create an envelope player
			lineOut = new LineOut();

		// Connect oscillator to LineOut so we can hear it.
			sineOsc.output.connect( 0, lineOut.input, 0 );
			sineOsc.output.connect( 0, lineOut.input, 1 );
			
		// control sine wave amplitude with envelope output
			envPlayer.output.connect( sineOsc.amplitude );
		
		// define shape of envelope as an array of doubles
			double[] data =
			{
			    0.1, 1.0,  // Take 0.1 seconds to go to value 1.0. "Attack"
			    0.3, 0.0   // Take 0.3 seconds to drop to 0.0.  "Release"
			};
			envData = new SynthEnvelope( data );
			
		// Create buttons.
			add( attackButton = new Button("Attack") );
			add( releaseButton = new Button("Release") );
			
		// Start units.
			lineOut.start();
			sineOsc.start();
			envPlayer.start();

		// Synchronize Java display to make buttons appear.
			getParent().validate();
			getToolkit().sync();
			
	   } catch(SynthException e) {
		  SynthAlert.showError(this,e);
	   }
	}

/*
 * Clean up synthesis by overriding stop() method.
 */
	public void stop()  
	{
	   try
	   {
 /* Your cleanup code goes here. */
		  removeAll(); // remove components from Applet panel.
		  Synth.stopEngine();
	   } catch(SynthException e) {
		  SynthAlert.showError(this,e);
	   }
	}
 /* Process button hits. */
   	public boolean action(Event evt, Object what)
   	{
		if( evt.target == attackButton )
   		{
			envPlayer.envelopePort.clear(); // clear the queue
			envPlayer.envelopePort.queue( envData, 0, 1 );  // queue attack
			return true;
   		}
		else if( evt.target == releaseButton )
   		{
			envPlayer.envelopePort.queue( envData, 1, 1 );  // queue release
			return true;
   		}
   		return false;
	}
}