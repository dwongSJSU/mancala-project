import java.util.Scanner;
import java.util.Set;

public class Player {
    private String name;
    private boolean side; // false -> top, true -> bottom.
    private static final Set<String> TOP_SPACES = Set.of("A1", "A2", "A3", "A4", "A5", "A6");
    private static final Set<String> BOTTOM_SPACES = Set.of("B1", "B2", "B3", "B4", "B5", "B6");
    private MancalaLinkedList mancalaboard;

    public Player(String name, boolean side, MancalaLinkedList mancalaboard) {
        this.name = name;
        this.side = side;
        this.mancalaboard = mancalaboard;
    }

    public void makeMove(Scanner scanner) {
        Set<String> validSpaces = side ? BOTTOM_SPACES : TOP_SPACES;
        String prefix = side ? "B" : "A";

        BoardSpace chosen = null;
        while (chosen == null) {
            System.out.print("Choose your grid (" + prefix + "1-" + prefix + "6): ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (validSpaces.contains(input)) {
                chosen = BoardSpace.valueOf(input);
            } else {
                System.out.println("Invalid choice. Please pick from " + prefix + "1-" + prefix + "6.");
            }
        }
        if(mancalaboard.startMoveOn(chosen, side)) {
            System.out.println("Player" + name + " got another chance to move.");
            makeMove(scanner);
        }
    }
}
