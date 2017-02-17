package com.bfs.v6;

import java.util.HashSet;
import java.util.Set;

import com.bfs.Node;

public class Demo6 {

	public static void main(String[] args) {
		Graph map = new Graph();
		// 构建节点
		// 构造需要点对象
		Node a = new Node("a");
		Node b = new Node("b");
		Node c = new Node("c");
		Node d = new Node("d");
		Node e = new Node("e");
		Node f = new Node("f");
		Node g = new Node("g");
		Node h = new Node("h");

		// 构建路径
		map.addPath(a, b, 1);
		map.addPath(a, c, 2);
		map.addPath(a, d, 3);
		map.addPath(a, h, 4);

		map.addPath(b, c, 4);
		map.addPath(b, g, 5);

		map.addPath(d, e, 6);

		map.addPath(e, f, 7);
		map.addPath(e, g, 8);

		map.addPath(h, g, 9);

		// 创建路径搜索器(每次搜索都需要创建新的MapSearcher)
		ImplAlgorithm searcher = new ImplAlgorithm();
		// 创建关闭节点集合
		Set<Node> closeNodeIdsSet = new HashSet<Node>();
		// closeNodeIdsSet.add("C");

		// 设置初始节点
		searcher.init(a, map, closeNodeIdsSet);
		// 获取结果
		SearchResult<String> result = searcher.getResult(g);
		System.out.println(result);
		searcher.printPathInfo();
	}
}
