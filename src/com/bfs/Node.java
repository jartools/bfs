package com.bfs;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @title 节点 - 相当于地图
 * 
 * @author Canyon
 *
 * @time 2017年2月15日 下午6:10:26
 */
public class Node {
	public String label = "";

	// 地图上的传送门
	public List<Edge> edgeList = new ArrayList<Edge>();
	
	public boolean isValidate = true;
	
	public boolean validate() {
		return isValidate;
	}

	public Node(String label) {
		super();
		this.label = label;
	}

	public Edge getEdge(Node toNode) {
		if (toNode == null)
			return null;

		int lens = edgeList.size();
		if (lens <= 0) {
			return null;
		}
		Edge tmp = null;
		for (int i = 0; i < lens; i++) {
			tmp = edgeList.get(i);
			if (tmp.end == toNode)
				return tmp;
		}
		return null;
	}

	public void addEdge(Edge v) {
		if (v.start != this)
			return;

		if (edgeList.contains(v))
			return;

		edgeList.add(v);
	}

	public List<Edge> getEdgeList() {
		return this.edgeList;
	}

	@Override
	public String toString() {
		return "Node_"+(label != null ? label : "");
	}
	
	public String toString2() {
		return "Node [" + (label != null ? "label=" + label + ", " : "")
				+ (edgeList != null ? "edgeList=" + edgeList.size() : "") + "]";
	}
}
