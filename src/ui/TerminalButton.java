package ui;

import java.awt.Graphics;

import javax.swing.JComponent;

public class TerminalButton extends JComponent implements M{

	private Terminal terminal;
	private String name;
	
	public TerminalButton(Terminal terminal, String name, int x, int y, int charsetWidth, int charsetHeight) {
		this.terminal = terminal;
		this.name = name;
		setBounds(x*charsetWidth, y*charsetHeight, name.length()*charsetWidth, charsetHeight);
	}
}
