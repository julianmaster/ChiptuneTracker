package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;
import java.awt.image.ShortLookupTable;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Julien MAITRE
 */
public class Terminal extends JPanel implements KeyListener {
	private static final long serialVersionUID = 7552042213014894337L;
	
	private JFrame window;
	
	private KeyEvent event;
	
    private Dimension m_size;

    private BufferedImage[] m_character;
    private Color m_defaultCharacterColor;
    private Color m_defaultCharacterBackgroundColor;
    private Dimension m_characterSize;

    private TerminalDataCell[][] m_terminal;
    private TerminalDataCell[][] m_oldTerminal;
    private Image m_image;
    private Graphics m_graphics;

    public Terminal(String title, Dimension d, String tilesetsFile, int characterWidth, int characterHeight) {
        this.m_size = d;
        this.m_characterSize = new Dimension(characterWidth, characterHeight);
        this.m_defaultCharacterColor = Color.WHITE;
        this.m_defaultCharacterBackgroundColor = Color.BLACK;

        m_terminal = new TerminalDataCell[m_size.height][m_size.width];
        m_oldTerminal = new TerminalDataCell[m_size.height][m_size.width];
        for(int i = 0; i < m_size.height; i++){
            for(int j = 0; j < m_size.width; j++){
                TerminalDataCell tdc = new TerminalDataCell();
                m_terminal[i][j] = tdc;
                m_oldTerminal[i][j] = tdc;
            }
        }

        this.setPreferredSize(new Dimension(m_size.width*m_characterSize.width, m_size.height*m_characterSize.height));

        try {
            m_character = new  BufferedImage[256];
            BufferedImage tilesets = ImageIO.read(new File(tilesetsFile));

            // Récupération de la couleur du background
            BufferedImage imageBackgroundColor = tilesets.getSubimage(0, 0, 1, 1);
            int color = imageBackgroundColor.getRGB(0, 0);
            Color m_characterBackgroundColor = Color.getColor(null, color);

            // On modifie le fond des caractères
            Image characterBackgroundColorModified = createImage(new FilteredImageSource(tilesets.getSource(), new BackgroundFilter(m_characterBackgroundColor)));

            // Création du tileset dont on a modifier la couleur du background
            BufferedImage tilesetsModified = new BufferedImage(tilesets.getWidth(), tilesets.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics graphicsTilesetsModified = tilesetsModified.getGraphics();
            graphicsTilesetsModified.setColor(Color.BLACK);
            graphicsTilesetsModified.fillRect(0, 0, tilesetsModified.getWidth(), tilesetsModified.getHeight());
            // On dessine cela dans le bufferedImage finale duquel on va récupérer les caractéres
            graphicsTilesetsModified.drawImage(characterBackgroundColorModified, 0, 0, this);

            for(int i = 0; i < 256; i++){
                int x = (i%16)*m_characterSize.width;
                int y = (i/16)*m_characterSize.height;
                m_character[i] = new BufferedImage(m_characterSize.width, m_characterSize.height, BufferedImage.TYPE_INT_ARGB);
                m_character[i].getGraphics().drawImage(tilesetsModified, 0, 0, m_characterSize.width, m_characterSize.height, x, y, x+m_characterSize.width, y+m_characterSize.height, this);
            }
        }
        catch (IOException ex) {
            Logger.getLogger(Terminal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        // Construction de la fenêtre
        window = new JFrame();
        
        window.setTitle(title);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().add(this);
        window.pack();
        window.setLocationRelativeTo(null);

        window.addKeyListener(this);
        window.setVisible(true);
    }

    public void write(int positionX, int positionY, char character, Color characterColor){
        TerminalDataCell tdc = new TerminalDataCell();
        tdc.data = character;
        tdc.dataColor = characterColor;
        tdc.backgroundColor = m_defaultCharacterBackgroundColor;
        this.write(positionX, positionY, tdc);
    }

    public void write(int positionX, int positionY, char character, Color characterColor, Color characterBackgroundColor){
        TerminalDataCell tdc = new TerminalDataCell();
        tdc.data = character;
        tdc.dataColor =characterColor;
        tdc.backgroundColor = characterBackgroundColor;
        this.write(positionX, positionY, tdc);
    }

    public void write(int positionX, int positionY, TerminalDataCell character){
        if(positionX < 0 || positionX > m_size.width - 1){
            throw new IllegalArgumentException("X position between [0 and "+m_size.width+"]");
        }
        if(positionY < 0 || positionY > m_size.height - 1){
            throw new IllegalArgumentException("Y position between [0 and "+m_size.height+"]");
        }

        m_terminal[positionY][positionX] = character;
    }

    public void writeString(int positionX, int positionY, String string){
        writeString(positionX, positionY, string, m_defaultCharacterColor);
    }

    public void writeString(int positionX, int positionY, String string, Color characterColor){
        for(char c : string.toCharArray()){
            if(positionX < 0 || positionX > m_size.width - 1){
                throw new IllegalArgumentException("X position between [0 and "+m_size.width+"]");
            }
            if(positionY < 0 || positionY > m_size.height - 1){
                throw new IllegalArgumentException("Y position between [0 and "+m_size.height+"]");
            }

            TerminalDataCell tdc = new TerminalDataCell();
            tdc.data = c;
            tdc.dataColor = characterColor;
            write(positionX, positionY, tdc);
            positionX++;
        }
    }

    public TerminalDataCell read(int positionX, int positionY){
        return this.m_oldTerminal[positionX][positionY];
    }

    public void clear(){
        clear(0, 0, m_size.width-1, m_size.height-1);
    }

    public void clear(int x, int y){
        clear(x, y, m_size.width-1, m_size.height-1);
    }

    public void clear(int x, int y, int width, int height){
        if(x < 0 || x > m_size.width - 1){
            throw new IllegalArgumentException("X position between [0 and "+(m_size.width-1)+"]");
        }
        if(y < 0 || y > m_size.height - 1){
            throw new IllegalArgumentException("Y position between [0 and "+(m_size.height-1)+"]");
        }
        if(width < 1){
            throw new IllegalArgumentException("Width under 0");
        }
        if(height < 1){
            throw new IllegalArgumentException("Height under 0");
        }
        if(width+x > m_size.width-1 || height+y > m_size.height-1){
            throw new IllegalArgumentException("Clear over the terminal");
        }
        for(int i = x; i < m_size.height; i++){
            for(int j = x; j < m_size.width; j++){
                TerminalDataCell tdc = new TerminalDataCell();
                m_terminal[i][j] = tdc;
                m_oldTerminal[i][j] = tdc;
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        if(m_image == null){
            m_image = this.createImage(this.getWidth(), this.getHeight());
            m_graphics = m_image.getGraphics();
        }

        m_graphics.setColor(Color.BLACK);
        m_graphics.fillRect(0, 0, this.getWidth(), this.getHeight());

        for(int i = 0; i < m_size.height; i++){
            for(int j = 0; j < m_size.width; j++){
                if(     m_terminal[i][j].data == m_oldTerminal[i][j].data &&
                        m_terminal[i][j].dataColor.equals(m_oldTerminal[i][j].dataColor) &&
                        m_terminal[i][j].backgroundColor.equals(m_oldTerminal[i][j].backgroundColor)){
                    continue;
                }

                LookupOp lookupOp = setColorCharacter(m_terminal[i][j].backgroundColor, m_terminal[i][j].dataColor);
                m_graphics.drawImage(lookupOp.filter(m_character[m_terminal[i][j].data], null), j*m_characterSize.width, i*m_characterSize.height, this);

                m_oldTerminal[i][j].data = m_terminal[i][j].data;
                m_oldTerminal[i][j].dataColor = m_terminal[i][j].dataColor;
                m_oldTerminal[i][j].backgroundColor = m_terminal[i][j].backgroundColor;
            }
        }

        g.drawImage(m_image, 0, 0, this);
    }

    private LookupOp setColorCharacter(Color bgColor, Color fgColor){
        short[] red = new short[256];
        short[] green = new short[256];
        short[] blue = new short[256];
        short[] alpha = new short[256];

        // Récupération des composantes couleurs de la couleur du caractère
        short dcr = (short) fgColor.getRed();
        short dcg = (short) fgColor.getGreen();
        short dcb = (short) fgColor.getBlue();

        // Récupération des composantes couleurs de la couleur du caractère
        short bgr = (short) bgColor.getRed();
        short bgg = (short) bgColor.getGreen();
        short bgb = (short) bgColor.getBlue();

        for(short j = 0; j < 256; j++){
            // Si c'est la couleur du foreground
            if(j != 0){

                /**
                 * Calcul de j*255/dcr .
                 * Produit en croix
                 * dcr = 180     255
                 *   j =  ?      50
                 * Permet de répartir la couleur demandé par l'utilisateur pour que de [0 a 255],
                 * il y est la couleur du caractère de [0 a X]
                 */
                // Rouge
                if(dcr != 0){
                    //red[j] = dcr;
                    red[j] = (short)(j*dcr/255);
                }
                else{
                    red[j] = 0;
                }

                // Vert
                if(dcg != 0){
                    green[j] = (short)(j*dcg/255);
                }
                else{
                    green[j] = 0;
                }

                // Bleu
                if( dcb != 0){
                    blue[j] = (short)(j*dcb/255);
                }
                else{
                    blue[j] = 0;
                }

                // Alpha
                alpha[j] = 255;
            }
            // Sinon c'est la couleur du background
            else {
                red[j] = bgr;
                green[j] = bgg;
                blue[j] = bgb;
                alpha[j] = 255;
            }
        }

        short[][] data = new short[][]{red, green, blue, alpha};
        LookupTable lookupTable = new ShortLookupTable(0, data);
        LookupOp lookupOp = new LookupOp(lookupTable, null);
        return lookupOp;
    }
    
    public Color getDefaultCharacterColor(){
        return this.m_defaultCharacterColor;
    }

    public void setDefaultCharacterColor(Color color){
        this.m_defaultCharacterColor = color;
    }

    public Color getDefaultCharacterBackgroundColor() {
        return m_defaultCharacterBackgroundColor;
    }

    public void setDefaultCharacterBackgroundColor(Color defaultCharacterBackgroundColor) {
        this.m_defaultCharacterBackgroundColor = defaultCharacterBackgroundColor;
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
	
//	public JFrame getWindow() {
//		return window;
//	}
}
