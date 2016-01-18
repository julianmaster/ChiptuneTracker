package ui;

import java.awt.Color;
import java.awt.image.RGBImageFilter;

/**
 * Change la couleur du fond des caractères d'un tileset en noir.
 * @author Julien
 */
public class BackgroundFilter extends RGBImageFilter {

    private Color m_BackgroundColor;

    public BackgroundFilter(Color m_BackgroundColor) {
        // La transformation des couleurs ne dépend pas
        // des coordonnées des points de l'image
        canFilterIndexColorModel = true;
        this.m_BackgroundColor = m_BackgroundColor;
    }

    @Override
    public int filterRGB(int x, int y, int rgb) {
        int red = rgb & 0x00FF0000;
        int green = rgb & 0x0000FF00;
        int blue = rgb & 0x000000FF;
        int alpha = rgb & 0xFF000000;

        if(     (red >> 4*4) == m_BackgroundColor.getRed() &&
                (green >> 2*4) == m_BackgroundColor.getGreen()&&
                (blue >> 0*4) == m_BackgroundColor.getBlue()){
            return alpha << 6*4;
        }
        else{
            return rgb;
        }
    }
}
