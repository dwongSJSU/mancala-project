/**
 * Mancala Assignment Solution
 *
 * @author Dylan Wong
 */

import java.awt.*;
import javax.swing.*;

/**
 * BlankComponent is the JComponent used by the application to draw a blank space.
 * Defaults to the size of a PitComponent (100x100).
 */
public class BlankComponent extends JPanel {
    private final int PREFERRED_SIZE = 100; //100 is the size of a PitComponent

    /**
     * Constructs a BlankComponent object of size 100x100.
     */
    public BlankComponent() {
        this.setPreferredSize(new Dimension(PREFERRED_SIZE, PREFERRED_SIZE));
        setBackground(UIManager.getColor("Panel.background"));
        setOpaque(true);
    }

    /**
     * Constructs a BlankComponent object of specified size.
     * 
     * @param width width of the component
     * @param height height of the component
     */
    public BlankComponent(int width, int height) {
        this.setPreferredSize(new Dimension(width, height));
        setBackground(UIManager.getColor("Panel.background"));
        setOpaque(true);
    }


    /**
     * {@inheritDoc}
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
