/**
 * Mancala Assignment Solution
 *
 * @author Dylan Wong
 * (GUI design)
 *
 * @author Amarjargal Ayurzana
 * (Game Functionality: listeners, undo button + panel, announce winner)
 */

import java.awt.event.*;
import java.util.HashMap;
import javax.swing.*;

/**
 * AbstractMancalaView holds the shared game-state and Swing wiring used by
 * every concrete view. Subclasses build their own component layout and
 * supply mancala display widgets via {@link #updateAMancalaDisplay(int)} and
 * {@link #updateBMancalaDisplay(int)}.
 */
public abstract class AbstractMancalaView extends JFrame implements ViewStrategy {
    protected MancalaLinkedList model;

    // false = Player A's turn (pits A1-A6), true = Player B's turn (pits B1-B6)
    protected boolean currentSide = false;
    protected boolean gameOver = false;

    protected static final int MAX_UNDOS = 3;
    protected record BoardState(HashMap<BoardSpace, Integer> stones, boolean side) {}
    protected BoardState previousState = null;
    protected int[] undosLeft = {MAX_UNDOS, MAX_UNDOS}; // [A, B]
    protected JButton undoButton = new JButton("Undo (A: 3)");

    protected PitComponent a1 = new PitComponent();
    protected PitComponent a2 = new PitComponent();
    protected PitComponent a3 = new PitComponent();
    protected PitComponent a4 = new PitComponent();
    protected PitComponent a5 = new PitComponent();
    protected PitComponent a6 = new PitComponent();

    protected PitComponent b1 = new PitComponent();
    protected PitComponent b2 = new PitComponent();
    protected PitComponent b3 = new PitComponent();
    protected PitComponent b4 = new PitComponent();
    protected PitComponent b5 = new PitComponent();
    protected PitComponent b6 = new PitComponent();

    protected JLabel turnLabel = new JLabel("Player A's Turn", SwingConstants.CENTER);

    protected AbstractMancalaView() {
        undoButton.setEnabled(false);
        undoButton.addActionListener(e -> {
            if (previousState == null || gameOver) return;
            // The undo charges the player whose move is being reverted, not
            // necessarily the player whose turn it currently is (the side may
            // have switched after the move).
            int chargedIndex = previousState.side() ? 1 : 0;
            if (undosLeft[chargedIndex] == 0) return;
            undosLeft[chargedIndex]--;
            model.restoreSnapshot(previousState.stones());
            currentSide = previousState.side();
            previousState = null;
            stateChanged();
        });

        addPitListener(a1, BoardSpace.A1, false);
        addPitListener(a2, BoardSpace.A2, false);
        addPitListener(a3, BoardSpace.A3, false);
        addPitListener(a4, BoardSpace.A4, false);
        addPitListener(a5, BoardSpace.A5, false);
        addPitListener(a6, BoardSpace.A6, false);

        addPitListener(b1, BoardSpace.B1, true);
        addPitListener(b2, BoardSpace.B2, true);
        addPitListener(b3, BoardSpace.B3, true);
        addPitListener(b4, BoardSpace.B4, true);
        addPitListener(b5, BoardSpace.B5, true);
        addPitListener(b6, BoardSpace.B6, true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Attaches a MouseListener to a pit component. When clicked, the pit
     * executes a move only if it belongs to the current player's side and
     * has at least one stone.
     */
    private void addPitListener(PitComponent pit, BoardSpace space, boolean pitSide) {
        pit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (gameOver) return;
                if (currentSide != pitSide) return;
                if (model.getStoneCount(space) == 0) return;

                previousState = new BoardState(model.getSnapshot(), currentSide);
                boolean extraTurn = model.startMoveOn(space, currentSide);

                if (!extraTurn) {
                    currentSide = !currentSide;
                    // The new player's turn starts with a fresh undo
                    // allowance. previousState is intentionally kept so the
                    // last move can still be undone across the turn boundary.
                    undosLeft[currentSide ? 1 : 0] = MAX_UNDOS;
                }

                if (model.isGameOver()) {
                    model.sweepRemainingStones();
                    gameOver = true;
                }
                stateChanged();

                if (gameOver) {
                    announceWinner();
                }
            }
        });
    }

    @Override
    public void attachTo(MancalaLinkedList model) {
        this.model = model;
    }

    @Override
    public void stateChanged() {
        a1.updateCount(model.getStoneCount(BoardSpace.A1));
        a2.updateCount(model.getStoneCount(BoardSpace.A2));
        a3.updateCount(model.getStoneCount(BoardSpace.A3));
        a4.updateCount(model.getStoneCount(BoardSpace.A4));
        a5.updateCount(model.getStoneCount(BoardSpace.A5));
        a6.updateCount(model.getStoneCount(BoardSpace.A6));

        b1.updateCount(model.getStoneCount(BoardSpace.B1));
        b2.updateCount(model.getStoneCount(BoardSpace.B2));
        b3.updateCount(model.getStoneCount(BoardSpace.B3));
        b4.updateCount(model.getStoneCount(BoardSpace.B4));
        b5.updateCount(model.getStoneCount(BoardSpace.B5));
        b6.updateCount(model.getStoneCount(BoardSpace.B6));

        updateAMancalaDisplay(model.getStoneCount(BoardSpace.AM));
        updateBMancalaDisplay(model.getStoneCount(BoardSpace.BM));

        // Show the player whose move would be reverted (the last-action
        // player) and their remaining undo chances. When there is no move
        // to undo yet, fall back to the player whose turn it currently is.
        boolean labelSide = previousState != null ? previousState.side() : currentSide;
        int labelIndex = labelSide ? 1 : 0;
        String labelName = labelSide ? "B" : "A";
        undoButton.setText("Undo (" + labelName + ": " + undosLeft[labelIndex] + ")");
        undoButton.setEnabled(!gameOver && previousState != null && undosLeft[labelIndex] > 0);

        if (gameOver) {
            turnLabel.setText("Game Over");
        } else {
            turnLabel.setText(currentSide ? "Player B's Turn" : "Player A's Turn");
        }
    }

    /**
     * Push the latest A-mancala stone count to whatever widget the subclass
     * uses to display it.
     */
    protected abstract void updateAMancalaDisplay(int count);

    /**
     * Push the latest B-mancala stone count to whatever widget the subclass
     * uses to display it.
     */
    protected abstract void updateBMancalaDisplay(int count);

    private void announceWinner() {
        int aScore = model.getStoneCount(BoardSpace.AM);
        int bScore = model.getStoneCount(BoardSpace.BM);
        String message;
        if (aScore > bScore) {
            message = "Player A wins! (A: " + aScore + "  B: " + bScore + ")";
        } else if (bScore > aScore) {
            message = "Player B wins! (B: " + bScore + "  A: " + aScore + ")";
        } else {
            message = "It's a tie! Both players scored " + aScore + ".";
        }
        JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }
}
