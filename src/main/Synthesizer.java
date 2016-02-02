package main;

import java.util.ArrayList;
import java.util.List;


public class Synthesizer {

	public List<Sample> samples = new ArrayList<>();
	private View currentView;
	public TrackerView trackerView;
	public EditorView editorView;
	
	public Synthesizer() {
		trackerView = new TrackerView(this);
		editorView = new EditorView(this);
		changeView(trackerView);
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
			
			// Paint
			ChiptuneTracker.terminal.clear();
			currentView.paint();
			ChiptuneTracker.terminal.repaint();
			
			try {
				long value = (lastLoopTime - System.nanoTime() + ChiptuneTracker.OPTIMAL_TIME) / 1000000;
//				System.out.println(value);
				Thread.sleep(value);
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
}
