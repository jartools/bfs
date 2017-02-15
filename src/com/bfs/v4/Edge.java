package com.bfs.v4;

/**
 * 
 * @title 跳转边
 * 
 * @author Canyon
 *
 * @time 2017年2月15日 下午6:10:10
 */
public class Edge {
	public Node start;
	public Node end;
	
	public Object obj;
	
	public Edge(Node start, Node end,Object obj) {
		super();
		this.start = start;
		this.end = end;
		this.obj = obj;
	}

	@Override
	public String toString() {
		return "Edge [" + (start != null ? "start=" + start.label + ", " : "")
				+ (end != null ? "end=" + end.label + ", " : "")
				+ (obj != null ? "obj=" + obj : "") + "]";
	}
	
	
}
