package main;

import java.awt.Color;
import java.awt.Dimension;

import ui.AsciiPanel;
import ui.CustomAsciiTerminal;

// A regarder :
//  * Music Components in Java
//  * jMusic

public class ChiptuneTracker {
	public static final String TITLE = "ChiptuneTracker";
	public static final int WINDOW_WIDTH = 29;
	public static final int WINDOW_HEIGHT = 18;
	public static final String TILESET_FILE = "src/assets/wanderlust.png";
	public static final int CHARACTER_WIDTH = 12;
	public static final int CHARACTER_HEIGHT = 12;
	
	public static final int TARGET_FPS = 60;
	public static final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
	
	public static CustomAsciiTerminal asciiTerminal;
	public static AsciiPanel asciiPanel;
	
	public static boolean initSampleView = true;
	public static boolean initPatternView = true;
	
	public static Data data = new Data();
	public static DataManager dataManager;
	public static boolean changeData = true;
	public static Chanels chanels = new Chanels();
	
	private View currentView;
	public static MenuView menuView;
	public static SampleView sampleView;
	public static PatternView patternView;
	
	public ChiptuneTracker() {
		asciiTerminal = new CustomAsciiTerminal(TITLE, new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT), TILESET_FILE, CHARACTER_WIDTH, CHARACTER_HEIGHT);
		asciiPanel = asciiTerminal.getAsciiPanel();
		asciiPanel.setDefaultCharacterBackgroundColor(Color.DARK_GRAY);
		asciiPanel.setDefaultCharacterColor(Color.WHITE);
		
		dataManager = new DataManager(this);
		
		menuView = new MenuView(this);
		sampleView = new SampleView(this);
		patternView = new PatternView(this);
		changeView(sampleView);
		
		run();
	}
	
	public void run() {
		long lastLoopTime = System.nanoTime();
		
		while(true) {
			long now = System.nanoTime();
			double updateLength = now - lastLoopTime;
			lastLoopTime = now;
			double delta = updateLength / ChiptuneTracker.OPTIMAL_TIME;
			
			// Update
			currentView.update(delta);
			chanels.update();
			
			// Paint
			currentView.paint();
			ChiptuneTracker.asciiTerminal.repaint();
			
			try {
				long value = (lastLoopTime - System.nanoTime() + ChiptuneTracker.OPTIMAL_TIME) / 1000000;
				if(value > 0) {
					Thread.sleep(value);					
				}
				else {
					Thread.sleep(5);
				}
			} catch (InterruptedException e) {
			}
		}
	}
	
	public void changeView(View nextView) {
		if(currentView != null) {
			currentView.quit();
		}
		currentView = nextView;
		ChiptuneTracker.asciiPanel.clear();
		currentView.init();
	}

	public static void main(String[] args) {
		new ChiptuneTracker();
	}

}
