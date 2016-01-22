
/**
 * Modulate the frequency of one oscillator with another using JSyn.
 *
 * @author (C) 1998 Phil Burk, All Rights Reserved
 */

package JSynTutorial;  /* Put resulting class into tutorial package. */

import java.util.*;
import java.awt.*;
import java.applet.Applet;
import com.softsynth.jsyn.*;
import com.softsynth.jsyn.view102.*;


public class TUT_Vibrato extends Applet
{
/* Declare Synthesis Objects here */
	SineOscillator     modOsc;
	TriangleOscillator triOsc;
	AddUnit            freqAdder;
	LineOut            lineOut;
	
	PortFader          centerFader;
	PortFader          modFreqFader;
	PortFader          modRangeFader;
	SynthScope         scope;
	
/* Can be run as either an application or as an applet. */
	public static void main(String args[])
	{
/* DO: Change TUT_Vibrato to match the name of your class. Must match file name! */
	   TUT_Vibrato  applet = new TUT_Vibrato();
	   AppletFrame frame = new AppletFrame("Test JSyn", applet);
	   frame.resize(500,550);
	   frame.show();
 /* Begin test after frame opened so that DirectSound will use Java window. */
	   frame.test();
	}

 /*
  * Setup synthesis by overriding start() method.
  */
	public void start()  
	{
		/* Use GridBagLayout to get reasonably sized components. */
		GridBagLayout  gridbag  =  new  GridBagLayout();
		GridBagConstraints  constraint  =  new  GridBagConstraints();
		setLayout(gridbag);
		constraint.fill  =  GridBagConstraints.BOTH;
		constraint.weightx  =  1.0;

		try
		{
			Synth.startEngine(0);
 /* DO: Your setup code goes here. ******************/
 /* Create unit generators. */
			modOsc = new SineOscillator();
			triOsc = new TriangleOscillator();
			freqAdder    = new AddUnit();
			lineOut  = new LineOut();

/* Feed first oscillators through adder to offset center frequency. */
			modOsc.output.connect( freqAdder.inputA );
			freqAdder.output.connect( triOsc.frequency );
			
/* Connect oscillator to LineOut so we can hear it. */
			triOsc.output.connect( 0, lineOut.input, 0 );
			triOsc.output.connect( 0, lineOut.input, 1 );
			
/* Set up constraints for nice placements of faders and scope. */
			constraint.gridheight  =  1;  
			constraint.gridwidth  =  GridBagConstraints.REMAINDER; 
			constraint.weighty  =  0.0;

/* Create a fader to control Frequency. */
			add( centerFader = new PortFader( freqAdder.inputB,
					"Center Frequency", 330.0, 0.0, 500.0) );
			gridbag.setConstraints(centerFader,  constraint);
		
			add( modFreqFader = new PortFader( modOsc.frequency,
					"Modulation Frequency", 2.0, 0.0, 50.0) );
			gridbag.setConstraints(modFreqFader,  constraint);
			
			add( modRangeFader = new PortFader( modOsc.amplitude,
					"Modulation Depth", 0.0, 0.0, 100.0) );
			gridbag.setConstraints(modRangeFader,  constraint);

/* *****************************************/
/* Create an oscilloscope to show sine waveforms. */
			add( scope = new SynthScope() );
			scope.createProbe( triOsc.output, "triOsc", Color.green );
			scope.createProbe( freqAdder.output, "freqAdder", Color.red );
			scope.createProbe( modOsc.output, "modOsc", Color.yellow );
			scope.finish();

			constraint.gridheight  =  GridBagConstraints.RELATIVE;  
			constraint.weighty  =  1.0;
			gridbag.setConstraints(scope,  constraint);

/* Synchronize Java display to make buttons appear. */
			getParent().validate();
			getToolkit().sync();
			
/* Start units. */
			lineOut.start();
			freqAdder.start();
			modOsc.start();
			triOsc.start();
			
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
   
}