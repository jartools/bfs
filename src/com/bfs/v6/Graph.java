package com.bfs.v6;

import java.util.HashMap;
import java.util.Map;

import com.bfs.Edge;
import com.bfs.Node;

/**
 * 图 - 相当于管理类
 */
public class Graph {

	/**
	 * 地图-id --> Node(地图)
	 */
	private Map<String, Node> nodes = new HashMap<String, Node>();

	/**
	 * 获取地图的无向图节点
	 * 
	 * @return 节点Id - 节点
	 */
	public Map<String, Node> getNodes() {
		return nodes;
	}

	public void addNode(Node en) {
		if (!nodes.containsValue(en))
			nodes.put(en.label, en);
	}

	public Graph addPath(Node node1Id, Node node2Id, int weight) {
		addNode(node1Id);
		addNode(node2Id);

		Edge edg = getPath(node1Id, node2Id);
		if (edg == null) {
			edg = new Edge(node1Id, node2Id, weight);
		}
		return this;
	}

	public Edge getPath(Node node1Id, Node node2Id) {
		return node1Id.getEdge(node2Id);
	}
}
