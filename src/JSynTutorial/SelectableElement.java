package JSynTutorial;

import java.awt.Color;
import java.awt.event.MouseEvent;

import ui.Terminal;

public class SelectableElement extends WindowElement {
    
    private Color m_currentColor;
    
    private Color m_mouseDefaultColor;
    private Color m_mousePressedColor;
    private Color m_mouseEnteredColor;

    public SelectableElement(Terminal asciiPanel, String text) {
        this(asciiPanel, text, asciiPanel.getCursorX(), asciiPanel.getCursorY());
    }
    
    public SelectableElement(Terminal asciiPanel, String text, int positionX, int positionY) {
        super(asciiPanel, text, positionX, positionY);
        m_mouseDefaultColor = Color.GREEN;
        m_mouseEnteredColor = Color.ORANGE;
        m_mousePressedColor = Color.MAGENTA;
        m_currentColor = m_mouseDefaultColor;
        this.paint();
    }
    
    @Override
    public void mouseDragged(MouseEvent me) {
        
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        updateObservers();
    }

    @Override
    public void mousePressed(MouseEvent me) {
        m_currentColor = m_mousePressedColor;
        this.paint();
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        if(     me.getX() >= 0 &&
                me.getX() < m_width*ASCKingdom.CHARACTERWIDTH &&
                me.getY() >= 0 &&
                me.getY() < m_height*ASCKingdom.CHARACTERHEIGHT){
            m_currentColor = m_mouseEnteredColor;
        }
        else{
            m_currentColor = m_mouseDefaultColor;
        }
        
        this.paint();
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        m_currentColor = m_mouseEnteredColor;
        this.paint();
    }

    @Override
    public void mouseExited(MouseEvent me) {
        m_currentColor = m_mouseDefaultColor;
        this.paint();
    }
    
    @Override
    public void paint(){
        m_asciiPanel.writeString(m_positionX, m_positionY, m_text, m_currentColor);
        m_asciiPanel.repaint();
    }
}

