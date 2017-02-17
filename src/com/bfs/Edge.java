package com.bfs;

/**
 * 
 * @title 跳转边 - 地图上的传送门
 * 
 * @author Canyon
 *
 * @time 2017年2月15日 下午6:10:10
 */
public class Edge {
	public Node start;
	public Node end;

	// start - > end 的距离
	public int distance = 1;

	// 传送门数据
	public Object obj;
	
	public Edge(Node start, Node end) {
		this(start, end, 1, null);
	}

	public Edge(Node start, Node end, Object obj) {
		this(start, end, 1, obj);
	}

	public Edge(Node start, Node end, int distance) {
		this(start, end, distance, null);
	}

	public Edge(Node start, Node end, int distance, Object obj) {
		super();
		this.start = start;
		this.end = end;
		this.distance = distance;
		this.obj = obj;
		this.start.addEdge(this);
	}

	@Override
	public String toString() {
		return "Edge [" + (start != null ? "start=" + start.label + ", " : "")
				+ (end != null ? "end=" + end.label + ", " : "")
				+ (obj != null ? "obj=" + obj : "") + "]";
	}

}
