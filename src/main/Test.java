/**
 * Mix two sine wave oscillators using JSyn.
 *
 * @author (C) 1998 Phil Burk, All Rights Reserved
 */

package main;  /* Put resulting class into tutorial package. */

import java.util.*;
import java.awt.*;
import java.applet.Applet;
import com.softsynth.jsyn.*;
import com.softsynth.jsyn.view102.*;

public class Test extends Applet
{
/* Declare Synthesis Objects here */
	SineOscillator     sineOsc1;
	SineOscillator     sineOsc2;
	AddUnit            mixer;
	LineOut            lineOut;
	
	PortFader          freqFader1;
	PortFader          ampFader1;
	PortFader          freqFader2;
	PortFader          ampFader2;
	SynthScope         scope;
	
/* Can be run as either an application or as an applet. */
	public static void main(String args[])
	{
/* DO: Change TUT_SineMix to match the name of your class. Must match file name! */
	   Test  applet = new Test();
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
			sineOsc1 = new SineOscillator();
			sineOsc2 = new SineOscillator();
			mixer    = new AddUnit();
			lineOut  = new LineOut();

/* Feed both oscillators to the mixer to be added together. */
			sineOsc1.output.connect( mixer.inputA );
			sineOsc2.output.connect( mixer.inputB );

/* Connect oscillator to LineOut so we can hear it. */
			mixer.output.connect( 0, lineOut.input, 0 );
//			mixer.output.connect( 0, lineOut.input, 1 );

/* Start amplitudes at zero. */
			sineOsc1.amplitude.set( 0.0 );
			sineOsc2.amplitude.set( 0.0 );
			
/* Start units. */
			lineOut.start();
			mixer.start();
			sineOsc1.start();
			sineOsc2.start();
			
/* Set up constraints for nice placements of faders and scope. */
			constraint.gridheight  =  1;  
			constraint.gridwidth  =  GridBagConstraints.REMAINDER; 
			constraint.weighty  =  0.0;

/* Create a fader to control Frequency. */
			add( freqFader1 = new PortFader( sineOsc1.frequency, 440.0, 0.0, 500.0) );
			gridbag.setConstraints(freqFader1,  constraint);
/* Create a fader to control Amplitude. */
			add( ampFader1 = new PortFader( sineOsc1.amplitude, 0.0, 0.0, 1.0) );
			gridbag.setConstraints(ampFader1,  constraint);
			
/* make similar faders for oscillator 2. */
			add( freqFader2 = new PortFader( sineOsc2.frequency, 330.0, 0.0, 500.0) );
			gridbag.setConstraints(freqFader2,  constraint);
			add( ampFader2 = new PortFader( sineOsc2.amplitude, 0.0, 0.0, 1.0) );
			gridbag.setConstraints(ampFader2,  constraint);

/* *****************************************/
/* Create an oscilloscope to show sine waveforms. */
			add( scope = new SynthScope() );
			scope.createProbe( sineOsc1.output, "Sine 1", Color.red );
			scope.createProbe( sineOsc2.output, "Sine 2", Color.green );
			scope.createProbe( mixer.output, "Mixed", Color.yellow );
			scope.finish();

			constraint.gridheight  =  GridBagConstraints.RELATIVE;  
			constraint.weighty  =  1.0;
			gridbag.setConstraints(scope,  constraint);

/* Synchronize Java display to make buttons appear. */
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
   
}