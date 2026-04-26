import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * MancalaDefaultView is one strategy to display the GUI for the game.
 * It provides the most basic GUI for the game and only uses black and white coloring.
 */
public class MancalaDefaultView extends JFrame implements ViewStrategy {
    private final int FRAME_SIZE = 1000;

    private MancalaLinkedList model;

    // false = Player A's turn (pits A1-A6), true = Player B's turn (pits B1-B6)
    private boolean currentSide = false;
    private boolean gameOver = false;

    //start of GUI Components
    private PitComponent a1 = new PitComponent();
    private PitComponent a2 = new PitComponent();
    private PitComponent a3 = new PitComponent();
    private PitComponent a4 = new PitComponent();
    private PitComponent a5 = new PitComponent();
    private PitComponent a6 = new PitComponent();

    private PitComponent b1 = new PitComponent();
    private PitComponent b2 = new PitComponent();
    private PitComponent b3 = new PitComponent();
    private PitComponent b4 = new PitComponent();
    private PitComponent b5 = new PitComponent();
    private PitComponent b6 = new PitComponent();

    private MancalaComponent aM = new MancalaComponent();
    private MancalaComponent bM = new MancalaComponent();

    private JLabel turnLabel = new JLabel("Player A's Turn", SwingConstants.CENTER);
    //end of GUI Components

    public MancalaDefaultView() {
        //set size of window
        this.setSize(FRAME_SIZE, FRAME_SIZE);
        this.setLayout(new BorderLayout());

        //create top and bottom "rows" for A1-6 (bottom row) and B1-6 (top row)
        JPanel topRow = new JPanel();
        topRow.setLayout(new BoxLayout(topRow, BoxLayout.X_AXIS));
        JPanel bottomRow = new JPanel();
        bottomRow.setLayout(new BoxLayout(bottomRow, BoxLayout.X_AXIS));

        //add six pits to each row
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

        //add the rows to the window
        JPanel pits = new JPanel();
        pits.setLayout(new BorderLayout());
        pits.add(bottomRow, BorderLayout.SOUTH);
        pits.add(topRow, BorderLayout.NORTH);
        this.add(pits, BorderLayout.CENTER);

        //add mancalas to the sides of the window
        this.add(aM, BorderLayout.EAST);
        this.add(bM, BorderLayout.WEST);

        //add turn indicator at the bottom
        turnLabel.setFont(turnLabel.getFont().deriveFont(Font.BOLD, 18f));
        this.add(turnLabel, BorderLayout.SOUTH);

        // wire up click listeners for every pit
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
    }

    /**
     * Attaches a MouseListener to a pit component. When clicked, the pit executes a move
     * only if it belongs to the current player's side and has at least one stone.
     *
     * @param pit       the PitComponent to listen on
     * @param space     the BoardSpace enum value this pit represents
     * @param pitSide   false = Player A's pit, true = Player B's pit
     */
    private void addPitListener(PitComponent pit, BoardSpace space, boolean pitSide) {
        pit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (gameOver) return;

                // ignore clicks if it's not this pit's player's turn
                if (currentSide != pitSide) return;

                // ignore clicks on empty pits
                if (model.getStoneCount(space) == 0) return;

                boolean extraTurn = model.startMoveOn(space, currentSide);

                // switch sides only if no extra turn was earned
                if (!extraTurn) {
                    currentSide = !currentSide;
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

    public void attachTo(MancalaLinkedList model) {
        this.model = model;
    }

    /**
     * Repaints the GUI
     * Precondition: current GUI does not show up-to-date data
     * Postcondition: GUI displays the up-to-date data
     */
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

        aM.updateCount(model.getStoneCount(BoardSpace.AM));
        bM.updateCount(model.getStoneCount(BoardSpace.BM));

        if (gameOver) {
            turnLabel.setText("Game Over");
        } else {
            turnLabel.setText(currentSide ? "Player B's Turn" : "Player A's Turn");
        }
    }

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
