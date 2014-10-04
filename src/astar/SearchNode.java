package astar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * To implement the A* algorithm, it is useful to encapsulate search states
 * within nodes in the search tree/graph. The class search-node uses properties
 * such as parent, kids to keep track of neighboring nodes in the graph, while
 * the properties f, g, and h are key node variables in A*. The parent slot
 * always denotes the best parent found so far. Anytime the g value of a node is
 * updated, the f value should change accordingly, and if the node has children,
 * their g and f values may change as well.
 * <p>
 * 
 * CLASS search-node <br>
 * • state - an object describing a state of the search process<br>
 * • g - cost of getting to this node<br>
 * • h - estimated cost to goal<br>
 * • f - estimated total cost of a solution path going through this node; f = g
 * + h • status - open or closed<br>
 * • parent - pointer to best parent node<br>
 * • kids - list of all successor nodes, whether or not this node is currently
 * their best parent.<br>
 * 
 * @author GuoJunjun
 *
 */

public class SearchNode {
	/**
	 * OPEN contains unexpanded nodes.
	 * <p>
	 * 1. Begin at the starting point A and add it to an “open list” of squares
	 * to be considered.<br>
	 * 
	 * 2. Look at all the reachable or walkable squares adjacent to the starting
	 * point, ignoring squares with walls, water, or other illegal terrain. Add
	 * them to the open list, too. For each of these squares, save point A as
	 * its “parent square”. This parent square stuff is important when we want
	 * to trace our path. It will be explained more later.<br>
	 * 
	 * sorted by lowest F cost
	 */
	protected ArrayList<Node> open;
	/**
	 * 3. Drop the starting square A from your open list, and add it to a
	 * “closed list” of squares that you don’t need to look at again for now.
	 * <p>
	 * CLOSED requires no particular ordering, since once a node is added to
	 * CLOSED, it is never removed.<br>
	 * 
	 * CLOSED houses nodes that have already been expanded,
	 */
	protected ArrayList<Node> closed;
	/**
	 * [<br>
	 * y-i1: [x-j1,x-j2,x-j3 ... ]<br>
	 * y-i2: [ ]<br>
	 * y-i3: [ ]<br>
	 * ...<br>
	 * ]
	 */
	protected ArrayList<ArrayList<Character>> matrix;
	/**
	 * ax, ay: A starting point; <br>
	 * bx, by: B Goal point
	 * 
	 */
	protected int ax, ay, bx, by;
	protected Node root;

	/**
	 * @param open
	 * @param closed
	 */
	public SearchNode(String board) {
		this.open = new ArrayList<Node>();
		this.closed = new ArrayList<Node>();
		this.matrix = ReadText.getMatrix(board);
		init();
	}

	/**
	 * @return the open
	 */
	public List<Node> getOpen() {
		return open;
	}

	/**
	 * @return the closed
	 */
	public List<Node> getClosed() {
		return closed;
	}

	/**
	 * 
	 * @param e
	 */
	public void addtoOpen(Node e) {
		open.add(e);
		Collections.sort(open, Node.getFComparator());
	}

	/**
	 * 
	 * @param x
	 *            current matrix position
	 * @param y
	 *            current matrix position
	 * @param g
	 *            root A to current position length
	 * @return new Node(int x, int y, int g, int h);
	 */
	protected Node getNode(int x, int y, int g) {
		int h = Math.abs(x - this.bx) + Math.abs(y - this.by);
		return new Node(x, y, g, h);
	}

	protected void init() {
		printMatrix();
		System.out.println();
		for (int i = 0; i < matrix.size(); i++) {
			for (int j = 0; j < matrix.get(0).size(); j++) {
				if (matrix.get(i).get(j).charValue() == 'A') {
					ax = j;
					ay = i;
				}
				if (matrix.get(i).get(j).charValue() == 'B') {
					bx = j;
					by = i;
				}
			}
		}

		root = getNode(ax, ay, 0);
		open.add(root);
		updateMatrix(agendaLoop());
		matrix.get(by).set(bx, 'B');// set B back
		printMatrix();
		printReport();
	}

	/**
	 * print report information:
	 */
	protected void printReport() {
		System.out.println("\nposition A(ax,ay): " + ax + ", " + ay);
		System.out.println("position B(bx,by): " + bx + ", " + by);
		System.out.println("matrix.size: " + matrix.size() + " x "
				+ matrix.get(0).size() + " = " + matrix.size()
				* matrix.get(0).size());
		System.out.println("open.size(): " + open.size());
		System.out.println("closed.size(): " + closed.size());
		System.out.println("root.size: " + root.size());

		ArrayList<Node> openCopy = new ArrayList<Node>(open);
		Collections.sort(openCopy, Node.getPositionComparator());
		System.out.println("open: " + openCopy);

		ArrayList<Node> closedCopy = new ArrayList<Node>(closed);
		Collections.sort(closedCopy, Node.getPositionComparator());
		System.out.println("closed: " + closedCopy);
		System.out.println(root.getChildren().get(0).getChildren());
	}

	/**
	 * print out matrix
	 */
	protected void printMatrix() {
		for (int i = 0; i < matrix.size(); i++) {
			for (int j = 0; j < matrix.get(0).size(); j++) {
				System.out.print(matrix.get(i).get(j).toString() + " ");
			}
			System.out.println();
		}
	}

	/**
	 * 
	 * @param b
	 * <br>
	 *            From Node B trace back to Node A and add the path to matrix
	 */
	protected void updateMatrix(Node b) {
		if (b.getParent() == null) {
			return;
		} else {
			matrix.get(b.getY()).set(b.getX(), '+');
			System.out.println(b);
			updateMatrix(b.getParent());
		}
	}

