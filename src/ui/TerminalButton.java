package ui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

public class TerminalButton extends JComponent implements MouseListener {

	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private int x;
	private int y;
	
	protected Color mouseCurrentColor;
	protected Color mouseDefaultColor;
	protected Color mouseEnteredColor;
	
	public TerminalButton(String name, Color mouseDefaultColor, Color mouseEnteredColor, int x, int y, int charsetWidth, int charsetHeight) {
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
	}

	@Override
	public void mouseExited(MouseEvent e) {
		mouseCurrentColor = mouseDefaultColor;
	}
	
	public void paint(Terminal terminal) {
		terminal.writeString(x, y, name, mouseCurrentColor);
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	public void setMouseDefaultColor(Color mouseDefaultColor) {
		this.mouseDefaultColor = mouseDefaultColor;
	}
	
	public void setMouseEnteredColor(Color mouseEnteredColor) {
		this.mouseEnteredColor = mouseEnteredColor;
	}
}
