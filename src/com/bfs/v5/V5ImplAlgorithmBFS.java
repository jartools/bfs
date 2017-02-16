package com.bfs.v5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.bfs.Edge;
import com.bfs.Node;

/**
 * 封装BFS算法
 */
public class V5ImplAlgorithmBFS implements V5IAlgorithm {
	// 保存已经访问过的地点
	private List<Node> visitedVertex = new ArrayList<>();
	// 保存最短路径
	private Map<Node, Node> path = new HashMap<Node, Node>();

	@Override
	public void perform(V5Graph g, Node sourceVertex) {
		if (null == visitedVertex) {
			visitedVertex = new ArrayList<>();
		}
		if (null == path) {
			path = new HashMap<Node, Node>();
		}

		visitedVertex.clear();
		path.clear();

		BFS(g, sourceVertex);
	}

	@Override
	public Map<Node, Node> getPath() {
		return path;
	}

	private void BFS(V5Graph g, Node start) {
		Queue<Node> queue = new LinkedList<Node>();
		// 起点入列
		queue.add(start);

		Node cur, to;
		Map<Node, List<Edge>> map = g.getAdj();
		List<Edge> toList = null;

		boolean isHasCur = false;
		while (!queue.isEmpty()) {
			cur = queue.poll();

			if (!visitedVertex.contains(cur)) {
				visitedVertex.add(cur);
				isHasCur = map.containsKey(cur);
				
				System.out.println("广度遍历 - 查找的节点是：" + cur.label + (isHasCur ? "" : (" - Null Edge,Node = " + cur)));

				if (!isHasCur) {
					continue;
				}

				toList = map.get(cur);

				for (int i = 0; i < toList.size(); i++) {
					// 把它的下一层，加入到队列中
					to = toList.get(i).end;
					queue.add(to);
					path.put(to, cur);
				}
			}
		}
	}
}
