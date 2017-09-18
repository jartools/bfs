package com.astar.v4;

import com.astar.v3.AStar_V3;

public class AStar_V4 {
	public static void main(String[] args) {
		Graph graph = new Graph(new ImplAlgorithm(true));
		graph.InitGraph(AStar_V3.maps);
		Node node = graph.findPath(new Node(1, 5), new Node(12, 1), null);
		graph.printSearchResult(node);
	}
}
