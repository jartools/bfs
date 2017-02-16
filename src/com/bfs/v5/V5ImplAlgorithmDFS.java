package com.bfs.v5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class V5ImplAlgorithmDFS implements V5IAlgorithm {
	// 保存已经访问过的地点
	private List<Node> visitedVertex = new ArrayList<>();
	// 保存最短路径
	private Map<Node, Node> path = new HashMap<Node, Node>();

	@Override
	public void perform(V5Graph g, Node start) {
		if (null == visitedVertex) {
			visitedVertex = new ArrayList<>();
		}
		if (null == path) {
			path = new HashMap<Node, Node>();
		}

		visitedVertex.clear();
		path.clear();

		DFS(g, start);
	}

	@Override
	public Map<Node, Node> getPath() {
		return path;
	}

	private void DFS(V5Graph g, Node start) {
		Map<Node, List<Edge>> map = g.getAdj();
		searchTraversing(start, visitedVertex, map);
	}

	void searchTraversing(Node node, List<Node> visited,
			Map<Node, List<Edge>> map) {
		// 判断是否遍历过
		if (visited.contains(node)) {
			return;
		}

		visited.add(node);
		
		boolean isHasCur = map.containsKey(node);
		
		System.out.println("深度遍历 - 查找的节点是：" + node.label + (isHasCur ? "" : (" - Null Edge,Node = " + node)));
		if (!isHasCur) {
			return;
		}

		List<Edge> toList = map.get(node);
		Edge edg = null;
		for (int i = 0; i < toList.size(); i++) {
			edg = toList.get(i);
			searchTraversing(edg.end, visited, map);
			path.put(edg.end, node);
		}
	}

}
