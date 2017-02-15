package com.bfs.v2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * (无向)图
 */
public class V2Graph {
	// 图的起点
	private String firstVertax;

	// 邻接表
	private Map<String, List<String>> adj = new HashMap<>();

	// 遍历算法
	private V2IAlgorithm algorithm;

	public V2Graph(V2IAlgorithm algorithm) {
		setAlgorithm(algorithm);
	}

	/**
	 * 执行算法
	 */
	void done() {
		algorithm.perform(this, firstVertax);
	}

	/**
	 * 得到从起点到{@code vertex}点的最短路径
	 * 
	 * @param toVertex
	 * @return
	 */
	public Stack<String> findPathTo(String fromVertex, String toVertex) {

		this.firstVertax = fromVertex;
		done();

		Stack<String> stack = new Stack<>();
		stack.add(toVertex);

		Map<String, String> path = algorithm.getPath();
		String location = path.get(toVertex);
		for (; null != location && !location.equals(firstVertax);) {
			stack.push(location);
			location = path.get(location);
		}
		
		if (!toVertex.equals(firstVertax))
			stack.push(firstVertax);

		return stack;
	}

	/**
	 * 添加一条边
	 */
	public void addEdge(String fromVertex, String toVertex) {
		getOrNew(fromVertex).add(toVertex);
		getOrNew(toVertex).add(fromVertex);
	}

	public List<String> getOrNew(String vertex) {
		List<String> ret = adj.get(vertex);
		if (ret == null) {
			ret = new ArrayList<String>();
			adj.put(vertex, ret);
		}
		return ret;
	}

	/**
	 * 添加一个顶点
	 */
	public void addVertex(String vertex) {
		adj.put(vertex, new ArrayList<String>());
	}

	public Map<String, List<String>> getAdj() {
		return adj;
	}

	public void setAlgorithm(V2IAlgorithm algorithm) {
		this.algorithm = algorithm;
	}

	public void clearAdj() {
		this.adj.clear();
	}
}
