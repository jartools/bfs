package com.astar.v4;

import com.astar.v3.AStar_V3;

public class AStar_V4 {
	public static void main(String[] args) {
		Search(1, 5, 12, 1);
		Search(1, 6, 14, 3);
	}
	
	static void Search(int fX,int fY,int eX,int eY){
		ImplAlgorithm.SearchSlash searchSlash = ImplAlgorithm.SearchSlash.SearchSlashNearly;
		Graph graph = new Graph(new ImplAlgorithm(searchSlash));
		graph.InitGraph(AStar_V3.maps);
		Node start = graph.getNode(fX,fY);
		Node end = graph.getNode(eX,eY);
		Node node = graph.findPath(start, end, null);
		if(node != null){
			System.out.println("总代价:" + node.getgWeight());
		}
		graph.printSearchResult(node);
	}
}
