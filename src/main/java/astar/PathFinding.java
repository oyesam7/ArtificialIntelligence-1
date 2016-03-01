package astar;

import java.util.Scanner;

/**
 * Path Finding class allows to choose one of the 8 maps; and print out path on
 * map, program stops when f or F is set in;
 * 
 * @author GuoJunjun
 *
 */
public class PathFinding {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		String board = null;
		while (true) {
			boolean grid = false;
			System.out
					.println("Choose one board: \n1 \n2 \n3  \n4 \n5--8 \nF ( Finish ) \nyour Choice:");
			Scanner sc = new Scanner(System.in);
			String input = sc.next();
			char in = input.charAt(0);
			System.out.println("\n" + in + "\n");
			if ((in + "").toLowerCase() == "f") {
				System.out.println("Programme Closed!");
				return;
			} else if (in == '1') {
				board = "board-1-1";
			} else if (in == '2') {
				board = "board-1-2";
			} else if (in == '3') {
				board = "board-1-3";
			} else if (in == '4') {
				board = "board-1-4";
			} else {
				switch (in) {
				case '5':
					board = "board-2-1";
					// break terminates the loop (jumps to the code below it)
					break;
				case '6':
					board = "board-2-2";
					break;
				case '7':
					board = "board-2-3";
					break;
				case '8':
					board = "board-2-4";
					break;
				default:
					System.err.println("\n Error Input: " + in + "\n");
					// continue terminates the rest of the processing of the
					// code within the loop for the current iteration, but
					// continues the loop.
					continue;
				}
				grid = true;
			}
			if (board != null) if (grid)
				new GridsWithCost(board);
			else
				new SearchNode(board);
		}
	}
}
