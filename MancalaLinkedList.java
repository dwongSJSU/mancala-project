/**
 * Mancala Assignment Solution
 *
 * @author Dylan Wong
 * (base design)
 * 
 * @author Amarjargal Ayurzana
 * (code refactoring + startMoveOn, sweepStones, snapshot-related functionality)
 */

import java.util.HashMap;
import java.util.Map;

/**
 * MancalaLinkedList uses a circular linked list to represent the game board.
 */
public class MancalaLinkedList {
    private HashMap<BoardSpace, Node> nodes; //maps an enum representing the board spaces to their actual node objects
    private Node currentNode; //current board space selected
    private int defaultStoneInPit;
    public static BoardSpace[] aPits = {BoardSpace.A1, BoardSpace.A2, BoardSpace.A3, BoardSpace.A4, BoardSpace.A5, BoardSpace.A6, BoardSpace.AM};
    public static BoardSpace[] bPits = {BoardSpace.B1, BoardSpace.B2, BoardSpace.B3, BoardSpace.B4, BoardSpace.B5, BoardSpace.B6, BoardSpace.BM};

    /**
     * Constructs a MancalaLinkedList object.
     * The board contains 12 pits and 2 mancalas. Each pit starts with 4 stones.
     */
    public MancalaLinkedList() {
        defaultStoneInPit = 4;
        this.nodes = new HashMap<BoardSpace, Node>();
        initializeBoard();
    }

    /**
     * Constructs a MancalaLinkedList object.
     * The board has 12 pits and 2 mancalas. Each pit starts with the input number of stones.
     */
    public MancalaLinkedList(int startingStones) {
        this.defaultStoneInPit = startingStones;
        this.nodes = new HashMap<BoardSpace, Node>();
        initializeBoard();
    }

    /** 
     * Initializes the game board. More specifically, it creates 12 pits with 4 stones each and 2 empty mancalas, and links them together to form the board.
     */
    public void initializeBoard() {
        nodes.clear();
        int n = aPits.length;
        Node[] aNodes = new Node[n];
        Node[] bNodes = new Node[n];

        // create and add nodes to the map.
        for(int i = 0; i < n; i++) {
            aNodes[i] = new Node(aPits[i], (i < n - 1 ? defaultStoneInPit : 0));
            bNodes[i] = new Node(bPits[i], (i < n - 1 ? defaultStoneInPit : 0));
            nodes.put(aPits[i], aNodes[i]);
            nodes.put(bPits[i], bNodes[i]);
        }

        // link them together.
        for(int i = 0; i < n - 1; i++) {
            aNodes[i].setNext(aNodes[i + 1]);
            bNodes[i].setNext(bNodes[i + 1]);
            aNodes[i].setOpposite(bNodes[n - 2 - i]);
            bNodes[i].setOpposite(aNodes[n - 2 - i]);
        }
        // A mancala's next is B6.
        aNodes[n - 1].setNext(bNodes[0]);
        // B mancala's next is A1
        bNodes[n - 1].setNext(aNodes[0]);
        //set the currently selected node to null (nothing selected yet)
        this.currentNode = null;
    }

    /**
     * Sets the currently selected node to the input.
     * Precondition: none
     * Postcondition: The current node is set to the input.
     * 
     * @param newSpace board space to be set as the new current node.
     * @exception IllegalStateException the input space was not linked to a node during board initialization
     */
    public void setCurrentNode(BoardSpace newSpace) {
        Node newNode = nodes.get(newSpace);
        if (newNode == null) {
            throw new IllegalStateException("Error: input board space does not correspond to a node on the board.");
        }
        this.currentNode = newNode;
    }

    /**
     * Moves to the next node on the board.
     * Precondition: The current node is already set (not null).
     * Postcondition: The current node is changed to the next node.
     * 
     * @exception IllegalStateException if the current node is not set (if the current node is null)
     */
    public void moveToNextNode() {
        if (this.currentNode == null) {
            throw new IllegalStateException("Error: current node is not set.");
        }

        this.currentNode = this.currentNode.getNext();
    }

    /**
     * Simulates the game process when the player start a move on the given boardspace.
     * Returns true if it allows the user to make another move. 
     * Precondition: The player chose a valid boardspace and side.
     * Postcondition: Board is updated.
     * 
     * @param space starting board space
     * @param side false -> top (B), true -> bottom (A)
     * @return true if the move allows the user a second move 
     */
    public boolean startMoveOn(BoardSpace space, boolean side) {
        BoardSpace myMancala = side ? BoardSpace.BM : BoardSpace.AM;
        setCurrentNode(space);
        int totalStones = currentNode.getStones();
        changeStonesOfCurrentNode(-totalStones);
        while (totalStones > 0) {
            moveToNextNode();
            changeStonesOfCurrentNode(1);
            totalStones--;
        }
        if(currentNode.getBoardSpace() == myMancala) {
            return true;
        }

        if(currentNode.getStones() == 1) {
            Node oppositeNode = currentNode.getOpposite();
            Node mancalaNode = nodes.get(myMancala);
            int oppositeStone = oppositeNode.getStones();
            int total = oppositeStone + currentNode.getStones();
            oppositeNode.setStones(0);
            currentNode.setStones(0);
            mancalaNode.setStones(mancalaNode.getStones() + total);
        }

        return false;
    }

