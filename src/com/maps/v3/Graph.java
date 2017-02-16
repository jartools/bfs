package com.maps.v3;

import java.util.HashMap;
import java.util.Map;

/**
 * 图 - 相当于管理类
 */
public class Graph<T> {

	/**
	 * 所有的节点集合 节点Id - 节点
	 */
	private Map<T, ENDode<T>> nodes = new HashMap<T, ENDode<T>>();

	/**
	 * 获取地图的无向图节点
	 * 
	 * @return 节点Id - 节点
	 */
	public Map<T, ENDode<T>> getNodes() {
		return nodes;
	}

	public Graph<T> addPath(T node1Id, T node2Id, int weight) {
		ENDode<T> node1 = nodes.get(node1Id);
		if (node1 == null) {
			throw new RuntimeException("无法找到节点:" + node1Id);
		}

		ENDode<T> node2 = nodes.get(node2Id);
		if (node2 == null) {
			throw new RuntimeException("无法找到节点:" + node2Id);
		}

		node1.getChilds().put(node2, weight);
		return this;
	}

	public Graph<T> addNode(ENDode<T> node) {
		nodes.put(node.getId(), node);
		return this;
	}
}
