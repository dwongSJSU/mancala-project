import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;

/**
 * MancalaShape contains directions on how to draw a mancala (with stones). This class should only be used by MancalaComponent.
 */
public class MancalaShape {
    private final int HEIGHT = 200;
    private final int WIDTH = 100;

    private int numStones; //number of stones in this mancala

    public MancalaShape() {
        this.numStones = 0;
    }

    public MancalaShape(int numStones) {
        this.numStones = numStones;
    }

    public void draw (Graphics2D g2) {
        Rectangle2D.Double border = new Rectangle2D.Double(0, 0, WIDTH, HEIGHT);
        g2.draw(border);

        if (numStones <= 0) {
            return;
        }

        int stoneSize = (WIDTH / 4) - 3; //4 stones (columns) per row
        int numCols = 4;

        int numRows = Math.ceilDiv(numStones, numCols);

        int startX = 3;
        int startY = 3;

        //traverse the grid and draw a stone in each cell until we run out
        int stonesDrawn = 0;
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {

                if (stonesDrawn < numStones) {
                    int x = col * stoneSize;
                    int y = row * stoneSize;

                    Ellipse2D.Double stone = new Ellipse2D.Double(x + startX, y + startY, stoneSize, stoneSize);
                    g2.draw(stone);
                    g2.fill(stone);

                    stonesDrawn++;
                }
                else {
                    break;
                }

            }
        }
    }
}
