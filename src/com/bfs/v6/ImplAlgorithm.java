package com.bfs.v6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.bfs.Edge;
import com.bfs.Node;
import com.bfs.SearchResult;

/**
 * 
 * @title  算法 - 迪杰斯特拉 Dijkstra 算法
 * 
 * @author Canyon
 *
 * @time 2017年2月17日 上午10:14:18
 */
public class ImplAlgorithm {
	Graph graph;

	Node start;
	Node traget;

	Set<Node> open = new HashSet<>();
	Set<Node> close = new HashSet<>();

	Map<Node, Integer> path = new HashMap<>();

	Map<String, List<String>> pathInfo = new HashMap<>();

	public Node init(Node source, Graph map, Set<Node> closeSet) {
		Map<String, Node> nodeMap = map.getNodes();
		Node startNode = nodeMap.get(source.label);
		close.add(startNode);

		// 将其他节点放到open
		for (Node node : nodeMap.values()) {
			if (!closeSet.contains(node) && !node.equals(source)) {
				this.open.add(node);
			}
		}

		// 初始路径
		for (Edge edge : startNode.getEdgeList()) {
			Node to = edge.end;
			if (open.contains(to)) {
				String nodeId = to.label;
				path.put(to, edge.distance);
				pathInfo.put(
						nodeId,
						new ArrayList<String>(Arrays.asList(source.label,
								nodeId)));
			}
		}

		for (Node node : nodeMap.values()) {
			if (open.contains(node) && !path.containsKey(node)) {
				path.put(node, Integer.MAX_VALUE);
				pathInfo.put(node.label,
						new ArrayList<String>(Arrays.asList(source.label)));
			}
		}
		this.start = startNode;
		this.graph = map;
		return startNode;
	}

	/**
	 * 递归Dijkstra
	 * 
	 * @param start
	 *            已经选取的最近节点
	 */
	protected void computePath(Node start) {
		Node nearest = getShortestPath();
		if (nearest == null) {
			return;
		}

		close.add(nearest);
		open.remove(nearest);
		// 已经找到结果
		if (nearest == this.traget) {
			return;
		}
		List<Edge> childs = nearest.getEdgeList();
		for (Edge edge : childs) {
			if (open.contains(edge.end)) {// 如果子节点在open中
				Integer newCompute = path.get(nearest) + edge.distance;
				if (path.get(edge.end) > newCompute) {// 之前设置的距离大于新计算出来的距离
					path.put(edge.end, newCompute);

					List<String> path = new ArrayList<String>(
							pathInfo.get(nearest.label));
					path.add(edge.end.label);
					pathInfo.put(edge.end.label, path);
				}
			}
		}
		// computePath(start);// 重复执行自己,确保所有子节点被遍历
		computePath(nearest);// 向外一层层递归,直至所有顶点被遍历
	}

	private Node getShortestPath() {
		Node res = null;
		int minDis = Integer.MAX_VALUE;
		for (Node entry : path.keySet()) {
			if (open.contains(entry)) {
				int distance = path.get(entry);
				if (distance < minDis) {
					minDis = distance;
					res = entry;
				}
			}
		}
		return res;
	}

	public SearchResult<String> getResult(Node target) {
		Node targetNode = graph.getNodes().get(target.label);
		if (targetNode == null) {
			throw new RuntimeException("目标节点不存在!");
		}
		this.traget = targetNode;
		// 开始计算
		this.computePath(start);
		return SearchResult.valueOf(pathInfo.get(target.label), path.get(targetNode));
	}

	public void printPathInfo() {
		for (Entry<String, List<String>> pathInfo : pathInfo.entrySet()) {
			System.out.println(" key = " + pathInfo.getKey());
			System.out.println(" val = " + list2Str(pathInfo.getValue()));
		}
	}

	@SuppressWarnings("rawtypes")
	public String list2Str(List list) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		buffer.append("\n");
		for (Object object : list) {
			buffer.append("\t");
			buffer.append(object);
			buffer.append("\n");
		}
		buffer.append("]");
		String ret = buffer.toString();
		buffer.setLength(0);
		return ret;
	}
}
