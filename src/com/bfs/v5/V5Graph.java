package com.bfs.v5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 图 - 相当于管理类
 */
public class V5Graph {
	// 图的起点
	private Node firstVertax;

	// 地图 - 该地图上的所有传送门
	private Map<Node, List<Edge>> adj = new HashMap<>();

	// 遍历算法
	private V5IAlgorithm algorithm;

	public V5Graph(V5IAlgorithm algorithm) {
		setAlgorithm(algorithm);
	}

	/**
	 * 执行算法
	 */
	void done() {
		algorithm.perform(this, firstVertax);
	}
	
	Edge getEdge(Node fmNode, Node toNode){
		if(toNode == null || fmNode == null)
			return null;
		
		List<Edge> edgeList = getList(fmNode);
		if(edgeList == null)
			return null;
		
		int lens = edgeList.size();
		if(lens <= 0){
			return null;
		}
		Edge tmp = null;
		for (int i = 0; i < lens; i++) {
			tmp = edgeList.get(i);
			if(tmp.end == toNode)
				return tmp;
		}
		return null;
	}

	/**
	 * 得到从起点到{@code vertex}点的最短路径
	 * 
	 * @param toVertex
	 * @return
	 */
	public void findPathTo(Node fromVertex, Node toVertex) {

		this.firstVertax = fromVertex;
		done();

		List<Node> list = new ArrayList<Node>();
		list.add(toVertex);

		Map<Node, Node> path = algorithm.getPath();
		
		Node cur, next;
		
		cur = path.get(toVertex);
		for (; null != cur && !cur.equals(fromVertex);) {
			list.add(cur);
			cur = path.get(cur);
		}

		if (!toVertex.equals(fromVertex))
			list.add(fromVertex);

		Collections.reverse(list);

		List<Object> obj = new ArrayList<Object>();
		Edge edg = null;
		int lens = list.size();
		for (int i = 0; i < lens; i++) {
			cur = list.get(i);			
			System.out.println("-->" + cur.label);
			
			if (i < lens - 1)
				next = list.get(i + 1);
			else
				next = null;
				
			edg = getEdge(cur,next);
			if(edg != null)
				obj.add(edg.obj);
		}

		for (Object object : obj) {
			System.out.println(object);
		}
	}

	/**
	 * 添加一条边
	 */
	public void addEdge(Edge edg) {
		List<Edge>  list = getOrNew(edg.start);
		if(list.contains(edg))
			return;
		list.add(edg);
	}
	
	public void addEdge(Node fromVertex, Node toVertex,Object obj) {
		addEdge(new Edge(fromVertex, toVertex, obj));
	}
	
	public List<Edge> getList(Node vertex) {
		return adj.get(vertex);
	}
	
	public List<Edge> getOrNew(Node vertex) {
		List<Edge> ret = getList(vertex);
		if (ret == null) {
			ret = new ArrayList<Edge>();
			adj.put(vertex, ret);
		}
		return ret;
	}

	/**
	 * 添加一个顶点
	 */
	public void addVertex(Node vertex) {
		adj.put(vertex, new ArrayList<Edge>());
	}

	public Map<Node, List<Edge>> getAdj() {
		return adj;
	}

	public void setAlgorithm(V5IAlgorithm algorithm) {
		this.algorithm = algorithm;
	}

	public void clearAdj() {
		this.adj.clear();
	}
}
