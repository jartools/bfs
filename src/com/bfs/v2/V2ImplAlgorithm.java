package com.bfs.v2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

import com.bfs.Edge;

/**
 * 封装BFS算法
 */
public class V2ImplAlgorithm implements V2IAlgorithm {
	// 保存已经访问过的地点
	private List<String> visitedVertex = new ArrayList<>();
	// 保存最短路径
	private Map<String, String> path = new HashMap<String, String>();

	@Override
	public void perform(V2Graph g, String sourceVertex) {
		if (null == visitedVertex) {
			visitedVertex = new ArrayList<>();
		}
		if (null == path) {
			path = new HashMap<String, String>();
		}

		visitedVertex.clear();
		path.clear();

		BFS(g, sourceVertex);
	}

	@Override
	public Map<String, String> getPath() {
		return path;
	}

	private void BFS(V2Graph g, String sourceVertex) {
		Queue<String> queue = new LinkedList<String>();
		// 标记起点
		visitedVertex.add(sourceVertex);
		// 起点入列
		queue.add(sourceVertex);

		while (false == queue.isEmpty()) {
			String ver = queue.poll();

			List<String> toBeVisitedVertex = g.getAdj().get(ver);
			for (String v : toBeVisitedVertex) {
				if (false == visitedVertex.contains(v)) {
					visitedVertex.add(v);
					path.put(v, ver);
					queue.add(v);
				}
			}
		}
	}

	public static void main(String[] args) {
		Edge[] edges = {
				new Edge("North Gate", "Classroom"),
				new Edge("North Gate", "Square"),
				// new Edge("Classroom", "Toilet"),
				// new Edge("Square", "Toilet"),
				new Edge("Square", "Canteen"), new Edge("Toilet", "Square"),
				new Edge("Toilet", "South Gate"), };

		V2Graph g = new V2Graph(new V2ImplAlgorithm());
		for (Edge edge : edges) {
			g.addEdge(edge.from, edge.to);
		}

		String fromeVertax = "North Gate";
		String toVertax = "South Gate";

		Stack<String> result = g.findPathTo(fromeVertax,toVertax);
		System.out.println("BFS: From ["+fromeVertax+"] to ["+toVertax+"]:");
		while (!result.isEmpty()) {
			System.out.println(result.pop());
		}
	}

}
