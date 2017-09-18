package com.astar.v4;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class ImplAlgorithm implements IAlgorithm {
	/**
	 * 相邻的代价
	 */
	float Weight_AdJoin = 1;

	/**
	 * 斜线的代价
	 */
	float Weight_Slash = 2;

	SearchSlash m_emSlash = SearchSlash.None;

	public ImplAlgorithm(float weightAdJoin, SearchSlash searchSlash) {
		Weight_AdJoin = weightAdJoin;
		double d = Math.pow(Weight_AdJoin, 2) * 2;
		Weight_Slash = (float) Math.sqrt(d);
		this.m_emSlash = searchSlash;
	}

	public ImplAlgorithm(float weightAdJoin) {
		this(weightAdJoin, SearchSlash.SlashJugde);
	}

	public ImplAlgorithm(SearchSlash searchSlash) {
		this(1, searchSlash);
	}

	Graph graph;
	Node start;
	Node end;

	Queue<Node> openList = new PriorityQueue<Node>(); // 优先队列(升序)
	List<Node> closeList = new ArrayList<Node>();

	@Override
	public Node perform(Graph graph, Node start, Node end, Node[] dynamicClose) {
		openList.clear();
		closeList.clear();
		openList.add(start);

		this.graph = graph;
		this.start = start;
		this.end = end;

		this.start.SetStartVal();
		this.end.SetEndVal();

		if (dynamicClose != null && dynamicClose.length > 0) {
			for (int i = 0; i < dynamicClose.length; i++) {
				closeList.add(dynamicClose[i]);
			}
		}

		computePath();

		if (this.end.parent == null && isNearlay()) {
			return graph.GetNearlyNode();
		}
		return this.end;
	}

	protected void computePath() {
		Node current;
		while (openList.size() > 0) {
			if (isInCloseList(end.x, end.y)) {
				break;
			}
			current = openList.poll();
			closeList.add(current);

			addNeighborNodeInOpen(current);
		}
	}

	boolean isInCloseList(int x, int y) {
		int lens = closeList.size();
		Node node;
		for (int i = 0; i < lens; i++) {
			node = closeList.get(i);
			if (node.x == x && node.y == y)
				return true;
		}
		return false;
	}

	Node GetNodeInOpenList(int x, int y) {
		if (openList.isEmpty())
			return null;

		for (Node node : openList) {
			if (node.x == x && node.y == y)
				return node;
		}
		return null;
	}

	Node GetNodeToOpen(int nX, int nY) {
		if (isInCloseList(nX, nY))
			return null;

		Node node = this.graph.getNode(nX, nY);
		if (node == null || node.isRed())
			return null;
		return node;
	}

	void addNeighborNodeInOpen(Node current) {
		int x = current.x;
		int y = current.y;
		// 上
		addNeighborNodeInOpen(current, x, y + 1, Weight_AdJoin);
		// 下
		addNeighborNodeInOpen(current, x, y - 1, Weight_AdJoin);
		// 左
		addNeighborNodeInOpen(current, x - 1, y, Weight_AdJoin);
		// 右
		addNeighborNodeInOpen(current, x + 1, y, Weight_AdJoin);

		boolean isNotSlash = this.m_emSlash == SearchSlash.None
				|| this.m_emSlash == SearchSlash.Nearly;
		
		if (isNotSlash)
			return;

		boolean isLeftUp = true;
		boolean isLeftDown = true;
		boolean isRightDown = true;
		boolean isRightUp = true;

		boolean isJugdeSlash = this.m_emSlash == SearchSlash.SlashJugde
				|| this.m_emSlash == SearchSlash.NearlySlashJugde;

		if (isJugdeSlash) {
			int nullCount = 0;
			Node up = GetNodeToOpen(x, y + 1);
			nullCount = (up == null) ? nullCount + 1 : nullCount;
			Node down = GetNodeToOpen(x, y - 1);
			nullCount = (down == null) ? nullCount + 1 : nullCount;
			Node left = GetNodeToOpen(x - 1, y);
			nullCount = (left == null) ? nullCount + 1 : nullCount;
			Node ritght = GetNodeToOpen(x + 1, y);
			nullCount = (ritght == null) ? nullCount + 1 : nullCount;
			if (nullCount >= 3) {
				if (up == null && down == null) {
					isLeftUp = left != null;
					isLeftDown = isLeftUp;
					isRightUp = ritght != null;
					isRightDown = isRightUp;
				} else {
					isLeftUp = up != null;
					isRightUp = isLeftUp;
					isLeftDown = down != null;
					isRightDown = isLeftDown;
				}
			}
		}

		// 左上
		if (isLeftUp)
			addNeighborNodeInOpen(current, x - 1, y + 1, Weight_Slash);

		// 左下
		if (isLeftDown)
			addNeighborNodeInOpen(current, x - 1, y - 1, Weight_Slash);

		// 右下
		if (isRightDown)
			addNeighborNodeInOpen(current, x + 1, y - 1, Weight_Slash);

		// 右上
		if (isRightUp)
			addNeighborNodeInOpen(current, x + 1, y + 1, Weight_Slash);
	}

	/**
	 * “曼哈顿” 距离
	 */
	int calcMhDistance(int x1, int y1, int x2, int y2) {
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}

	// 当前点到目的点的权重
	int calcHWeight(int x, int y) {
		return calcMhDistance(end.x, end.y, x, y);
	}

	boolean isCurrNode(Node curr, int x, int y) {
		return curr.x == x && curr.y == y;
	}

	boolean isEndNode(int x, int y) {
		return isCurrNode(end, x, y);
	}

	void addNeighborNodeInOpen(Node current, int nX, int nY, float weight) {
		Node node = GetNodeToOpen(nX, nY);
		if (node == null)
			return;

		float gWeight = current.getgWeight() + weight;

		Node nodeInOpen = GetNodeInOpenList(nX, nY);
		if (nodeInOpen == null) {
			int hWeight = calcHWeight(nX, nY);
			if (isEndNode(nX, nY)) {
				nodeInOpen = end;
			} else {
				nodeInOpen = node;
			}
			nodeInOpen.parent = current;
			nodeInOpen.setgWeight(gWeight);
			nodeInOpen.sethWeight(hWeight);
			openList.add(nodeInOpen);
		} else if (nodeInOpen.getgWeight() > gWeight) {
			nodeInOpen.parent = current;
			nodeInOpen.setgWeight(gWeight);
		}
		nodeInOpen.SetSeachVal();
	}

	@Override
	public boolean isNearlay() {
		return m_emSlash == SearchSlash.Nearly
				|| m_emSlash == SearchSlash.NearlySlash
				|| m_emSlash == SearchSlash.NearlySlashJugde;
	}
}
