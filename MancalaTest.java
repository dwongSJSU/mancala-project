import javax.swing.*;

/**
 * The MancalaTest class contains the main method to run the program.
 */
public class MancalaTest {
    public static void main(String[] args) {
        MancalaLinkedList model = new MancalaLinkedList();
        MancalaDefaultView view = new MancalaDefaultView();
        //MancalaCircularView view = new MancalaCircularView();
        view.attachTo(model);
        view.stateChanged(); // display the real initial board state
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.pack();
        view.setVisible(true);
    }
}
