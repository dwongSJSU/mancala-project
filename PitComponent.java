import java.awt.*;
import javax.swing.*;

/**
 * PitComponent is the JComponent used by the application to draw a pit.
 */
public class PitComponent extends JPanel {
    private final int PREFERRED_SIZE = 100;

    private PitShape pit;
    private Color color = Color.BLACK;

    public PitComponent() {
        this.pit = new PitShape();
        this.setPreferredSize(new Dimension(PREFERRED_SIZE, PREFERRED_SIZE));
    }

    public PitComponent(int numStones) {
        this.pit = new PitShape(numStones);
        this.setPreferredSize(new Dimension(PREFERRED_SIZE, PREFERRED_SIZE));
    }

    public PitComponent(int numStones, Color color) {
        this(numStones);
        this.color = color;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(color);
        pit.draw(g2);
    }

    public void updateCount(int numStones) {
        this.pit = new PitShape(numStones);
        repaint();
    }
}
