import java.awt.*;
import javax.swing.*;

/**
 * MancalaComponent is the JComponent used by the application to draw a mancala.
 */
public class MancalaComponent extends JPanel {
    final int PREFERRED_WIDTH = 100;
    final int PREFERRED_HEIGHT = 100;

    private MancalaShape mancala;
    private Color color = Color.BLACK;

    public MancalaComponent() {
        this.mancala = new MancalaShape();
        this.setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
    }

    public MancalaComponent(int numStones) {
        this.mancala = new MancalaShape(numStones);
        this.setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
    }

    public MancalaComponent(int numStones, Color color) {
        this(numStones);
        this.color = color;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(color);
        mancala.draw(g2);
    }

    public void updateCount(int numStones) {
        this.mancala = new MancalaShape(numStones);
        repaint();
    }
}
