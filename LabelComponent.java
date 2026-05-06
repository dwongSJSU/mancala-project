import java.awt.*;
import javax.swing.*;

public class LabelComponent extends BlankComponent {
    private JLabel textHolder;

    /**
     * Constructs a LabelComponent object of size 100x100.
     * 
     * @param text text to be displayed
     * @param side true to display the text at the top of the label, false to display the text at the bottom of the label
     */
    public LabelComponent(String text, boolean side) {
        super();

        this.textHolder = new JLabel(text);
        textHolder.setPreferredSize(new Dimension(50, 50));
        textHolder.setFont(textHolder.getFont().deriveFont(30.0f));

        this.setLayout(new BorderLayout());
        
        if (side) {
            this.add(textHolder, BorderLayout.NORTH);
        }
        else {
            this.add(textHolder, BorderLayout.SOUTH);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
