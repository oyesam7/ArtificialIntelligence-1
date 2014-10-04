package astar;

import java.util.ArrayList;
import java.util.Collections;

/**
 * inherited from SearchNodeC<br>
 * this class will find a path which consider the path cost also;
 * 
 * @author GuoJunjun
 *
 */
public class GridsWithCost extends SearchNode {
	protected ArrayList<NodeC> open;
	protected ArrayList<NodeC> closed;
	protected NodeC root;

	public GridsWithCost(String board) {
		super(board);
		this.open = new ArrayList<NodeC>();
		this.closed = new ArrayList<NodeC>();
	}

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

	protected boolean generateRoute(NodeC n) {
		boolean hasNextRoute = false;
		int x, y, lx, ly;
		x = n.getX();
		y = n.getY();
		ly = matrix.size();
		lx = matrix.get(0).size();
		if (y - 1 >= 0) { // '.' movable
			if (matrix.get(y - 1).get(x) != '#' && !isInClosed(x, y - 1)
					&& !isInOpen(x, y - 1)) {// 2
				open.add(getNode(x, y - 1, n.getG() + 1, n.getC()));
				hasNextRoute = true;
			}
		}
		if (y + 1 < ly) {
			if (matrix.get(y + 1).get(x) != '#' && !isInClosed(x, y + 1)
					&& !isInOpen(x, y + 1)) {// 7
				open.add(getNode(x, y + 1, n.getG() + 1, n.getC()));
				hasNextRoute = true;
			}
		}
		if (x - 1 >= 0 && (matrix.get(y).get(x - 1) != '#')
				&& !isInClosed(x - 1, y) && !isInOpen(x - 1, y)) {// 4
			open.add(getNode(x - 1, y, n.getG() + 1, n.getC()));
			hasNextRoute = true;
		}
		if (x + 1 < lx && (matrix.get(y).get(x + 1) != '#')
				&& !isInClosed(x + 1, y) && !isInOpen(x + 1, y)) {// 5
			open.add(getNode(x + 1, y, n.getG() + 1, n.getC()));
			hasNextRoute = true;
		}
		Collections.sort(open, NodeC.getCostComparator());
		return hasNextRoute;
	}
}
