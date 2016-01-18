package ui;

import java.awt.Color;

/**
 *
 * @author Julien Maitre
 */
public class TerminalDataCell {
    public char data;
    public Color dataColor;
    public Color backgroundColor;

    public TerminalDataCell() {
        this.data = 0;
        this.dataColor = Color.WHITE;
        this.backgroundColor = Color.BLACK;
    }

    public TerminalDataCell(char data, Color dataColor, Color backgroundColor) {
        this.data = data;
        this.dataColor = dataColor;
        this.backgroundColor = backgroundColor;
    }
}
