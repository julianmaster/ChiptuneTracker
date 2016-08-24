package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import ui.AsciiPanel;
import ui.CustomAsciiTerminal;

// A regarder :
//  * Music Components in Java
//  * jMusic

public class ChiptuneTracker {
	public static final String TITLE = "ChiptuneTracker";
	public static final int WINDOW_WIDTH = 29;
	public static final int WINDOW_HEIGHT = 18;
	public static final String TILESET_FILE = "/assets/wanderlust.png";
	public static final String ICON_FILE = "/assets/icon.png";
	public static final int CHARACTER_WIDTH = 12;
	public static final int CHARACTER_HEIGHT = 12;
	public static final int TARGET_FPS = 60;
	public static final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
	
	private static ChiptuneTracker instance = new ChiptuneTracker();
	
	private CustomAsciiTerminal asciiTerminal;
	private AsciiPanel asciiPanel;
	
	private boolean initSampleView = true;
	private boolean initPatternView = true;
	
	private Data data = new Data();
	private DataManager dataManager;
	private boolean changeData = false;
	private Chanels chanels = new Chanels();
	
	private View currentView;
	private MenuView menuView;
	private SampleView sampleView;
	private PatternView patternView;
	
	private ChiptuneTracker() {
		asciiTerminal = new CustomAsciiTerminal(TITLE, new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT), TILESET_FILE, CHARACTER_WIDTH, CHARACTER_HEIGHT, ICON_FILE);
		asciiPanel = asciiTerminal.getAsciiPanel();
		asciiPanel.setDefaultCharacterBackgroundColor(Color.DARK_GRAY);
		asciiPanel.setDefaultCharacterColor(Color.WHITE);
		dataManager = new DataManager();
	}
	
	public void init() {
		menuView = new MenuView(this);
		sampleView = new SampleView(this);
		patternView = new PatternView(this);
		changeView(sampleView);
	}
	
	public void run() {
		long lastLoopTime = System.nanoTime();
		
		while(true) {
			long now = System.nanoTime();
			double updateLength = now - lastLoopTime;
			lastLoopTime = now;
			double delta = updateLength / ChiptuneTracker.OPTIMAL_TIME;
			
			// Screenshot
			KeyEvent event = asciiTerminal.getEvent();
			if(event != null) {
				if(event.getKeyCode() == KeyEvent.VK_F12) {
					try {
						BufferedImage image = new BufferedImage(WINDOW_WIDTH * CHARACTER_WIDTH, WINDOW_HEIGHT * CHARACTER_HEIGHT, BufferedImage.TYPE_INT_RGB);
						Graphics2D graphics = image.createGraphics();
						asciiPanel.paint(graphics);
						
						boolean save = false;
						int i = 0;
						do {
							File file = new File("screenshot-" + i + ".png");
							if(!file.exists()) {
								ImageIO.write(image, "PNG", file);
								save = true;
							}
							i++;
						} while(!save);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(ChiptuneTracker.getInstance().getAsciiTerminal(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
					finally {
						asciiTerminal.setEvent(null);
					}
				}
			}
			
			// Update
			currentView.update(delta);
			chanels.update();
			
			// Paint
			currentView.paint();
			asciiTerminal.repaint();
			
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
		asciiPanel.clear();
		currentView.init();
	}
	
	public static ChiptuneTracker getInstance() {
		return instance;
	}
	
	public CustomAsciiTerminal getAsciiTerminal() {
		return asciiTerminal;
	}
	
	public AsciiPanel getAsciiPanel() {
		return asciiPanel;
	}

	public boolean isInitPatternView() {
		return initPatternView;
	}
	
	public boolean isInitSampleView() {
		return initSampleView;
	}
	
	public void setInitPatternView(boolean initPatternView) {
		this.initPatternView = initPatternView;
	}
	
	public void setInitSampleView(boolean initSampleView) {
		this.initSampleView = initSampleView;
	}
	
	public Data getData() {
		return data;
	}
	
	public void setData(Data data) {
		this.data = data;
	}
	
	public DataManager getDataManager() {
		return dataManager;
	}
	
	public boolean isChangeData() {
		return changeData;
	}
	
	public void setChangeData(boolean changeData) {
		this.changeData = changeData;
	}
	
	public Chanels getChanels() {
		return chanels;
	}
	
	public MenuView getMenuView() {
		return menuView;
	}
	
	public SampleView getSampleView() {
		return sampleView;
	}
	
	public PatternView getPatternView() {
		return patternView;
	}
	
	public static void main(String[] args) {
		ChiptuneTracker chiptuneTracker = ChiptuneTracker.getInstance();
		chiptuneTracker.init();
//		chiptuneTracker.getDataManager().openFile(new File("./Legend of Zelda - Wind Waker - Outset Island.ct"));
		chiptuneTracker.run();
	}
}
