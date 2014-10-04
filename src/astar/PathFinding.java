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
			System.out
					.println("Choose one board: \n1 \n2 \n3  \n4 \nF ( Finish ) \nyour Choice:");
			Scanner sc = new Scanner(System.in);
			String input = sc.next();
			if (input.toLowerCase().charAt(0) == 'f') {
				System.out.println("Programme Closed!");
				return;
			} else if (input.charAt(0) == '1') {
				board = "board-1-1";
			} else if (input.charAt(0) == '2') {
				board = "board-1-2";
			} else if (input.charAt(0) == '3') {
				board = "board-1-3";
			} else if (input.charAt(0) == '4') {
				board = "board-1-4";
			}
			if (board != null) new SearchNode(board);
		}
	}
}
