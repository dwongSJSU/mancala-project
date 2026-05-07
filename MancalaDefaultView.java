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
 * MancalaDefaultView is one strategy to display the GUI for the game.
 * It provides the most basic GUI for the game and only uses black and white coloring.
 */
public class MancalaDefaultView extends AbstractMancalaView {
    private final int FRAME_SIZE = 1500;
    private final int CONTAINER_SIZE = 1000;

    private LargeMancalaComponent aM = new LargeMancalaComponent();
    private LargeMancalaComponent bM = new LargeMancalaComponent();

    public MancalaDefaultView() {
        super();
        this.setSize(CONTAINER_SIZE, CONTAINER_SIZE);
        this.setLayout(new BorderLayout());

        JPanel gameContainer = new JPanel();
        gameContainer.setSize(FRAME_SIZE, FRAME_SIZE);
        gameContainer.setLayout(new BorderLayout());

        JPanel topLabels = new JPanel();
        topLabels.setSize(CONTAINER_SIZE, CONTAINER_SIZE - FRAME_SIZE);
        topLabels.setLayout(new BoxLayout(topLabels, BoxLayout.X_AXIS));

        topLabels.add(new BlankComponent(50, 100)); //padding
        topLabels.add(new LabelComponent("BM", BorderLayout.SOUTH));
        topLabels.add(new LabelComponent("B6", BorderLayout.SOUTH));
        topLabels.add(new LabelComponent("B5", BorderLayout.SOUTH));
        topLabels.add(new LabelComponent("B4", BorderLayout.SOUTH));
        topLabels.add(new LabelComponent("B3", BorderLayout.SOUTH));
        topLabels.add(new LabelComponent("B2", BorderLayout.SOUTH));
        topLabels.add(new LabelComponent("B1", BorderLayout.SOUTH));
        topLabels.add(new BlankComponent());

        JPanel bottomLabels = new JPanel();
        bottomLabels.setSize(CONTAINER_SIZE, CONTAINER_SIZE - FRAME_SIZE);
        bottomLabels.setLayout(new BoxLayout(bottomLabels, BoxLayout.X_AXIS));

        bottomLabels.add(new BlankComponent(150, 100)); //padding
        bottomLabels.add(new LabelComponent("A1", BorderLayout.NORTH));
        bottomLabels.add(new LabelComponent("A2", BorderLayout.NORTH));
        bottomLabels.add(new LabelComponent("A3", BorderLayout.NORTH));
        bottomLabels.add(new LabelComponent("A4", BorderLayout.NORTH));
        bottomLabels.add(new LabelComponent("A5", BorderLayout.NORTH));
        bottomLabels.add(new LabelComponent("A6", BorderLayout.NORTH));
        bottomLabels.add(new LabelComponent("AM", BorderLayout.NORTH));

        JPanel board = new JPanel(new BorderLayout());

        JPanel topRow = new JPanel();
        topRow.setLayout(new BoxLayout(topRow, BoxLayout.X_AXIS));
        JPanel bottomRow = new JPanel();
        bottomRow.setLayout(new BoxLayout(bottomRow, BoxLayout.X_AXIS));

        bottomRow.add(a1);
        bottomRow.add(a2);
        bottomRow.add(a3);
        bottomRow.add(a4);
        bottomRow.add(a5);
        bottomRow.add(a6);
        // top row has to be added in reverse order due to the linked list binding.
        // game continues counterclockwise.
        topRow.add(b6);
        topRow.add(b5);
        topRow.add(b4);
        topRow.add(b3);
        topRow.add(b2);
        topRow.add(b1);

        JPanel pits = new JPanel();
        pits.setLayout(new BorderLayout());
        pits.add(bottomRow, BorderLayout.SOUTH);
        pits.add(topRow, BorderLayout.NORTH);
        board.add(pits, BorderLayout.CENTER);

        board.add(aM, BorderLayout.EAST);
        board.add(bM, BorderLayout.WEST);

        board.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        gameContainer.add(board, BorderLayout.CENTER);
        gameContainer.add(topLabels, BorderLayout.NORTH);
        gameContainer.add(bottomLabels, BorderLayout.SOUTH);

        turnLabel.setFont(turnLabel.getFont().deriveFont(Font.BOLD, 18f));
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(turnLabel, BorderLayout.CENTER);
        bottomPanel.add(undoButton, BorderLayout.EAST);

        this.add(gameContainer, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    @Override
    protected void updateAMancalaDisplay(int count) {
        aM.updateCount(count);
    }

    @Override
    protected void updateBMancalaDisplay(int count) {
        bM.updateCount(count);
    }
}
