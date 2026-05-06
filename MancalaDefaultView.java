import java.awt.*;
import java.awt.event.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import javax.swing.*;

/**
 * MancalaDefaultView is one strategy to display the GUI for the game.
 * It provides the most basic GUI for the game and only uses black and white coloring.
 */
public class MancalaDefaultView extends JFrame implements ViewStrategy {
    private final int FRAME_SIZE = 1500; //size of the window
    private final int CONTAINER_SIZE = 1000;

    private MancalaLinkedList model;

    // false = Player A's turn (pits A1-A6), true = Player B's turn (pits B1-B6)
    private boolean currentSide = false;
    private boolean gameOver = false;

    private static final int MAX_UNDOS = 3;
    private record BoardState(HashMap<BoardSpace, Integer> stones, boolean side) {}
    private final Deque<BoardState> undoStack = new ArrayDeque<>();
    private int[] undosLeft = {MAX_UNDOS, MAX_UNDOS}; // [A, B]
    private JButton undoButton = new JButton("Undo (A: 3, B: 3)");

    
    //start of GUI Components
    // later refactor it into array. 
    // private PitComponent[] aComponents = new PitComponent[6];
    // private PitComponent[] bComponents = new PitComponent[6];
    
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

    private LargeMancalaComponent aM = new LargeMancalaComponent();
    private LargeMancalaComponent bM = new LargeMancalaComponent();

    private JLabel turnLabel = new JLabel("Player A's Turn", SwingConstants.CENTER);
    //end of GUI Components

    public MancalaDefaultView() {
        //set size of window
        this.setSize(CONTAINER_SIZE, CONTAINER_SIZE);
        this.setLayout(new BorderLayout());

        JPanel gameContainer = new JPanel();
        gameContainer.setSize(FRAME_SIZE, FRAME_SIZE);
        gameContainer.setLayout(new BorderLayout());

        JPanel topLabels = new JPanel();
        topLabels.setSize(CONTAINER_SIZE, CONTAINER_SIZE-FRAME_SIZE);
        topLabels.setLayout(new BoxLayout(topLabels, BoxLayout.X_AXIS));

        topLabels.add(new BlankComponent(50, 100)); //padding
        topLabels.add(new LabelComponent("BM", 2));
        topLabels.add(new LabelComponent("B6", 2));
        topLabels.add(new LabelComponent("B5", 2));
        topLabels.add(new LabelComponent("B4", 2));
        topLabels.add(new LabelComponent("B3", 2));
        topLabels.add(new LabelComponent("B2", 2));
        topLabels.add(new LabelComponent("B1", 2));
        topLabels.add(new BlankComponent());

        JPanel bottomLabels = new JPanel();
        bottomLabels.setSize(CONTAINER_SIZE, CONTAINER_SIZE-FRAME_SIZE);
        bottomLabels.setLayout(new BoxLayout(bottomLabels, BoxLayout.X_AXIS));

        bottomLabels.add(new BlankComponent(150, 100)); //padding
        bottomLabels.add(new LabelComponent("A1", 0));
        bottomLabels.add(new LabelComponent("A2", 0));
        bottomLabels.add(new LabelComponent("A3", 0));
        bottomLabels.add(new LabelComponent("A4", 0));
        bottomLabels.add(new LabelComponent("A5", 0));
        bottomLabels.add(new LabelComponent("A6", 0));
        bottomLabels.add(new LabelComponent("AM", 0));
        
        JPanel board = new JPanel(new BorderLayout());

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

        //add the rows to the board
        JPanel pits = new JPanel();
        pits.setLayout(new BorderLayout());
        pits.add(bottomRow, BorderLayout.SOUTH);
        pits.add(topRow, BorderLayout.NORTH);
        board.add(pits, BorderLayout.CENTER);

        //add mancalas to the sides of the board
        board.add(aM, BorderLayout.EAST);
        board.add(bM, BorderLayout.WEST);

        //add board to the window
        board.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        gameContainer.add(board, BorderLayout.CENTER);
        gameContainer.add(topLabels, BorderLayout.NORTH);
        gameContainer.add(bottomLabels, BorderLayout.SOUTH);

        //add turn indicator and undo button at the bottom
        turnLabel.setFont(turnLabel.getFont().deriveFont(Font.BOLD, 18f));
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(turnLabel, BorderLayout.CENTER);
        bottomPanel.add(undoButton, BorderLayout.EAST);

        undoButton.addActionListener(e -> {
            if (undoStack.isEmpty() || gameOver) return;
            int playerIndex = currentSide ? 1 : 0;
            if (undosLeft[playerIndex] == 0) {
                JOptionPane.showMessageDialog(gameContainer, (currentSide ? "Player B" : "Player A") + " has no undos left.", "Undo Unavailable", JOptionPane.WARNING_MESSAGE);
                return;
            }
            undosLeft[playerIndex]--;
            BoardState prev = undoStack.pop();
            model.restoreSnapshot(prev.stones());
            currentSide = prev.side();
            stateChanged();
        });

        this.add(gameContainer, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

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
                
                undoButton.setEnabled(!undoStack.isEmpty());
                undoStack.push(new BoardState(model.getSnapshot(), currentSide));
                boolean extraTurn = model.startMoveOn(space, currentSide);

                // switch sides only if no extra turn was earned
                if (!extraTurn) {
                    currentSide = !currentSide;
                    undoButton.setEnabled(false);
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
     * Attaches this MancalaDefaultView object to a MancalaLinkedList model.
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

        undoButton.setText("Undo (A: " + undosLeft[0] + ", B: " + undosLeft[1] + ")");
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
