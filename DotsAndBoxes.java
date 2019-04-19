import java.util.Scanner;
import java.util.Random;

public class DotsAndBoxes {

	public static void main (String[] args) {

		int plys = 0;
		int rows = 0;
		int cols = 0;

		Scanner input = new Scanner(System.in);

		System.out.println("How many plys for AI to search?");
		plys = input.nextInt();

		System.out.println("How many rows on board (of boxes)?");
		rows = input.nextInt();

		System.out.println("How many columns on board (of boxes)?");
		cols = input.nextInt();

		rows = rows * 2 + 1;
		cols = cols * 2 + 1;

		int maxlines = (rows * cols) / 2;
		int totallines = 0;

		/* These numbers will be used to represent things in the array
		 * 0 = dot
		 * 7 = blank space
		 * 9 = horizontal line
		 * 11 = vertical line
		 */

		int[][] board = new int[rows][cols];

		//Generating the random numbers into the "boxes" as well as empty "lines" into the array
		for (int i = 0; i < rows; i++ ) {
			for (int j = 0; j < cols; j ++) {

				//When row is odd, and column is odd
				if (i % 2 != 0 && j % 2 != 0) {
					board[i][j] = new Random().nextInt(5 - 1 + 1) + 1;
				}
				//Else when row is even and column is odd, or when row is odd and column is even
				else if ((i % 2 == 0 && j % 2 != 0) || (i % 2 != 0 && j % 2 == 0)) {
					board[i][j] = 7;
				}
			}
		}

		int p1score = 0;
		int p2score = 0;
		Board game = new Board(board, rows, cols, p1score, p2score, false, totallines, maxlines);

		String direction = "";

		//Main while loop to play the game
		while (game.totallines < game.maxlines) {

			game.aimove = false;

			System.out.println();
			System.out.println("Move: Player 1");
			game.printboard();

			int row = 0;
			int col = 0;
			System.out.println("Choose row to place line in");
			row = input.nextInt();
			System.out.println("Choose col to place line in");
			col = input.nextInt();

			//Making sure user is choosing an "empty line"
			while (game.getState()[row][col] != 7) {
				System.out.println("Try again.");
				System.out.println("Choose row to place line in");
				row = input.nextInt();
				System.out.println("Choose col to place line in");
				col = input.nextInt();
			}

			//If the row of line they are placing is even, then it must be a horizontal line
			if (row % 2 == 0) {
				game.getState()[row][col] = 9;
				direction = "horizontal";
			}

			//If the row of line they are placing is odd, then it must be a vertical line
			else if (row % 2 != 0) {
				game.getState()[row][col] = 11;
				direction = "vertical";
			}

			game.totallines++;

			game.updatescore(row, col, direction);

			//Making sure Player 1 didn't just make the last move, and if not then AI can go. Player 1 goes 1st
			if (game.totallines < game.maxlines) {
				Minimax ai = new Minimax(game, plys);
				game = ai.move();
			}

		}

		System.out.println("Game Over");
		game.printboard();

		System.out.println("Player 1 Score: " + game.playerscore);
		System.out.println("AI Score: " + game.aiscore);

		if (game.playerscore > game.aiscore) {
			System.out.println("Player 1 Wins.");
		}

		else if (game.playerscore < game.aiscore) {
			System.out.println("AI Wins.");
		}

		else {
			System.out.println("Draw.");
		}

	}

}
