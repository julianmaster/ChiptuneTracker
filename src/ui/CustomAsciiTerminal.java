package ui;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import main.ChiptuneTracker;

public class CustomAsciiTerminal extends AsciiTerminal {

	private KeyEvent event;
	
	public CustomAsciiTerminal(String title, Dimension dimension, String tilesetFile, int characterWidth, int characterHeight, int scale, String frameIconPath, boolean customWindow) {
		super(title, dimension, tilesetFile, characterWidth, characterHeight, scale, customWindow);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		try {
			this.setIconImage(ImageIO.read(getClass().getResource(frameIconPath)));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		// WindowAdapter
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					ChiptuneTracker.getInstance().getDataManager().exit();
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(ChiptuneTracker.getInstance().getAsciiTerminal(), exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		// KeyAdapter
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				event = e;
			}
		});
	}

	public KeyEvent getEvent() {
		return event;
	}
	
	public void setEvent(KeyEvent event) {
		this.event = event;
	}
}
