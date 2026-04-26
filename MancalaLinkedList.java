import java.util.HashMap;

/**
 * MancalaLinkedList uses a circular linked list to represent the game board.
 */
public class MancalaLinkedList {
    private HashMap<BoardSpace, Node> nodes; //maps an enum representing the board spaces to their actual node objects
    private Node currentNode; //current board space selected

    public MancalaLinkedList() {
        this.nodes = new HashMap<BoardSpace, Node>();
        initializeBoard();
    }

    /** 
     * Initializes the game board. More specifically, it creates 12 pits with 4 stones each and 2 empty mancalas, and links them together to form the board.
     */
    public void initializeBoard() {
        //each pit starts with 4 stones
        Node a1 = new Node(BoardSpace.A1, 4);
        Node a2 = new Node(BoardSpace.A2, 4);
        Node a3 = new Node(BoardSpace.A3, 4);
        Node a4 = new Node(BoardSpace.A4, 4);
        Node a5 = new Node(BoardSpace.A5, 4);
        Node a6 = new Node(BoardSpace.A6, 4);
        Node b1 = new Node(BoardSpace.B1, 4);
        Node b2 = new Node(BoardSpace.B2, 4);
        Node b3 = new Node(BoardSpace.B3, 4);
        Node b4 = new Node(BoardSpace.B4, 4);
        Node b5 = new Node(BoardSpace.B5, 4);
        Node b6 = new Node(BoardSpace.B6, 4);

        //each mancala starts with 0 stones
        Node aM = new Node(BoardSpace.AM, 0);
        Node bM = new Node(BoardSpace.BM, 0);


        //link together the board
        a1.setNext(a2);
        a2.setNext(a3);
        a3.setNext(a4);
        a4.setNext(a5);
        a5.setNext(a6);
        a6.setNext(aM);
        aM.setNext(b1);
        b1.setNext(b2);
        b2.setNext(b3);
        b3.setNext(b4);
        b4.setNext(b5);
        b5.setNext(b6);
        b6.setNext(bM);
        bM.setNext(a1);

        //set opposites for pits only, mancalas have 'null' opposites
        a1.setOpposite(b6);
        a2.setOpposite(b5);
        a3.setOpposite(b4);
        a4.setOpposite(b3);
        a5.setOpposite(b2);
        a6.setOpposite(b1);

        b6.setOpposite(a1);
        b5.setOpposite(a2);
        b4.setOpposite(a3);
        b3.setOpposite(a4);
        b2.setOpposite(a5);
        b1.setOpposite(a6);

        //add nodes to map
        nodes.clear();
        nodes.put(a1.getBoardSpace(), a1);
        nodes.put(a2.getBoardSpace(), a2);
        nodes.put(a3.getBoardSpace(), a3);
        nodes.put(a4.getBoardSpace(), a4);
        nodes.put(a5.getBoardSpace(), a5);
        nodes.put(a6.getBoardSpace(), a6);
        nodes.put(b1.getBoardSpace(), b1);
        nodes.put(b2.getBoardSpace(), b2);
        nodes.put(b3.getBoardSpace(), b3);
        nodes.put(b4.getBoardSpace(), b4);
        nodes.put(b5.getBoardSpace(), b5);
        nodes.put(b6.getBoardSpace(), b6);
        nodes.put(aM.getBoardSpace(), aM);
        nodes.put(bM.getBoardSpace(), bM);

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
     * Precondition: The player chose the valid boardspace and side.
     * Postcondition: Board is updated.
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
     */
    public boolean isGameOver() {
        boolean aSideEmpty = getStoneCount(BoardSpace.A1) == 0 &&
                             getStoneCount(BoardSpace.A2) == 0 &&
                             getStoneCount(BoardSpace.A3) == 0 &&
                             getStoneCount(BoardSpace.A4) == 0 &&
                             getStoneCount(BoardSpace.A5) == 0 &&
                             getStoneCount(BoardSpace.A6) == 0;
        boolean bSideEmpty = getStoneCount(BoardSpace.B1) == 0 &&
                             getStoneCount(BoardSpace.B2) == 0 &&
                             getStoneCount(BoardSpace.B3) == 0 &&
                             getStoneCount(BoardSpace.B4) == 0 &&
                             getStoneCount(BoardSpace.B5) == 0 &&
                             getStoneCount(BoardSpace.B6) == 0;
        return aSideEmpty || bSideEmpty;
    }

    /**
     * Sweeps all remaining pit stones into their respective mancalas.
     * Called once isGameOver() returns true.
     */
    public void sweepRemainingStones() {
        BoardSpace[] aPits = {BoardSpace.A1, BoardSpace.A2, BoardSpace.A3, BoardSpace.A4, BoardSpace.A5, BoardSpace.A6};
        BoardSpace[] bPits = {BoardSpace.B1, BoardSpace.B2, BoardSpace.B3, BoardSpace.B4, BoardSpace.B5, BoardSpace.B6};

        for (BoardSpace pit : aPits) {
            Node node = nodes.get(pit);
            nodes.get(BoardSpace.AM).setStones(nodes.get(BoardSpace.AM).getStones() + node.getStones());
            node.setStones(0);
        }
        for (BoardSpace pit : bPits) {
            Node node = nodes.get(pit);
            nodes.get(BoardSpace.BM).setStones(nodes.get(BoardSpace.BM).getStones() + node.getStones());
            node.setStones(0);
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

        public Node(BoardSpace boardSpace, int stones) {
            this.boardSpace = boardSpace;
            this.stones = stones;
            this.opposite = null;
            this.next = null;
        }

        public void setStones(int stones) {
            this.stones = stones;
        }

        public void setOpposite(Node opposite) {
            this.opposite = opposite;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public int getStones() {
            return this.stones;
        }

        public Node getOpposite() {
            return this.opposite;
        }

        public Node getNext() {
            return this.next;
        }

        public BoardSpace getBoardSpace() {
            return this.boardSpace;
        }
    }
}