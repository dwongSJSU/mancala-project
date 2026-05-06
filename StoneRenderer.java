import java.awt.*;

/** 
 * StoneRenderer is an interface that defines two requirements for StoneRenderer objects: update stone count and draw (render) the stones.
 */
public interface StoneRenderer {
    /**
     * Draws the stones within the bounding box.
     * 
     * @param g2 Graphics2D object that handles the drawings
     * @param width width of the bounding area
     * @param height height of the bounding area
     */
    void draw(Graphics2D g2, int width, int height);

    /**
     * Updates the number of stones to be drawn.
     * 
     * @param count new stone count
     */
    void setStoneCount(int count);
}