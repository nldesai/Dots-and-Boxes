import java.util.ArrayList;

//Each node represents a state and its attributes are stored here
public class Node {

	Board board;
	Node parent;
	int depth;
	ArrayList<Node> children;

	//Constructor for Node, initialized with the board
	Node (Board board) {
		this.board = board;
	}

	//Sets the parent for current node
	public void setParent (Node parent) {
        this.parent = parent;
    }

	//Adds a child of current node
	public void addChild(Node current) {
    	this.children.add(current);
    }

}
