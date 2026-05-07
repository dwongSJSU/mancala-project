import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * MancalaCircularView is one strategy to display the GUI for the game.
 * Draws the stones in a circular manner, with small mancalas at the left and right edges of the circle.
 */
public class MancalaCircularView extends JFrame implements ViewStrategy {
    private final int FRAME_SIZE = 1000; //size of the window

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

    private PitComponent aM = new PitComponent(new RectangleStoneRenderer(0), new SquareBorder());
    private PitComponent bM = new PitComponent(new RectangleStoneRenderer(0), new SquareBorder());

    private JLabel turnLabel = new JLabel("Player A's Turn", SwingConstants.CENTER);
    //end of GUI Components

    /**
     * Constructs a MancalaCircularView object.
     */
    public MancalaCircularView() {
        //set size of window
        this.setSize(FRAME_SIZE, FRAME_SIZE);
        this.setLayout(new BorderLayout());

        //create rows to hold the pits
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

        //add the rows to the window
        this.add(rowContainer, BorderLayout.CENTER);

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

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    /**
     * Factory method to create an invisible pit (for spacing)
     * 
     * @return new BlankComponent object
    */
    private BlankComponent blank() {
        return new BlankComponent();
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

    /**
     * Attaches this MancalaCircularView object to a MancalaLinkedList model.
     * This model is used as the source of the ongoing game's data.
     * 
     * @param model model where this view will be attached to
     */
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

    /**
     * Displays the game's results in a pop-up window.
     */
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
