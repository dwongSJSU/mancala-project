import java.util.HashMap;

/*
 * MancalaLinkedList uses a circular linked list to represent the game board.
 */
public class MancalaLinkedList {
    private HashMap<String, Node> nodes; //map to easily access nodes
    private Node boardStart; //"starting" node for the linked list

    public MancalaLinkedList() {
        this.nodes = new HashMap<String, Node>();
        this.boardStart = null;
        initializeBoard();
    }

    /* 
     * Initializes the game board. More specifically, it creates 12 pits with 4 stones each and 2 empty mancalas, and links them together to form the board.
     */
    public void initializeBoard() {
        //each pit starts with 4 stones
        Node a1 = new Node("a1", 4);
        Node a2 = new Node("a2", 4);
        Node a3 = new Node("a3", 4);
        Node a4 = new Node("a4", 4);
        Node a5 = new Node("a5", 4);
        Node a6 = new Node("a6", 4);
        Node b1 = new Node("b1", 4);
        Node b2 = new Node("b2", 4);
        Node b3 = new Node("b3", 4);
        Node b4 = new Node("b4", 4);
        Node b5 = new Node("b5", 4);
        Node b6 = new Node("b6", 4);

        //each mancala starts with 0 stones
        Node aM = new Node("aM", 0);
        Node bM = new Node("bM", 0);


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

        //set opposites
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

        //set a1 as the starting node
        this.boardStart = a1;

        //add nodes to map
        nodes.clear();
        nodes.put(a1.getName(), a1);
        nodes.put(a2.getName(), a2);
        nodes.put(a3.getName(), a3);
        nodes.put(a4.getName(), a4);
        nodes.put(a5.getName(), a5);
        nodes.put(a6.getName(), a6);
        nodes.put(b1.getName(), b1);
        nodes.put(b2.getName(), b2);
        nodes.put(b3.getName(), b3);
        nodes.put(b4.getName(), b4);
        nodes.put(b5.getName(), b5);
        nodes.put(b6.getName(), b6);
        nodes.put(aM.getName(), aM);
        nodes.put(bM.getName(), bM);
    }
 
    /*
     * A Node represents a pit or mancala on the board
     */
    private class Node {
        private String name; //name of the pit
        private Node opposite; //pit adjacent to this node (for the 'steal' rule)
        private Node next; //next node to be visited
        private int stones; //number of stones currently in this pit

        public Node(String name, int stones) {
            this.name = name;
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

        public String getName() {
            return this.name;
        }
    }
}