    /**
     * Returns true if either side's pits are all empty, signaling game over.
     * 
     * @return true if the game is over, false if not
     */
    public boolean isGameOver() {
        int totalAstones = 0, totalBstones = 0;
        for(int i = 0; i < aPits.length - 1; i++) {
            totalAstones += getStoneCount(aPits[i]);
            totalBstones += getStoneCount(bPits[i]);
        }
        return totalAstones == 0 || totalBstones == 0;
    }

    /**
     * Sweeps all remaining pit stones into their respective mancalas.
     * Called once isGameOver() returns true.
     */
    public void sweepRemainingStones() {
        // The last entry of aPits/bPits is the mancala itself; skip it so we
        // don't overwrite its accumulated stones with zero.
        Node aMancala = nodes.get(BoardSpace.AM);
        Node bMancala = nodes.get(BoardSpace.BM);
        for (int i = 0; i < aPits.length - 1; i++) {
            Node aNode = nodes.get(aPits[i]);
            aMancala.setStones(aMancala.getStones() + aNode.getStones());
            aNode.setStones(0);

            Node bNode = nodes.get(bPits[i]);
            bMancala.setStones(bMancala.getStones() + bNode.getStones());
            bNode.setStones(0);
        }
    }

    /**
     * Returns a snapshot of the current stone counts for every board space.
     * 
     * @return a snapshot of the current game
     */
    public HashMap<BoardSpace, Integer> getSnapshot() {
        HashMap<BoardSpace, Integer> snapshot = new HashMap<>();
        for (Map.Entry<BoardSpace, Node> entry : nodes.entrySet()) {
            snapshot.put(entry.getKey(), entry.getValue().getStones());
        }
        return snapshot;
    }

    /**
     * Restores stone counts from a previously taken snapshot.
     * 
     * @param snapshot snapshot of the game to be reverted to.
     */
    public void restoreSnapshot(HashMap<BoardSpace, Integer> snapshot) {
        for (Map.Entry<BoardSpace, Integer> entry : snapshot.entrySet()) {
            nodes.get(entry.getKey()).setStones(entry.getValue());
        }
    }

    /**
     * Returns the number of stones in the given board space.
     * Precondition: none
     * Postcondition: none (read-only)
     *
     * @param space the board space to query
     * @return the number of stones in that space
     */
    public int getStoneCount(BoardSpace space) {
        return nodes.get(space).getStones();
    }

    /**
     * Changes the stone count of the current node. Will subtract if the input is negative.
     * Precondition: The current node is already set (not null). The input change will not make the stone count in the board space negative.
     * Postcondition: The current node's stone count is changed by the given amount.
     * 
     * @param delta change amount
     * @exception IllegalStateException if the current node is not set (if the current node is null)
     * @exception IllegalArgumentException if the input changes the stone count to a negative number
     */
    public void changeStonesOfCurrentNode(int delta) {
        if (this.currentNode == null) {
            throw new IllegalStateException("Error: current node is not set.");
        }

        int currentStones = this.currentNode.getStones();
        int newStones = delta + currentStones;
        if (newStones < 0) {
            throw new IllegalArgumentException("Error: cannot change the stones in a board space to a negative number. Attempted to set the count to " + newStones + ".");
        }

        this.currentNode.setStones(newStones);
    }


    /**
     * A Node represents a pit or mancala on the board
     */
    private class Node {
        private BoardSpace boardSpace; //enum representing this board space
        private Node opposite; //only applicable to nodes representing pits (for the 'steal' rule): pit adjacent to this pit, null for mancalas
        private Node next; //next node to be visited
        private int stones; //number of stones currently in this space

        /**
         * Constructs a Node object.
         * 
         * @param boardSpace board space represented by this node
         * @param stones stones held by this node
         */
        public Node(BoardSpace boardSpace, int stones) {
            this.boardSpace = boardSpace;
            this.stones = stones;
            this.opposite = null;
            this.next = null;
        }

        /**
         * Sets the number of stones in this node
         * 
         * @param stones new number of stones
         */
        public void setStones(int stones) {
            this.stones = stones;
        }

        /**
         * Sets the 'opposite' node of this node. Used for the "steal" rule.
         * 
         * @param opposite opposite node
         */
        public void setOpposite(Node opposite) {
            this.opposite = opposite;
        }

        /**
         * Sets the next node to be traversed in the MancalaLinkedList.
         * 
         * @param next next node
         */
        public void setNext(Node next) {
            this.next = next;
        }

        /**
         * Returns the number of stones this node holds.
         * 
         * @return number of stones held by this node
         */
        public int getStones() {
            return this.stones;
        }

        /**
         * Returns the opposite node of this node.
         * 
         * @return opposite node
         */
        public Node getOpposite() {
            return this.opposite;
        }

        /**
         * Returns the next node in the MancalaLinkedList.
         * 
         * @return next node
         */
        public Node getNext() {
            return this.next;
        }

        /**
         * Returns the BoardSpace enum represented by this node.
         * 
         * @return BoardSpace enum represented by this node
         */
        public BoardSpace getBoardSpace() {
            return this.boardSpace;
        }
    }
}