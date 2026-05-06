import java.awt.*;
import java.awt.geom.Ellipse2D;

/** 
 * CircleBorder is a BorderShape that draws a circular border.
 * Intended use is for drawing circular borders, but it also accomodates drawing elliptical borders.
 */
public class CircleBorder implements BorderShape {
    /**
     * Draws a circular border.
     * 
     * @param g2 {@inheritDoc}
     * @param w {@inheritDoc}
     * @param h {@inheritDoc}
     * @param color {@inheritDoc}
     */
    @Override
    public void draw(Graphics2D g2, int w, int h, Color color) {
        g2.setColor(color);
        g2.draw(new Ellipse2D.Double(0, 0, w - 1, h - 1));
    }
}