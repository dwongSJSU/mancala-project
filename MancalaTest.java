/**
 * Mancala Assignment Solution
 *
 * @author Dylan Wong
 */

import java.util.Scanner;

/**
 * The MancalaTest class contains the main method to run the program.
 */
public class MancalaTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int numStones = 0;
        do {
            try {
                System.out.println("Enter starting number of stones (3 or 4): ");
                numStones = sc.nextInt();
            }
            catch (Exception e) {
                //consume invalid input
                sc.nextLine();
            }
        } while (numStones != 3 && numStones != 4);
        sc.nextLine(); //consume remaining newline in the buffer

        String viewChoice = "";
        do {
            System.out.println("Enter how you would like the board to be displayed: Default (d) or Circular (c): ");
            viewChoice = sc.nextLine().strip().toLowerCase();
        } while (!viewChoice.equals("d") && !viewChoice.equals("c"));

        sc.close();

        MancalaLinkedList model = new MancalaLinkedList(numStones);
        
        ViewStrategy view;
        if (viewChoice.equals("d")) {
            view = new MancalaDefaultView();
        }
        else if (viewChoice.equals("c")) {
            view = new MancalaCircularView();
        }
        else {
            throw new IllegalStateException("The user's choice for the game display (view) is illegal.");
        }

        //MancalaCircularView view = new MancalaCircularView();
        view.attachTo(model);
        view.stateChanged(); // display the real initial board state
    }
}
