package com.bfs.v7;

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

/**
 * 
 * @title 算法 - 迪杰斯特拉 Dijkstra 算法
 * 
 * @author Canyon
 *
 * @time 2017年2月17日 上午10:14:18
 */
public class ImplAlgorithm implements IAlgorithm {
	Graph graph;

	Node start;
	Node target;

	Set<Node> open = new HashSet<>();
	Set<Node> close = new HashSet<>();

	Map<Node, Integer> path = new HashMap<>();

	Map<Node, List<Node>> pathInfo = new HashMap<>();

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
		if (nearest == this.target) {
			return;
		}
		List<Edge> childs = nearest.getEdgeList();
		for (Edge edge : childs) {
			if (open.contains(edge.end)) {// 如果子节点在open中
				Integer newCompute = path.get(nearest) + edge.distance;
				if (path.get(edge.end) > newCompute) {// 之前设置的距离大于新计算出来的距离
					path.put(edge.end, newCompute);

					List<Node> path = new ArrayList<Node>(pathInfo.get(nearest));
					path.add(edge.end);
					pathInfo.put(edge.end, path);
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

	public void printPathInfo() {
		for (Entry<Node, List<Node>> pathInfo : pathInfo.entrySet()) {
			System.out.println("key = " + pathInfo.getKey());
			System.out.println("val = " + list2Str(pathInfo.getValue()));
			System.out.println();
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

	@Override
	public void perform(Graph g, String fmLabel, String toLabel,
			String[] outLabels) {

		this.open.clear();
		this.close.clear();
		this.path.clear();
		this.pathInfo.clear();

		this.graph = g;
		this.start = graph.GetNode(fmLabel);
		this.target = graph.GetNode(toLabel);

		if (this.start == null) {
			System.out.println("开始点为Null");
			return;
		}

		if (this.target == null) {
			System.out.println("目标点为Null");
			return;
		}

		close.add(start);

		List<String> outs = new ArrayList<String>();
		if (outLabels != null && outLabels.length > 0) {
			for (String item : outLabels) {
				outs.add(item);
			}
		}

		// 将其他节点放到open
		for (Node node : graph.getNodes().values()) {
			if (node.equals(start)) {
				continue;
			}
			if (outs.contains(node.label)) {
				continue;
			}
			this.open.add(node);
		}

		// 初始路径
		for (Edge edge : start.getEdgeList()) {
			if (open.contains(edge.end)) {
				path.put(edge.end, edge.distance);
				pathInfo.put(edge.end,
						new ArrayList<Node>(Arrays.asList(start, edge.end)));
			}
		}

		for (Node node : graph.getNodes().values()) {
			if (open.contains(node) && !path.containsKey(node)) {
				path.put(node, Integer.MAX_VALUE);
				pathInfo.put(node, new ArrayList<Node>(Arrays.asList(start)));
			}
		}

		computePath(start);
	}

	@Override
	public int Distance() {
		if (path.containsKey(target))
			return path.get(target);
		return 0;
	}

	@Override
	public List<Node> GetPathNode() {
		if (pathInfo.containsKey(target))
			return pathInfo.get(target);
		return null;
	}

	@Override
	public List<Edge> GetPathEdge() {
		List<Node> pp = GetPathNode();
		if (pp == null || pp.size() <= 1)
			return null;

		List<Edge> ret = new ArrayList<Edge>();
		int lens = pp.size();
		Node cur, next;
		Edge edg = null;
		for (int i = 0; i < lens; i++) {
			cur = pp.get(i);
			if (i < lens - 1)
				next = pp.get(i + 1);
			else
				next = null;

			edg = cur.getEdge(next);
			if (edg != null)
				ret.add(edg);
		}
		return ret;
	}
}
