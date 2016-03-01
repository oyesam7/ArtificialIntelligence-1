package astar;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * inherited from Node
 * <p>
 * extra parameters:<br>
 * <li>w water 100
 * <li>m mountains 50
 * <li>f forests 10
 * <li>g grass lands 5
 * <li>r roads 1
 * 
 * @author GuoJunjun
 *
 */
public class NodeC extends Node {
	/**
	 * cost character
	 * <p>
	 * 
	 * <li>w water 100
	 * <li>m mountains 50
	 * <li>f forests 10
	 * <li>g grasslands 5
	 * <li>r roads 1
	 */
	private char c;
	private ArrayList<NodeC> children;

	public NodeC(int x, int y, int g, int h, char c) {
		super(x, y, g, h);
		this.c = c;
		f += cost();
	}

	@Override
	protected void init() {
		children = new ArrayList<NodeC>();
		f = g + h;
	}

	/**
	 * @return cost character (char)
	 *         <p>
	 *
	 *         <li>w water 100
	 *         <li>m mountains 50
	 *         <li>f forests 10
	 *         <li>g grasslands 5
	 *         <li>r roads 1
	 */
	public char getC() {
		return this.c;
	}

	/**
	 * 
	 * @return cost
	 *         <p>
	 * 
	 *         <li>w water 100
	 *         <li>m mountains 50
	 *         <li>f forests 10
	 *         <li>g grasslands 5
	 *         <li>r roads 1
	 */
	public int cost() {
		int cost = 0;
		switch (this.c) {
		case 'w':
			cost = 100;
			break;
		case 'm':
			cost = 50;
			break;
		case 'f':
			cost = 10;
			break;
		case 'g':
			cost = 5;
			break;
		case 'r':
			cost = 1;
			break;
		case 'B':
			cost = 0;
			break;
		default:
			System.err.println("\nERRER at cost method:" + c + " not found!\n");
		}
		return cost;
	}

	/**
	 * 
	 * @return compare first consider cost then consider F value
	 */
	public static Comparator<NodeC> getCostComparator() {
		return new Comparator<NodeC>() {

			@Override
			public int compare(NodeC o1, NodeC o2) {
				// if (o1.cost() != o2.cost()) {
				// return o1.cost() - o2.cost();
				// }
				return o1.getF() - o2.getF();
			}
		};
	}

	//
	public ArrayList<NodeC> getcChildren() {
		return children;
	}

	/**
	 * 
	 * @param child
	 */
	public void addChild(NodeC child) {
		this.children.add(child);
	}

	/**
	 * 
	 * @return Node size
	 */
	public int size() {
		int sum = 0;
		for (NodeC child : children) {
			sum += child.size();
		}
		return 1 + sum;
	}
}
