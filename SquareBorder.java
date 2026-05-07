/**
 * Mancala Assignment Solution
 *
 * @author Dylan Wong
 */

import java.awt.*;

/** 
 * SquareBorder is a BorderShape that draws a square border.
 * Intended use is for drawing square borders, but it also accomodates drawing rectangular borders.
 */
public class SquareBorder implements BorderShape {
    /**
     * Draws a square border.
     * 
     * @param g2 {@inheritDoc}
     * @param w {@inheritDoc}
     * @param h {@inheritDoc}
     * @param color {@inheritDoc}
     */
    @Override
    public void draw(Graphics2D g2, int w, int h, Color color) {
        g2.setColor(color);
        g2.drawRect(0, 0, w - 1, h - 1);
    }
}