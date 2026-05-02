import java.awt.*;

/** 
 * CircleBorder is a BorderShape that draws a square border.
 */
public class SquareBorder implements BorderShape {
    @Override
    public void draw(Graphics2D g2, int w, int h, Color color) {
        g2.setColor(color);
        g2.drawRect(0, 0, w - 1, h - 1);
    }
}