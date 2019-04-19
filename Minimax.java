import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//This class handles the AI movements
public class Minimax {

	Board game;
	int plys;

	//Constructor for class, initialized with current state of board after Player 1 makes a move, and the plys AI can search ahead
	Minimax (Board game, int plys) {
		this.game = game;
		this.plys = plys;
		this.game.aimove = true;

	}

	//Move is called from main to initiate AI Move
	public Board move () {

		//Grabs current board
		Node current = new Node(game);

		//Initiates minimax search with current board and specified plys
		Node minimax = search(current, 0, plys);

		//Traverses back up the tree to find the move to make
		Node move = getMove(minimax);

		//Returns the board after move has been made
		return move.board;
	}

	//Recursive Minimax Search, returns Node at max depth
	public Node search (Node current, int depth, int plys) {

		//Checks if we are at the max depth/ply, or if board is complete
		if (depth > plys || current.board.totallines == current.board.maxlines) {
			return current;
		}

		boolean ai = false;

		//At every other depth moves alternate between Player and AI
		if (depth % 2 == 0) {
			ai = false;
		}

		//Gets successors of current node (possible moves)
		List<Node> children = getSuccessors(current, ai);

		Node tmp = null;

		//If its AI's depth/move, find the max node based on the difference in scores
		if (current.board.isAIMove()) {
			Integer value = Integer.MIN_VALUE;

			for (Node child : children) {

				//Runs evaluation function
				child.board.evaluate();

				//Recurses down tree until max depth is reached for a child, then does comparisons
				Node x = search(child, depth + 1, plys);
				if (x.board.difference > value) {
					tmp = x;
					value = x.board.difference;
				}
			}
			return tmp;
		}

		//If its Player's depth/move, find the min node based on the difference in scores
		else {

			Integer value = Integer.MAX_VALUE;

			for (Node child : children) {

				//Runs evaluation function
				child.board.evaluate();

				//Recurses down tree until max depth is reached for a child, then does comparisons
				Node x = search(child, depth + 1, plys);
				if (x.board.difference < value) {
					tmp = x;
					value = x.board.difference;
				}
			}
			return tmp;
		}

	}

	//Starts with the Node returned by Search and traverses up the tree by parents, stopping before the "root" to give us the move to make
	public Node getMove (Node current) {

		Node tmp = current;

		while (tmp.parent.parent != null) {
			tmp = tmp.parent;
		}

		return tmp;
	}

	//Gets List of successors of a Node
	public List<Node> getSuccessors(Node state, boolean value) {

		List<Node> children = new ArrayList<>();
		Board x = state.board;
		int rows = x.rows;
		int cols = x.cols;

		int[][] board = x.getState();

		//Iterates over every position in current board
		for (int i = 0; i < rows; i ++) {
			for (int j = 0; j < cols; j++) {

				//If row is even and column is odd, and is "empty line"
				if ((i % 2 == 0 && j % 2 != 0) && board[i][j] == 7) {
					int[][] temp = copyArray(board, rows, cols);

					//Changes "empty line" to "horizontal line"
					temp[i][j] = 9;
					Board tmp = new Board(temp, x.rows, x.cols, x.playerscore, x.aiscore, value, x.totallines, x.maxlines);
					tmp.updatescore(i, j, "horizontal");
					tmp.totallines++;
					Node child = new Node(tmp);
					child.setParent(state);
					children.add(child);
				}
				//If row is odd and column is even, and is "empty line"
				else if ((i % 2 != 0 && j % 2 == 0) && board[i][j] == 7) {
					int[][] temp = copyArray(board, rows, cols);

					//Changes "empty line" to "vertical"
					temp[i][j] = 11;
					Board tmp = new Board(temp, x.rows, x.cols, x.playerscore, x.aiscore, value, x.totallines, x.maxlines);
					tmp.updatescore(i, j, "vertical");
					tmp.totallines++;
					Node child = new Node(tmp);
					child.setParent(state);
					children.add(child);
				}
			}
		}

		return children;
	}

	//Function to make a copy of a 2D Array
	public int[][] copyArray (int[][] state, int rows, int cols) {

		int[][] temp = new int[rows][cols];

		for (int i = 0; i < rows; i ++) {
			for (int j = 0; j < cols; j++) {
				temp[i][j] = state[i][j];
			}
		}

		return temp;

	}

}
