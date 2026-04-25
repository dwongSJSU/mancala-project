import java.awt.*;
import javax.swing.*;

/**
 * MancalaDefaultView is one strategy to display the GUI for the game.
 * It provides the most basic GUI for the game and only uses black and white coloring.
 */
public class MancalaDefaultView extends JFrame implements ViewStrategy {
    private final int FRAME_SIZE = 1000;

    private MancalaLinkedList model;

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
        topRow.add(b1);
        topRow.add(b2);
        topRow.add(b3);
        topRow.add(b4);
        topRow.add(b5);
        topRow.add(b6);

        //add the rows to the window
        JPanel pits = new JPanel();
        pits.setLayout(new BorderLayout());
        pits.add(bottomRow, BorderLayout.SOUTH);
        pits.add(topRow, BorderLayout.NORTH);
        this.add(pits, BorderLayout.CENTER);

        //add mancalas to the sides of the window
        this.add(aM, BorderLayout.EAST);
        this.add(bM, BorderLayout.WEST);
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
        //-------------------- PLACEHOLDER CODE --------------------
        
        /*
        data = model.getData();
        */

        a1.updateCount(10);
        a2.updateCount(10);
        a3.updateCount(10);
        a4.updateCount(10);
        a5.updateCount(10);
        a6.updateCount(10);

        b1.updateCount(10);
        b2.updateCount(10);
        b3.updateCount(10);
        b4.updateCount(10);
        b5.updateCount(10);
        b6.updateCount(10);

        aM.updateCount(10);
        bM.updateCount(10);
    }
}
