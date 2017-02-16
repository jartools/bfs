package com.bfs.v5;

import java.util.Map;
import java.util.Map.Entry;

import com.bfs.v4.TransformGate;

public class Demo5 {

	public static void main(String[] args) {
		V5ImplAlgorithmBFS BFS = new V5ImplAlgorithmBFS();
		V5IAlgorithm DFS = new V5ImplAlgorithmDFS();

		V5Graph graph = new V5Graph(BFS);
		// 构造需要点对象
		Node a = new Node("a");
		Node b = new Node("b");
		Node c = new Node("c");
		Node d = new Node("d");
		Node e = new Node("e");
		Node f = new Node("f");
		Node g = new Node("g");
		Node h = new Node("h");

		int iD = 0;
		int fromMapID = 0;
		int toMapID = 0;
		// [0-3]
		Edge ab = new Edge(a, b,
				new TransformGate(iD++, fromMapID++, toMapID++));
		Edge ac = new Edge(a, c,
				new TransformGate(iD++, fromMapID++, toMapID++));
		Edge ad = new Edge(a, d,
				new TransformGate(iD++, fromMapID++, toMapID++));
		Edge ah = new Edge(a, h,
				new TransformGate(iD++, fromMapID++, toMapID++));

		// [4-5]
		Edge bc = new Edge(b, c,
				new TransformGate(iD++, fromMapID++, toMapID++));
		Edge bg = new Edge(b, g,
				new TransformGate(iD++, fromMapID++, toMapID++));

		// [6]
		Edge de = new Edge(d, e,
				new TransformGate(iD++, fromMapID++, toMapID++));

		// [7-8]
		Edge ef = new Edge(e, f,
				new TransformGate(iD++, fromMapID++, toMapID++));
		Edge eg = new Edge(e, g,
				new TransformGate(iD++, fromMapID++, toMapID++));

		// [9]
		Edge hg = new Edge(h, g,
				new TransformGate(iD++, fromMapID++, toMapID++));

		graph.addEdge(ab);
		graph.addEdge(ac);
		graph.addEdge(ad);
		graph.addEdge(ah);

		graph.addEdge(bc);
		graph.addEdge(bg);

		graph.addEdge(de);
		graph.addEdge(ef);
		graph.addEdge(eg);
		graph.addEdge(hg);

		// 广度遍历
		graph.setAlgorithm(BFS);
		System.out.println("广度遍历如下：");
		graph.findPathTo(a, g);

		PrintMap(BFS.getPath());

		graph.setAlgorithm(DFS);
		System.out.println("深度遍历如下：");
		graph.findPathTo(a, g);

		PrintMap(DFS.getPath());
	}

	static public void PrintMap(Map<Node, Node> map) {
		System.out.println("========Map begin==========");
		for (Entry<Node, Node> entry : map.entrySet()) {
			System.out.print("Key = (" + entry.getKey() + ")");
			System.out.print(",Val = (" + entry.getValue() + ")");
			System.out.println();
		}
		System.out.println("========Map end==========");
	}

}
