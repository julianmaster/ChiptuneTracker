package JSynTutorial;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import javax.swing.JComponent;

import ui.Terminal;

public abstract class WindowElement extends JComponent implements MouseMotionListener, MouseListener, Observable {
    protected int m_positionX;
    protected int m_positionY;
    protected int m_width;
    protected int m_height;
    protected String m_text;
    protected Terminal m_asciiPanel;
    private List<Observer> m_observers = new ArrayList<>();

    public WindowElement(Terminal asciiPanel, String text, int positionX, int positionY) {
        super();    
        this.m_asciiPanel = asciiPanel;
        this.m_text = text;
        this.m_positionX = positionX;
        this.m_positionY = positionY;
        this.m_width = m_text.length();
        this.m_height = 1;

        this.setBounds(m_positionX*ASCKingdom.CHARACTERWIDTH, m_positionY*ASCKingdom.CHARACTERHEIGHT, m_width*ASCKingdom.CHARACTERWIDTH, m_height*ASCKingdom.CHARACTERHEIGHT);
        
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        m_asciiPanel.add(this);
    }

    public int getPositionX() {
        return m_positionX;
    }

    public void setPositionX(int positionX) {
        this.m_positionX = positionX;
    }

    public int getPositionY() {
        return m_positionY;
    }

    public void setPositionY(int positionY) {
        this.m_positionY = positionY;
    }

    public int getWidth() {
        return m_width;
    }

    public int getHeight() {
        return m_height;
    }
    
    public abstract void paint();

    @Override
    public void addObserver(Observer observer) {
        m_observers.add(observer);
    }

    @Override
    public void updateObservers() {
        for(Observer o : m_observers){
            o.updateObserver(this);
        }
    }

    @Override
    public void deleteObservers() {
        m_observers.clear();
    }
}
