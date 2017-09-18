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
	
	boolean isSearchSlash = false;

	public ImplAlgorithm(float weightAdJoin,boolean isSearchSlash) {
		Weight_AdJoin = weightAdJoin;
		double d = Math.pow(Weight_AdJoin, 2) * 2;
		Weight_Slash = (float)Math.sqrt(d);
		this.isSearchSlash = isSearchSlash;
	}
	
	public ImplAlgorithm(float weightAdJoin){
		this(weightAdJoin, true);
	}
	
	public ImplAlgorithm(boolean isSearchSlash){
		this(1, isSearchSlash);
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

		if (dynamicClose != null && dynamicClose.length > 0) {
			for (int i = 0; i < dynamicClose.length; i++) {
				closeList.add(dynamicClose[i]);
			}
		}
		
		computePath();
		
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
	
	Node GetNodeInOpenList(int x,int y){
		if(openList.isEmpty())
			return null;
		
		for (Node node : openList) {
			if (node.x == x && node.y == y)
				return node;
		}
		return null;
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

		// 左上
		addNeighborNodeInOpen(current, x - 1, y + 1, Weight_AdJoin);
		// 左下
		addNeighborNodeInOpen(current, x - 1, y - 1, Weight_AdJoin);
		// 右下
		addNeighborNodeInOpen(current, x + 1, y - 1, Weight_AdJoin);
		// 右上
		addNeighborNodeInOpen(current, x + 1, y + 1, Weight_AdJoin);
	}
	
	/**
	 * “曼哈顿” 距离 
	 */
	int calcMhDistance(int x1,int y1,int x2,int y2){
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}
	
	// 当前点到目的点的权重
	int calcHWeight(int x,int y){
		return calcMhDistance(end.x,end.y,x,y);
	}
	
	boolean isCurrNode(Node curr,int x,int y){
		return curr.x == x && curr.y == y;
	}
	
	boolean isEndNode(int x,int y){
		return isCurrNode(end, x, y);
	}

	void addNeighborNodeInOpen(Node current, int nX, int nY, float weight) {
		if(isInCloseList(nX, nY))
			return;
		
		Node node = this.graph.getNode(nX,nY);
		if(node == null || node.val > 0)
			return;
		
		float gWeight = current.getgWeight() + weight;
		
		Node nodeInOpen = GetNodeInOpenList(nX, nY);
		if(nodeInOpen == null){
			int hWeight = calcHWeight(nX, nY);
			if(isEndNode(nX,nY)){
				nodeInOpen = end;
			}else{
				nodeInOpen = node;
			}
			nodeInOpen.parent = current;
			nodeInOpen.setgWeight(gWeight);
			nodeInOpen.sethWeight(hWeight);
			openList.add(nodeInOpen);
		}else if(nodeInOpen.getgWeight() > gWeight){
			nodeInOpen.parent = current;
			nodeInOpen.setgWeight(gWeight);
		}
		nodeInOpen.SetSeachVal();
	}
}
