package astar;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * this Node class has two comparator one compares f value the other compares
 * position value(x:y), so List[Node] can sort by position or f value
 * 
 * @author GuoJunjun
 *
 */
public class Node {
	/**
	 * f = g + h;
	 */
	protected int f;
	/**
	 * g = current node length to A(root)
	 */
	protected int g;
	/**
	 * h = current node to B(goal node)
	 * 
	 */
	protected int h;
	/**
	 * x , y node position on the matrix / ArrayList
	 */
	protected int x, y;

	protected Node parent;
	private ArrayList<Node> children;

	/**
	 * 
	 * @param x
	 *            current x position on the matrix
	 * @param y
	 *            current y position on the matrix
	 * @param g
	 *            current node length to A(root)
	 * @param h
	 *            current node to B(goal node)
	 */
	public Node(int x, int y, int g, int h) {
		this.x = x;
		this.y = y;
		this.h = h;
		this.g = g;
		init();
	}

	protected void init() {
		children = new ArrayList<Node>();
		f = g + h;
	}

	public Node() {
		children = new ArrayList<Node>();
	}

	/**
	 * @return the f= g + h<br>
	 *         h = current node to B(goal node)<br>
	 *         g = current node length to A(root)
	 */
	public int getF() {
		return f;
	}

	/**
	 * @return the g<br>
	 *         g = current node length to A(root)
	 */
	public int getG() {
		return g;
	}

	/**
	 * @param g
	 *            g = current node length to A(root)
	 * 
	 *            g = the movement cost to move from the starting point A to a
	 *            given square on the grid, following the path generated to get
	 *            there.
	 *            or:
	 * 
	 */
	public void setG(int g) {
		this.g = g;
	}

	/**
	 * @return the h<br>
	 *         current node to B(goal node)
	 */
	public int getH() {
		return h;
	}

	/**
	 * @return the x: node position on the matrix / ArrayList
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y: node position on the matrix / ArrayList
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return the parent
	 */
	public Node getParent() {
		return parent;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public void setParent(Node parent) {
		this.parent = parent;
	}

	/**
	 * @return the children
	 */
	public ArrayList<Node> getChildren() {
		return children;
	}

	/**
	 * 
	 * @param child
	 */
	public void addChild(Node child) {
		this.children.add(child);
	}

	/**
	 * 
	 * @return Node size
	 */
	public int size() {
		int sum = 0;
		for (Node child : children) {
			sum += child.size();
		}
		return 1 + sum;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[" + x + ":" + y + "-" + f + "-" + g + "-" + h + "]";
		// return "[x=" + x + " y=" + y + " g=" + g + "]";

		// return "[ x=" + x + " y=" + y + " f=" + f + " g=" + g + " h=" + h
		// + " parent=" + parent + " chrildren: " + children.size() + " ]";
	}

	/**
	 * 
	 * @return compare value first by position value x then by y
	 */
	public static Comparator<Node> getPositionComparator() {
		return new Comparator<Node>() {

			@Override
			public int compare(Node o1, Node o2) {
				if (o1.getX() != o2.getX()) {
					return o1.getX() - o2.getX();
				} else {
					return o1.getY() - o2.getY();
				}
			}
		};
	}

	/**
	 * 
	 * @return compare using F value
	 */
	public static Comparator<Node> getFComparator() {
		return new Comparator<Node>() {

			@Override
			public int compare(Node o1, Node o2) {
				return o1.getF() - o2.getF();
			}
		};
	}
}
