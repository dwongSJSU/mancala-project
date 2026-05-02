import java.awt.*;

/** 
 * BorderShape is an interface that defines one requirement: a draw method for drawing the border.
 */
public interface BorderShape {
    void draw(Graphics2D g2, int width, int height, Color color);
}