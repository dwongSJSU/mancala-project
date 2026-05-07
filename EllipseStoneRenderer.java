/**
 * Mancala Assignment Solution
 *
 * @author Dylan Wong
 */

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * EllipseStoneRenderer is a StoneRenderer that draws the stones in a sqrt(n)xsqrt(n) grid.
 * Drawing assumes a 100x100 ellipse border when resizing the stones. Resizing formula may not work with other dimensions.
 * Works best when constructed with a starting x and y of 10.
 */
public class EllipseStoneRenderer implements StoneRenderer {
    private int numStones;
    private int startX;
    private int startY;

    /**
     * Constructs an EllipseStoneRenderer object.
     * 
     * @param numStones number of stones to be drawn
    */
    public EllipseStoneRenderer(int numStones) {
        this.numStones = numStones;
        this.startX = 0;
        this.startY = 0;
    }

    /**
     * Constructs an EllipseStoneRenderer object that draws stones at a specified (x, y).
     * 
     * @param numStones number of stones to be drawn
     * @param startX starting x-coordinate
     * @param startY starting y-coordinate
    */
    public EllipseStoneRenderer(int numStones, int startX, int startY) {
        this.numStones = numStones;
        this.startX = startX;
        this.startY = startY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStoneCount(int numStones) {
        this.numStones = numStones;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(Graphics2D g2, int width, int height) {
        if (numStones <= 0) return;

        int gridSize = (int) Math.ceil(Math.sqrt(numStones));

        int sizeMod = (int) Math.round(33.0 / (numStones + 1.0)) + 2; //magic formula to keep the stones within the border
        int stoneSize = (Math.min(width, height) / gridSize) - sizeMod;

        int stonesDrawn = 0;

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {

                if (stonesDrawn >= numStones) return;

                int x = col * stoneSize;
                int y = row * stoneSize;

                Ellipse2D.Double stone =
                        new Ellipse2D.Double(x + startX, y + startY, stoneSize, stoneSize);

                g2.fill(stone);
                g2.draw(stone);

                stonesDrawn++;
            }
        }
    }
}