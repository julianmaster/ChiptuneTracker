package ui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

public class TerminalButton extends JComponent implements MouseListener {

	private static final long serialVersionUID = 1L;
	
	private Terminal terminal;
	private String name;
	
	private int x;
	private int y;
	
	private Color mouseCurrentColor;
	private Color mouseDefaultColor;
	private Color mouseEnteredColor;
	
	public TerminalButton(Terminal terminal, String name, Color mouseDefaultColor, Color mouseEnteredColor, int x, int y, int charsetWidth, int charsetHeight) {
		this.terminal = terminal;
		this.name = name;
		this.x = x;
		this.y = y;
		this.mouseCurrentColor = mouseDefaultColor;
		this.mouseDefaultColor = mouseDefaultColor;
		this.mouseEnteredColor = mouseEnteredColor;
		setBounds(x*charsetWidth, y*charsetHeight, name.length()*charsetWidth, charsetHeight);
		this.addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// nothing
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// nothing
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// nothing
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		mouseCurrentColor = mouseEnteredColor;
		this.paint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		mouseCurrentColor = mouseDefaultColor;
		this.paint();
	}
	
	public void paint() {
		terminal.writeString(x, y, name, mouseCurrentColor);
	}
}
