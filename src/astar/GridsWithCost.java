package astar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

/**
 * inherited from SearchNodeC<br>
 * this class will find a path which consider the path cost also;
 * 
 * @author GuoJunjun
 *
 */
public class GridsWithCost extends SearchNode {
	private ArrayList<NodeC> open;
	private ArrayList<NodeC> closed;
	private NodeC root;

	public GridsWithCost(String board) {
		super(board);
	}

	@Override
	protected void init() {
		this.open = new ArrayList<NodeC>();
		this.closed = new ArrayList<NodeC>();
		root = getNode(ax, ay, 0, 'r');
		open.add(root);
		run();
	}

	/**
	 * 
	 * @param x
	 *            current matrix position
	 * @param y
	 *            current matrix position
	 * @param g
	 *            root A to current position length
	 * @param c
	 *            grid condition with character w, m, f, g, r
	 * @return new Node(int x, int y, int g, int h, char c);
	 */
	protected NodeC getNode(int x, int y, int g, char c) {
		int h = Math.abs(x - this.bx) + Math.abs(y - this.by);
		return new NodeC(x, y, g, h, c);
	}

	@Override
	protected NodeC agendaLoop() {
		NodeC X = null;
		boolean succeed = false;
		while (!succeed) {
			if (open == null) {
				throw new IllegalAccessError("\nopen is empty!\n");
			} else {
				X = open.remove(0); // first X = root ...
				if (!isInClosed(X.getX(), X.getY())) {
					closed.add(X);
				}
				// insertRecursive(X, root);// add X to root
				insertBFS(X);
				if (X.getX() == bx && X.getY() == by) {// found B
					succeed = true;
				} else {
					generateRoute(X);
				}
			}
		}
		return X;
	}

	private boolean generateRoute(NodeC n) {
		boolean hasNextRoute = false;
		int x, y, lx, ly;
		x = n.getX();
		y = n.getY();
		ly = matrix.size();
		lx = matrix.get(0).size();
		if (y - 1 >= 0) { // '.' movable
			if (!isInClosed(x, y - 1) && !isInOpen(x, y - 1)) {// 2
				open.add(getNode(x, y - 1, n.getG() + 1, n.getC()));
				hasNextRoute = true;
			}
		}
		if (y + 1 < ly) {
			if (!isInClosed(x, y + 1) && !isInOpen(x, y + 1)) {// 7
				open.add(getNode(x, y + 1, n.getG() + 1, n.getC()));
				hasNextRoute = true;
			}
		}
		if (x - 1 >= 0 && !isInClosed(x - 1, y) && !isInOpen(x - 1, y)) {// 4
			open.add(getNode(x - 1, y, n.getG() + 1, n.getC()));
			hasNextRoute = true;
		}
		if (x + 1 < lx && !isInClosed(x + 1, y) && !isInOpen(x + 1, y)) {// 5
			open.add(getNode(x + 1, y, n.getG() + 1, n.getC()));
			hasNextRoute = true;
		}
		Collections.sort(open, NodeC.getCostComparator());
		return hasNextRoute;
	}

	@Override
	protected boolean isInClosed(int x, int y) {
		for (NodeC node : closed) {
			if (node.getX() == x && node.getY() == y) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected boolean isInOpen(int x, int y) {
		for (NodeC node : open) {
			if (node.getX() == x && node.getY() == y) {
				return true;
			}
		}
		return false;
	}

	protected void insertBFS(NodeC node) {
		Queue<NodeC> queue = new LinkedList<NodeC>();
		queue.add(root);
		while (queue.size() > 0) {
			NodeC n = queue.remove();
			if (isChild(node, n)) {
				node.setParent(n);
				n.addChild(node);
				return;
			} else {
				for (NodeC c : n.getcChildren()) {
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
	private boolean isChild(NodeC child, NodeC parent) {
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

	@Override
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
	}

}
