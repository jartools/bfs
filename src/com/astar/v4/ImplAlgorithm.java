package com.astar.v4;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class ImplAlgorithm implements IAlgorithm {
	/**
	 * 相邻的代价
	 */
	double Weight_AdJoin = 1;
	
	/**
	 * 斜线的代价
	 */
	double Weight_Slash = 2;
	
	
	
	public ImplAlgorithm(double weightAdJoin){
		Weight_AdJoin = weightAdJoin;
		double d = Math.pow(Weight_AdJoin, 2) * 2;
		Weight_Slash = Math.sqrt(d);
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
		return null;
	}
	
	protected void computePath(){
		Node current;
		while (openList.size() > 0) {
			if(isInClose(end.x, end.y)){
				break;
			}
			current = openList.poll();
			closeList.add(current);
		}
	}
	
	boolean isInClose(int x, int y){
		int lens = closeList.size();
		Node node;
		for (int i = 0; i < lens; i++) {
			node = closeList.get(i);
			if(node.x == x && node.y == y)
				return true;
		}
		return false;
	}
}
