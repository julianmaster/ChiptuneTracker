package ui;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CustomAsciiTerminal extends AsciiTerminal implements KeyListener {

	private KeyEvent event;
	
	public CustomAsciiTerminal(String title, Dimension dimension, String tilesetFile, int characterWidth, int characterHeight) {
		super(title, dimension, tilesetFile, characterWidth, characterHeight);
		this.addKeyListener(this);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		event = e;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// Nothing
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// Nothing
	}
	
	public KeyEvent getEvent() {
		return event;
	}
	
	public void setEvent(KeyEvent event) {
		this.event = event;
	}
}
