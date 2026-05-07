/**
 * Mancala Assignment Solution
 *
 * @author Dylan Wong
 */

import javax.swing.*;

/**
 * The MancalaTest class contains the main method to run the program.
 */
public class MancalaTest {
    public static void main(String[] args) {
        Object[] stoneOptions = {"4 stones", "3 stones"};
        String message = "Choose how many stones should start in each pit:";
        String title = "Game Setup";
        int stoneChoice = JOptionPane.showOptionDialog(null, message, title, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, stoneOptions, stoneOptions[0]);
        if (stoneChoice == JOptionPane.CLOSED_OPTION) {
            return;
        }

        int numStones;
        if (stoneChoice == 0) { //button 0: 4 stones
            numStones = 4;
        }
        else { //button 1: 3 stones
            numStones = 3;
        }

        Object[] viewOptions = {"Default", "Circular"};
        message = "Choose board display:";
        int viewChoice = JOptionPane.showOptionDialog(null, message, title, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, viewOptions, viewOptions[0]);
        if (viewChoice == JOptionPane.CLOSED_OPTION) {
            return;
        }

        MancalaLinkedList model = new MancalaLinkedList(numStones);
        
        ViewStrategy view;
        if (viewChoice == 0) { //button 0: Default
            view = new MancalaDefaultView();
        }
        else { //button 1: circular
            view = new MancalaCircularView();
        }

        view.attachTo(model);
        view.stateChanged(); // display the real initial board state
    }
}
