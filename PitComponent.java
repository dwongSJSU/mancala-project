import java.awt.*;
import javax.swing.*;

/**
 * PitComponent is the JComponent used by the application to draw a pit (of size 100x100) with either a square or circular border.
 */
public class PitComponent extends JPanel {
    private final int PREFERRED_SIZE = 100;

    private StoneRenderer stones;
    private BorderShape border;
    private Color color = Color.BLACK;

    /**
     * Default PitComponent Constructor. Creates a PitComponent with a circular border, and provides a default offset to keep the stones in the circle border.
     */
    public PitComponent() {
        this.stones = new EllipseStoneRenderer(0, 10, 10);
        this.border = new CircleBorder();
        setPreferredSize(new Dimension(PREFERRED_SIZE, PREFERRED_SIZE));
    }

    public PitComponent(StoneRenderer stones, BorderShape border) {
        this.stones = stones;
        this.border = border;
        setPreferredSize(new Dimension(PREFERRED_SIZE, PREFERRED_SIZE));
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(color);

        stones.draw(g2, getWidth(), getHeight());
        border.draw(g2, getWidth(), getHeight(), color);
    }

    public void updateCount(int numStones) {
        stones.setStoneCount(numStones);
        repaint();
    }
}