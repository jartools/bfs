package com.bfs.v4;

import java.util.ArrayList;
import java.util.List;

public class Node {
	public String label = "";
	public List<Edge> edgeList = new ArrayList<Edge>();

	public Node(String label) {
		super();
		this.label = label;
	}
	
	public Edge getEdge(Node toNode){
		if(toNode == null)
			return null;
		
		int lens = edgeList.size();
		if(lens <= 0){
			return null;
		}
		Edge tmp = null;
		for (int i = 0; i < lens; i++) {
			tmp = edgeList.get(i);
			if(tmp.end == toNode)
				return tmp;
		}
		return null;
	}

	@Override
	public String toString() {
		return "Node [" + (label != null ? "label=" + label + ", " : "")
				+ (edgeList != null ? "edgeList=" + edgeList.size() : "") + "]";
	}
}
