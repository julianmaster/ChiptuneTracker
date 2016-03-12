package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import ui.AsciiPanel;
import ui.AsciiSelectableTerminalButton;
import ui.AsciiTerminal;
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
	public static final Color INDIGO = new Color(61, 81, 181);
	public static final Color DEEP_ORANGE = new Color(255, 120, 8);
	
	public static final int TARGET_FPS = 60;
	public static final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
	
	public static CustomAsciiTerminal asciiTerminal;
	public static AsciiPanel asciiPanel;
	
	public static List<Sample> samples = new ArrayList<>();
	public static Chanel chanel = new Chanel();
	
	private View currentView;
	public static TrackerView trackerView;
	public static EditorView editorView;
	
	public ChiptuneTracker() {
		asciiTerminal = new CustomAsciiTerminal(TITLE, new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT), TILESET_FILE, CHARACTER_WIDTH, CHARACTER_HEIGHT);
		asciiPanel = asciiTerminal.getAsciiPanel();
		asciiPanel.setDefaultCharacterBackgroundColor(Color.DARK_GRAY);
		asciiPanel.setDefaultCharacterColor(Color.WHITE);
		
		trackerView = new TrackerView(this);
		editorView = new EditorView(this);
		changeView(trackerView);
		
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
			chanel.update();
			
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
