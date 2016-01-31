package main;

import java.awt.Color;
import java.awt.Dimension;

import ui.CustomColor;
import ui.Terminal;

public class ChiptuneTracker {
	public static final String TITLE = "ChiptuneTracker";
	public static final int WINDOW_WIDTH = 29;
	public static final int WINDOW_HEIGHT = 16;
	public static final String TILESET_FILE = "src/assets/wanderlust.png";
	public static final int CHARACTER_WIDTH = 12;
	public static final int CHARACTER_HEIGHT = 12;
	
	public static final int TARGET_FPS = 60;
	public static final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
	
	public static Terminal terminal;
	
	public ChiptuneTracker() {
		terminal = new Terminal(TITLE, new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT), TILESET_FILE, CHARACTER_WIDTH, CHARACTER_HEIGHT);
		terminal.setDefaultCharacterBackgroundColor(CustomColor.BLACK);
		terminal.setDefaultCharacterColor(CustomColor.WHITE);
		terminal.setDefaultCharacterBackgroundColor(Color.DARK_GRAY);
		
		Synthesizer synthesizer = new Synthesizer();
		synthesizer.run();
		
//		Chanel chanel1 = new Chanel();
//		
//		Sample sample = new Sample();
//		sample.speed = 16;
//		sample.sounds = new Sound[12];
//		sample.sounds[0] = new Sound(1, Note.C, Instrument.INSTRUMENT_1);
//		sample.sounds[1] = new Sound(1, Note.C, Instrument.INSTRUMENT_1);
//		sample.sounds[2] = new Sound(1, Note.C, Instrument.INSTRUMENT_1);
//		sample.sounds[3] = new Sound(1, Note.C, Instrument.INSTRUMENT_1);
//		sample.sounds[4] = new Sound(1, Note.D, Instrument.INSTRUMENT_2);
//		sample.sounds[5] = new Sound(1, Note.D, Instrument.INSTRUMENT_2);
//		sample.sounds[6] = new Sound(1, Note.E, Instrument.INSTRUMENT_2);
//		sample.sounds[7] = new Sound(1, Note.E, Instrument.INSTRUMENT_2);
//		sample.sounds[8] = new Sound(1, Note.F, Instrument.INSTRUMENT_1);
//		sample.sounds[9] = new Sound(1, Note.F, Instrument.INSTRUMENT_1);
//		sample.sounds[10] = new Sound(1, Note.F, Instrument.INSTRUMENT_1);
//		sample.sounds[11] = new Sound(1, Note.F, Instrument.INSTRUMENT_1);
//		
//		chanel1.play(sample);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ChiptuneTracker();
	}

}
