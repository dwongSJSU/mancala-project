import javax.swing.*;

/**
 * The MancalaTest class contains the main method to run the program.
 */
public class MancalaTest {
    public static void main(String[] args) {
        MancalaDefaultView m = new MancalaDefaultView();
        //m.stateChanged(); --> placeholder for now, uncomment for testing to see if stateChanged works()
        m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        m.pack();
        m.setVisible(true);
    }
}
