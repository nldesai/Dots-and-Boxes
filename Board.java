//Each Board will hold information of the current state
public class Board {

	int[][] state;
	int rows;
	int cols;
	int maxlines;
	int playerscore;
	int aiscore;
	int totallines;
	boolean aimove;
	int difference;

	//Constructor for Board
	Board (int[][] state, int rows, int cols, int playerscore, int aiscore, boolean aimove, int totallines, int maxlines) {
		this.rows = rows;
		this.cols = cols;
		this.playerscore = playerscore;
		this.aiscore = aiscore;
		this.aimove = aimove;
		this.state = state;
		this.totallines = totallines;
		this.maxlines = maxlines;
	}

	//Returns the state, as a 2D array
	public int[][] getState () {
		return this.state;
	}

	//Updates Player Score
	public void updateplayerscore (int score) {
		this.playerscore += score;
	}

	//Updates AI Score
	public void updateaiscore (int score) {
		this.aiscore += score;
	}

	//Checks if current move is a move for AI
	public boolean isAIMove () {
		return this.aimove;
	}

	//Evaluates the current board
	public void evaluate () {
		this.difference = this.aiscore - this.playerscore;
	}

	/* These numbers will be used to represent things in the array
	 * 0 = dot
	 * 7 = blank space
	 * 9 = horizontal line
	 * 11 = vertical line
	 */

	//Prints the board
	public void printboard() {

		for (int i = 0; i < rows; i++ ) {
			for (int j = 0; j < cols; j ++) {
				if (state[i][j] == 0) {
					System.out.print("." + " ");
				}
				else if (state[i][j] == 7){
					System.out.print(" " + " ");
				}
				else if (state[i][j] == 9) {
					System.out.print("-" + " ");
				}
				else if (state[i][j] == 11) {
					System.out.print("|" + " ");
				}
				else {
					System.out.print(state[i][j] + " ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	//Runs through the board after a move has been and checks if score needs to be updated
	public void updatescore (int row, int col, String direction) {

		if (direction.equals("horizontal")) {

			//If horizontal line is placed anywhere at the top of the board, then it just has to check if a box has been made below
			if (row == 0) {
				if (state[row+1][col-1] == 11 && state[row+1][col+1] == 11 && state[row+2][col] == 9) {
					if (aimove) {
						updateaiscore(state[row+1][col]);
					}
					else {
						updateplayerscore(state[row+1][col]);
					}
				}
			}

			//If horizontal line is placed anywhere at the bottom of the board, then it just has to check if a box has been made above
			else if (row == rows - 1) {
				if (state[row-1][col-1] == 11 && state[row-1][col+1] == 11 && state[row-2][col] == 9) {
					if (aimove) {
						updateaiscore(state[row-1][col]);
					}
					else {
						updateplayerscore(state[row-1][col]);
					}
				}
			}

			//If horizontal line is placed anywhere else on the board, then it has to check if a box has been made above or below
			else {
				if (state[row+1][col-1] == 11 && state[row+1][col+1] == 11 && state[row+2][col] == 9) {
					if (aimove) {
						updateaiscore(state[row+1][col]);
					}
					else {
						updateplayerscore(state[row+1][col]);
					}
				}
				if (state[row-1][col-1] == 11 && state[row-1][col+1] == 11 && state[row-2][col] == 9) {
					if (aimove) {
						updateaiscore(state[row-1][col]);
					}
					else {
						updateplayerscore(state[row-1][col]);
					}
				}
			}

		}

		//If vertical line is placed anywhere at the very left of the board, then it just has to check if a box has been to the right
		else if (direction.equals("vertical")) {

			if (col == 0) {
				if (state[row-1][col+1] == 9 && state[row+1][col+1] == 9 && state[row][col+2] == 11) {
					if (aimove) {
						updateaiscore(state[row][col+1]);
					}
					else {
						updateplayerscore(state[row][col+1]);
					}
				}
			}

			//If vertical line is placed anywhere at the very right of the board, then it just has to check if a box has been to the left
			else if (col == cols - 1) {
				if (state[row-1][col-1] == 9 && state[row+1][col-1] == 9 && state[row][col-2] == 11) {
					if (aimove) {
						updateaiscore(state[row][col-1]);
					}
					else {
						updateplayerscore(state[row][col-1]);
					}
				}
			}

			//If vertical line is placed anywhere else on the board, then it has to check if a box has been to the right or left
			else {
				if (state[row-1][col+1] == 9 && state[row+1][col+1] == 9 && state[row][col+2] == 11) {
					if (aimove) {
						updateaiscore(state[row][col+1]);
					}
					else {
						updateplayerscore(state[row][col+1]);
					}
				}

				if (state[row-1][col-1] == 9 && state[row+1][col-1] == 9 && state[row][col-2] == 11) {
					if (aimove) {
						updateaiscore(state[row][col-1]);
					}
					else {
						updateplayerscore(state[row][col-1]);
					}
				}
			}

		}
	}

}
