package com.bfs.v7;

import java.util.List;

import com.bfs.Edge;
import com.bfs.Node;

public interface IAlgorithm {
	/**
	 * 执行算法
	 */
	void perform(Graph g, String fmLabel,String toLabel,String[] outLabels);

	int Distance();

	List<Node> GetPathNode();

	List<Edge> GetPathEdge();
}