	/**
	 * @return the matrix
	 */
	public ArrayList<ArrayList<Character>> getMatrix() {
		return matrix;
	}

	/**
	 * 
	 * @return Node B
	 *         <p>
	 * 
	 *         <li>Loop until the Node B is reached
	 *         <li>Use open and closed to complete the function<br>
	 *         <li>insert route node to A(root) so A become a Tree<br>
	 *         <li>return B as a leaf of tree A(root)
	 */
	protected Node agendaLoop() {
		Node X = null;
		boolean succeed = false;
		while (!succeed) {
			if (open == null) {
				throw new IllegalAccessError("\nopen is empty!\n");
			} else {
				X = open.remove(0); // first X = root ...
				if (!isInClosed(X.getX(), X.getY())) {
					closed.add(X);
				}
				insertRecursive(X, root);// add X to root
				// insertBFS(X);
				if (X.getX() == bx && X.getY() == by) {// found B
					succeed = true;
				} else {
					generateRoute(X);
				}
			}
		}
		return X;
	}

	/* ---------------- recursion method ----------------- */
	protected boolean done;

	/**
	 * 
	 * @param n
	 *            child node
	 * @param ancestor
	 *            node
	 *            <p>
	 *            use recursive method to find the right place and insert the
	 *            Node
	 */
	protected void insertRecursive(Node n, Node ancestor) {
		done = false;
		recursion(n, ancestor);
	}

	/**
	 * 
	 * @param n
	 *            child node
	 * @param ancestor
	 *            node
	 *            <p>
	 *            use recursive method to find the right place and insert the
	 *            Node
	 */
	protected void recursion(Node n, Node ancestor) {
		if (done) return; // done decide when the recursion has to stop
		if (isChild(n, ancestor)) {
			n.setParent(ancestor);
			ancestor.addChild(n);
			// when the child found its parent the recursion need to stop
			done = true;
		} else {
			for (Node node : ancestor.getChildren()) {
				recursion(n, node);
			}
		}
	}

	/* ---------------- recursion method done----------------- */
	/**
	 * 
	 * @param node
	 *            <p>
	 *            use Breadth First Search to find the right place and insert
	 *            the Node to root A
	 */
	protected void insertBFS(Node node) {
		Queue<Node> queue = new LinkedList<Node>();
		queue.add(root);
		while (queue.size() > 0) {
			Node n = queue.remove();
			if (isChild(node, n)) {
				node.setParent(n);
				n.addChild(node);
				// a child always has only one parent, when parent are found
				// insert is done
				return;
			} else {
				for (Node c : n.getChildren()) {
					queue.add(c);
				}
			}
		}
	}

	/**
	 * 
	 * @param child
	 * @param parent
	 * @return true or false
	 */
	protected boolean isChild(Node child, Node parent) {
		int cx, cy, px, py;
		cx = child.getX();
		cy = child.getY();
		px = parent.getX();
		py = parent.getY();

		if (child.getG() == parent.getG() + 1) {
			if (((cx == px - 1 || cx == px + 1) && cy == py)
					|| ((cy == py - 1 || cy == py + 1) && (cx == px))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param Node
	 * 
	 * @return true if successfully generated next route
	 *         <p>
	 * 
	 * 
	 *         n <br>
	 *         Generate: max 4(2,7,4,5) Node & add to open
	 *         <p>
	 * 
	 *         1 2 3<br>
	 *         4 n 5<br>
	 *         6 7 8<br>
	 *         <p>
	 *         [<br>
	 *         y-i1: [x-j1,x-j2,x-j3 ... ]<br>
	 *         y-i2: [ ]<br>
	 *         y-i3: [ ]<br>
	 *         ...<br>
	 *         ]
	 */
	protected boolean generateRoute(Node n) {
		boolean hasNextRoute = false;
		int x, y, lx, ly;
		x = n.getX();
		y = n.getY();
		ly = matrix.size();
		lx = matrix.get(0).size();
		if (y - 1 >= 0) { // '.' movable
			if (matrix.get(y - 1).get(x) != '#' && !isInClosed(x, y - 1)
					&& !isInOpen(x, y - 1)) {// 2
				open.add(getNode(x, y - 1, n.getG() + 1));
				hasNextRoute = true;
			}
		}
		if (y + 1 < ly) {
			if (matrix.get(y + 1).get(x) != '#' && !isInClosed(x, y + 1)
					&& !isInOpen(x, y + 1)) {// 7
				open.add(getNode(x, y + 1, n.getG() + 1));
				hasNextRoute = true;
			}
		}
		if (x - 1 >= 0 && (matrix.get(y).get(x - 1) != '#')
				&& !isInClosed(x - 1, y) && !isInOpen(x - 1, y)) {// 4
			open.add(getNode(x - 1, y, n.getG() + 1));
			hasNextRoute = true;
		}
		if (x + 1 < lx && (matrix.get(y).get(x + 1) != '#')
				&& !isInClosed(x + 1, y) && !isInOpen(x + 1, y)) {// 5
			open.add(getNode(x + 1, y, n.getG() + 1));
			hasNextRoute = true;
		}
		Collections.sort(open, Node.getFComparator());
		return hasNextRoute;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @return true if grid(x:y) is already in closed list
	 */
	protected boolean isInClosed(int x, int y) {
		for (Node node : closed) {
			if (node.getX() == x && node.getY() == y) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @return true if grid(x:y) is already in open list
	 */
	protected boolean isInOpen(int x, int y) {
		for (Node node : open) {
			if (node.getX() == x && node.getY() == y) {
				return true;
			}
		}
		return false;
	}
}
