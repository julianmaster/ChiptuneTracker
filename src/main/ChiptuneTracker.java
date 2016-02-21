package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

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
	public static List<Sample> samples = new ArrayList<>();
	public static Chanel chanel = new Chanel();
	
	private View currentView;
	public TrackerView trackerView;
	public EditorView editorView;
	
	public ChiptuneTracker() {
		terminal = new Terminal(TITLE, new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT), TILESET_FILE, CHARACTER_WIDTH, CHARACTER_HEIGHT);
		terminal.setDefaultCharacterBackgroundColor(CustomColor.BLACK);
		terminal.setDefaultCharacterColor(CustomColor.WHITE);
		terminal.setDefaultCharacterBackgroundColor(Color.DARK_GRAY);
		
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
			boolean change = currentView.update(delta);
			chanel.update();
			
			// Paint
			if(change) {
				currentView.paint();
				ChiptuneTracker.terminal.repaint();
			}
			
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
		currentView.init();
	}

	public static void main(String[] args) {
		new ChiptuneTracker();
	}

}
