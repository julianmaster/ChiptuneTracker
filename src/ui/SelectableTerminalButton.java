package ui;

import java.awt.Color;
import java.awt.event.MouseEvent;

public class SelectableTerminalButton extends TerminalButton {
	
	private static final long serialVersionUID = 1L;
	
	private boolean select = false;
	private Color mouseSelectColor;

	public SelectableTerminalButton(String name, Color mouseDefaultColor,
			Color mouseEnteredColor, Color mouseSelectColor, int x, int y, int charsetWidth,
			int charsetHeight) {
		super(name, mouseDefaultColor, mouseEnteredColor, x, y, charsetWidth,
				charsetHeight);
		this.mouseSelectColor = mouseSelectColor;
	}

	public void select(boolean select) {
		this.select = select;
		changeColor();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		changeColor();
	}
	
	private void changeColor() {
		if(select) {
			mouseCurrentColor = mouseSelectColor;
		}
		else {
			mouseCurrentColor = mouseDefaultColor;
		}
	}
}
