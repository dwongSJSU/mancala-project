/**
 * Mancala Assignment Solution
 *
 * @author Dylan Wong
 * (GUI design)
 *
 * @author Amarjargal Ayurzana
 * (code refactoring + inheritance)
 */

import java.awt.*;
import javax.swing.*;

/**
 * MancalaCircularView is one strategy to display the GUI for the game.
 * Draws the stones in a circular manner, with small mancalas at the left and right edges of the circle.
 */
public class MancalaCircularView extends AbstractMancalaView {
    private final int FRAME_SIZE = 1000;

    private PitComponent aM = new PitComponent(new RectangleStoneRenderer(0), new SquareBorder());
    private PitComponent bM = new PitComponent(new RectangleStoneRenderer(0), new SquareBorder());

    /**
     * Constructs a MancalaCircularView object
     */
    public MancalaCircularView() {
        super();
        this.setSize(FRAME_SIZE, FRAME_SIZE);
        this.setLayout(new BorderLayout());

        JPanel rowContainer = new JPanel();
        rowContainer.setLayout(new GridLayout(7, 6));
        rowContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //top row
        rowContainer.add(blank()); rowContainer.add(new LabelComponent("B4", BorderLayout.EAST)); rowContainer.add(b4); rowContainer.add(b3); rowContainer.add(new LabelComponent("B3", BorderLayout.WEST)); rowContainer.add(blank());

        //second row
        rowContainer.add(new LabelComponent("B5", BorderLayout.EAST)); rowContainer.add(b5); rowContainer.add(blank()); rowContainer.add(blank()); rowContainer.add(b2); rowContainer.add(new LabelComponent("B2", BorderLayout.WEST));

        //third row
        rowContainer.add(b6); rowContainer.add(new LabelComponent("B6", BorderLayout.WEST)); rowContainer.add(blank()); rowContainer.add(blank()); rowContainer.add(new LabelComponent("B1", BorderLayout.EAST)); rowContainer.add(b1);

        //mancala row
        rowContainer.add(bM); rowContainer.add(new LabelComponent("BM", BorderLayout.WEST)); rowContainer.add(blank()); rowContainer.add(blank()); rowContainer.add(new LabelComponent("AM", BorderLayout.EAST)); rowContainer.add(aM);

        //fifth row
        rowContainer.add(a1); rowContainer.add(new LabelComponent("A1", BorderLayout.WEST)); rowContainer.add(blank()); rowContainer.add(blank()); rowContainer.add(new LabelComponent("A6", BorderLayout.EAST)); rowContainer.add(a6);

        //sixth row
        rowContainer.add(new LabelComponent("A2", BorderLayout.EAST)); rowContainer.add(a2); rowContainer.add(blank()); rowContainer.add(blank()); rowContainer.add(a5); rowContainer.add(new LabelComponent("A5", BorderLayout.WEST));

        //bottom row
        rowContainer.add(blank()); rowContainer.add(new LabelComponent("A3", BorderLayout.EAST)); rowContainer.add(a3); rowContainer.add(a4); rowContainer.add(new LabelComponent("A4", BorderLayout.WEST)); rowContainer.add(blank());

        this.add(rowContainer, BorderLayout.CENTER);

        turnLabel.setFont(turnLabel.getFont().deriveFont(Font.BOLD, 18f));
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(turnLabel, BorderLayout.CENTER);
        bottomPanel.add(undoButton, BorderLayout.EAST);
        this.add(bottomPanel, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    /**
     * Factory method which creates a default BlankComponent object.
     * 
     * @return newly constructed BlankComponent
     */
    private BlankComponent blank() {
        return new BlankComponent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void updateAMancalaDisplay(int count) {
        aM.updateCount(count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void updateBMancalaDisplay(int count) {
        bM.updateCount(count);
    }
}
