import java.awt.*;

/** 
 * BorderShape is an interface that defines one requirement: a draw method for drawing a border.
 */
public interface BorderShape {
    /**
     * Draws a border.
     * 
     * @param g2 Graphics2D object that handles the drawing
     * @param width width of the border
     * @param height height of the border
     * @param color color of the border
     */
    void draw(Graphics2D g2, int width, int height, Color color);
}