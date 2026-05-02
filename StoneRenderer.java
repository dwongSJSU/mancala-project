import java.awt.*;

/** 
 * StoneRenderer is an interface that defines two requirements for StoneRenderer objects: update stone count and draw (render) the stones.
 */
public interface StoneRenderer {
    void draw(Graphics2D g2, int width, int height);
    void setStoneCount(int count);
}