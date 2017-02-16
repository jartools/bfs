package com.maps.v3;

import java.util.HashSet;
import java.util.Set;

import com.bfs.Node;

public class DemoMap3 {

	public static void main(String[] args) {
		Graph<Node> map = new Graph<>();
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

		map.addNode(ENDode.valueOf(a));
		map.addNode(ENDode.valueOf(b));
		map.addNode(ENDode.valueOf(c));
		map.addNode(ENDode.valueOf(d));
		map.addNode(ENDode.valueOf(e));
		map.addNode(ENDode.valueOf(f));
		map.addNode(ENDode.valueOf(g));
		map.addNode(ENDode.valueOf(h));

		// 构建路径
		map.addPath(a, b, 1);
		map.addPath(a, c, 2);
		map.addPath(a, e, 4);

		map.addPath(b, c, 3);

		map.addPath(c, h, 3);
		map.addPath(c, a, 2);

		map.addPath(d, e, 2);
		map.addPath(d, f, 5);

		map.addPath(e, f, 2);
		map.addPath(f, g, 1);

		map.addPath(h, g, 1);

		// 创建路径搜索器(每次搜索都需要创建新的MapSearcher)
		ImplAlgorithm<Node> searcher = new ImplAlgorithm<>();
		// 创建关闭节点集合
		Set<Node> closeNodeIdsSet = new HashSet<Node>();
		// closeNodeIdsSet.add("C");

		// 设置初始节点
		searcher.init(a, map, closeNodeIdsSet);
		// 获取结果
		SearchResult<Node> result = searcher.getResult(f);
		System.out.println(result);
		searcher.printPathInfo();
	}
}
