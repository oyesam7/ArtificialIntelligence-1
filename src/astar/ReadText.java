package astar;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * This class read text and transform it to adjacency matrix!
 * 
 * first step in path finding:
 * 
 * transform search area to a simple two dimensional ArrayList, Each item in the
 * array represents one of the squares on the grid, and its status is recorded
 * as walkable or unwalkable.
 * 
 * @author GuoJunjun
 *
 */
public class ReadText {
	private static BufferedReader in;

	/**
	 * 
	 * @param file
	 *            name
	 *
	 */
	private static void Read(String file) {
		String f;
		if (file.contains("/")) {
			f = "";
		} else {
			f =
					"/Users/junjun/Dropbox/ntnu2014/TDT4136AIintro/TDT4136/src/astar/boards/";
		}
		if (file.contains(".txt")) {
			f = f + file;
		} else {
			f = f + file + ".txt";
		}
		try {
			in = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e) {
			System.out.println("can not open file ERROR:\n" + e);
			return;
		}
	}

	/**
	 * 
	 * @param file
	 *            name
	 * @return matrix
	 */
	public static ArrayList<ArrayList<Character>> getMatrix(String file) {
		Read(file);
		try {
			String read = in.readLine();// read first line
			ArrayList<ArrayList<Character>> matrix = new ArrayList<>();
			ArrayList<Character> al;
			while (read != null) {
				al = new ArrayList<>();
				for (int i = 0; i < read.length(); i++) {
					al.add(i, read.charAt(i));
				}
				matrix.add(al);
				read = in.readLine();
			}
			return matrix;
		} catch (Exception e) {
			System.err.println("getMatrix ERROR:\n" + e);
			return null;
		}
	}
}
