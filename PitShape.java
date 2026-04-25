import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * PitShape contains directions on how to draw a pit (with stones). This class should only be used by PitComponent.
 */
public class PitShape {
    private final int BORDER_SIZE = 100; //size of the ellipse border of this pit

    private int numStones; //number of stones in this pit

    public PitShape() {
        this.numStones = 0;
    }

    public PitShape(int numStones) {
        this.numStones = numStones;
    }

    public void draw (Graphics2D g2) {
        Ellipse2D.Double border = new Ellipse2D.Double(0, 0, BORDER_SIZE, BORDER_SIZE);
        g2.draw(border);

        if (numStones <= 0) {
            return;
        }

        //draw stones in a sqrt(n) x sqrt(n) array
        int gridSize = (int) Math.ceil(Math.sqrt(numStones));

        int sizeMod =  (int) Math.round(33.0 / (numStones + 1.0)) + 2; //magic formula to keep stones within the border
        int stoneSize = (BORDER_SIZE / gridSize) - sizeMod;

        int startX = 10;
        int startY = 10;

        //traverse the grid and draw a stone in each cell until we run out
        int stonesDrawn = 0;
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {

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
