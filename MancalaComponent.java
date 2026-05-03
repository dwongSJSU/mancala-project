import java.awt.*;
import javax.swing.*;

/**
 * MancalaComponent is the JComponent used by the application to draw a mancala.
 */
public class MancalaComponent extends JPanel {
    private final int PREFERRED_WIDTH = 100;
    private final int PREFERRED_HEIGHT = 200;

    private StoneRenderer stones;
    private BorderShape border;
    private Color color = Color.BLACK;

    public MancalaComponent() {
        this(new RectangleStoneRenderer(0), new SquareBorder());
        setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
    }

    public MancalaComponent(StoneRenderer stones, BorderShape border) {
        this.stones = stones;
        this.border = border;
        setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
    }

    public void updateCount(int numStones) {
        stones.setStoneCount(numStones);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(color);

        stones.draw(g2, getWidth(), getHeight());
        border.draw(g2, getWidth(), getHeight(), color);
    }
}
