package com.maps.v3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ImplAlgorithm<T> {
	Graph<T> graph;

	ENDode<T> start;
	ENDode<T> traget;

	Set<ENDode<T>> open = new HashSet<>();
	Set<ENDode<T>> close = new HashSet<>();

	Map<ENDode<T>, Integer> path = new HashMap<>();

	Map<T, List<T>> pathInfo = new HashMap<>();

	public ENDode<T> init(T source, Graph<T> map, Set<T> closeSet) {
		Map<T, ENDode<T>> nodeMap = map.getNodes();
		ENDode<T> startNode = nodeMap.get(source);
		close.add(startNode);

		// 将其他节点放到open
		for (ENDode<T> node : nodeMap.values()) {
			if (!closeSet.contains(node.getId())
					&& !node.getId().equals(source)) {
				this.open.add(node);
			}
		}

		// 初始路径
		for (Entry<ENDode<T>, Integer> entry : startNode.getChilds().entrySet()) {
			ENDode<T> node = entry.getKey();
			if (open.contains(node)) {
				T nodeId = node.getId();
				path.put(node, entry.getValue());
				pathInfo.put(nodeId,
						new ArrayList<T>(Arrays.asList(source, nodeId)));
			}
		}

		for (ENDode<T> node : nodeMap.values()) {
			if (open.contains(node) && !path.containsKey(node)) {
				path.put(node, Integer.MAX_VALUE);
				pathInfo.put(node.getId(),
						new ArrayList<T>(Arrays.asList(source)));
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
	protected void computePath(ENDode<T> start) {
		ENDode<T> nearest = getShortestPath();
		if (nearest == null) {
			return;
		}

		close.add(nearest);
		open.remove(nearest);
		// 已经找到结果
		if (nearest == this.traget) {
			return;
		}
		Map<ENDode<T>, Integer> childs = nearest.getChilds();
		for (ENDode<T> child : childs.keySet()) {
			if (open.contains(child)) {// 如果子节点在open中
				Integer newCompute = path.get(nearest) + childs.get(child);
				if (path.get(child) > newCompute) {// 之前设置的距离大于新计算出来的距离
					path.put(child, newCompute);

					List<T> path = new ArrayList<T>(pathInfo.get(nearest
							.getId()));
					path.add(child.getId());
					pathInfo.put(child.getId(), path);
				}
			}
		}
		// computePath(start);// 重复执行自己,确保所有子节点被遍历
		computePath(nearest);// 向外一层层递归,直至所有顶点被遍历
	}

	private ENDode<T> getShortestPath() {
		ENDode<T> res = null;
		int minDis = Integer.MAX_VALUE;
		for (ENDode<T> entry : path.keySet()) {
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

	public SearchResult<T> getResult(T target) {
		ENDode<T> targetNode = graph.getNodes().get(target);
		if (targetNode == null) {
			throw new RuntimeException("目标节点不存在!");
		}
		this.traget = targetNode;
		// 开始计算
		this.computePath(start);
		return SearchResult.valueOf(pathInfo.get(target), path.get(targetNode));
	}
	
	public void printPathInfo() {
		Set<Map.Entry<T, List<T>>> pathInfos = pathInfo.entrySet();
		for (Map.Entry<T, List<T>> pathInfo : pathInfos) {
			System.out.println(pathInfo.getKey() + ":" + pathInfo.getValue());
		}
	}
}
