package com.astar.v3;

public class Node implements Comparable<Node> {

	public int x;
	public int y;
	public Node parent; // 父结点

	/**
	 * G：是个准确的值，是起点到当前结点的代价
	 */
	public int G;

	/**
	 * H：是个估值，当前结点到目的结点的估计代价
	 */
	public int H;

	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Node(int x, int y, Node parent, int g, int h) {
		this(x, y);

		this.parent = parent;
		G = g;
		H = h;
	}

	@Override
	public int compareTo(Node o) {
		if (o == null)
			return -1;
		if (G + H > o.G + o.H)
			return 1;
		else if (G + H < o.G + o.H)
			return -1;
		return 0;
	}
}
