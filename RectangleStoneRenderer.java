/**
 * Mancala Assignment Solution
 *
 * @author Dylan Wong
 */

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * RectangleStoneRenderer is a StoneRenderer that draws the stones in a grid, and resizes the stones to fit the rectangle border.
 */
public class RectangleStoneRenderer implements StoneRenderer {
    private int numStones;

    /**
     * Constructs a RectangleStoneRenderer object.
     * 
     * @param numStones number of stones to be drawn
     */
    public RectangleStoneRenderer(int numStones) {
        this.numStones = numStones;
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

        //adapt grid to rectangle shape
        int numCols = (int) Math.ceil(Math.sqrt(numStones * (width / (double) height)));
        int numRows = (int) Math.ceil((double) numStones / numCols);

        int stoneSize = Math.min(width / numCols, height / numRows) - 4; //pick minimum between ratios so the stones will fit

        int gridWidth = numCols * stoneSize;
        int gridHeight = numRows * stoneSize;

        //draw in the center of the bounding box
        int offsetX = (width - gridWidth) / 2;
        int offsetY = (height - gridHeight) / 2;

        int stonesDrawn = 0;
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {

                if (stonesDrawn >= numStones) return;

                int x = offsetX + col * stoneSize;
                int y = offsetY + row * stoneSize;

                g2.fill(new Ellipse2D.Double(x, y, stoneSize, stoneSize));
                g2.draw(new Ellipse2D.Double(x, y, stoneSize, stoneSize));

                stonesDrawn++;
            }
        }
    }
}