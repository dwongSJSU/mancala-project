import java.awt.*;
import javax.swing.*;

/**
 * BlankComponent is the JComponent used by the application to draw a blank space that is the size of a PitComponent (100x100).
 */
public class BlankComponent extends JPanel {
    private final int PREFERRED_SIZE = 100;

    public BlankComponent() {
        this.setPreferredSize(new Dimension(PREFERRED_SIZE, PREFERRED_SIZE));
        setBackground(UIManager.getColor("Panel.background"));
        setOpaque(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
