package com.astar.v4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Astar 的 地图
 * 
 * @author Canyon / 龚阳辉 Time : 2017-09-16 15:22
 */
public class Graph {

	private IAlgorithm algorithm;

	// 二维数组的地图
	int[][] map;
	
	// 地图节点转为数据处理节点
	Map<String, Node> nodes = new HashMap<String, Node>();

	public void setAlgorithm(IAlgorithm algorithm) {
		this.algorithm = algorithm;
	}

	public Map<String, Node> getMapNode() {
		return nodes;
	}

	public Graph(IAlgorithm algorithm) {
		setAlgorithm(algorithm);
	}

	public void addNode(Node en) {
		if (!nodes.containsValue(en))
			nodes.put(en.label, en);
	}

	public Node getNode(String label) {
		if (nodes.containsKey(label)) {
			return nodes.get(label);
		}
		return null;
	}

	public Node getNode(int x, int y) {
		String label = String.format("%s_%s", x, y);
		return getNode(label);
	}

	public void InitGraph(int[][] map) {
		this.map = map;
		this.nodes.clear();
		if (this.map == null || this.map.length <= 0)
			return;
		int numRow = map.length;
		int numColumn = 0;
		
		int[] columns;
		Node node;
		for (int y = 0; y < numRow; y++) {
			columns = map[y];
			numColumn = columns.length;
			if (numColumn <= 0)
				continue;
			
			for (int x = 0; x < numColumn; x++) {
				node = new Node(x, y, columns[x]);
				addNode(node);
			}
		}
	}

	// 清除拥有计算的信息
	public void ClearNodeCalcInfo() {
		for (Node node : nodes.values()) {
			node.ClearCalc();
		}
	}

	public Node findPath(String fmLabel, String toLabel, String[] outLabels) {
		Node start = getNode(fmLabel);
		Node end = getNode(toLabel);
		List<Node> list;
		Node[] dynamicClose = {};
		if (outLabels != null && outLabels.length > 0) {
			list = new ArrayList<Node>();
			Node out;
			for (int i = 0; i < outLabels.length; i++) {
				out = getNode(outLabels[i]);
				if (out == null)
					continue;
				list.add(out);
			}
			dynamicClose = list.toArray(dynamicClose);
		}
		return findPath(start, end, dynamicClose);
	}

	public Node findPath(Node from, Node to, Node[] dynamicClose) {
		if (from == null || from.val > 0) {
			return null;
		}
		if (to == null || to.val > 0) {
			return null;
		}
		ClearNodeCalcInfo();
		this.algorithm.perform(this, from, to, dynamicClose);
		return to;
	}
	
	public void printSearchResult(Node end){
		while (end != null) {
			end.SetPathVal();
			end = end.parent;
		}
		
		int[] columns;
		int numColumn = 0;
		Node node;
		for (int y = 0; y < map.length; y++) {
			columns = map[y];
			numColumn = columns.length;
			if (numColumn <= 0)
				continue;
			
			for (int x = 0; x < numColumn; x++) {
				node = getNode(x, y);
				System.out.print(node.val + " ");
			}
			System.out.println();
		}
	}
}
