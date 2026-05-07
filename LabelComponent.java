/**
 * Mancala Assignment Solution
 *
 * @author Dylan Wong
 */

import java.awt.*;
import javax.swing.*;

/**
 * LabelComponent displays a piece of text with an invisible background.
 */
public class LabelComponent extends BlankComponent {
    private JLabel textHolder;

    /**
     * Constructs a LabelComponent object of size 100x100. Displays the text in the middle of the component.
     * 
     * @param text text to be displayed
     */
    public LabelComponent(String text) {
        super();

        this.textHolder = new JLabel(text);
        textHolder.setPreferredSize(new Dimension(50, 50));
        textHolder.setFont(textHolder.getFont().deriveFont(30.0f));

        this.setLayout(new GridBagLayout());
        this.add(textHolder);
    }

    /**
     * Constructs a LabelComponent object of size 100x100. Moves the text to the top/bottom of the label depending on the input.
     * 
     * @param text text to be displayed
     * @param side BorderLayout direction
     */
    public LabelComponent(String text, String side) {
        super();

        this.textHolder = new JLabel(text);
        textHolder.setPreferredSize(new Dimension(50, 50));
        textHolder.setFont(textHolder.getFont().deriveFont(30.0f));

        this.setLayout(new BorderLayout());
        
        if (side.equals(BorderLayout.NORTH)) {
            this.add(textHolder, BorderLayout.NORTH);
        }
        else if (side.equals(BorderLayout.EAST)) {
            this.add(textHolder, BorderLayout.EAST);
        }
        else if (side.equals(BorderLayout.SOUTH)) {
            this.add(textHolder, BorderLayout.SOUTH);
        }
        else if (side.equals(BorderLayout.WEST)) {
            this.add(textHolder, BorderLayout.WEST);
        }
        else {
            this.add(textHolder, BorderLayout.CENTER);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
