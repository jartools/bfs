package com.maps.v1;

import java.util.HashSet;
import java.util.Set;

import com.bfs.Node;
import com.maps.v1.MapSearcher.SearchResult;
import com.maps.v1.Maps.MapBuilder;

public class DemoMap1 {

	public static void main(String[] args) {
		// test();
		test3();
	}

	/**
	 * 测试方法
	 */
	static void test() {

		MapBuilder<String> mapBuilder = new Maps.MapBuilder<String>().create();
		// 构建节点
		mapBuilder.addNode(Maps.Node.valueOf("A"));
		mapBuilder.addNode(Maps.Node.valueOf("B"));
		mapBuilder.addNode(Maps.Node.valueOf("C"));
		mapBuilder.addNode(Maps.Node.valueOf("D"));
		mapBuilder.addNode(Maps.Node.valueOf("E"));
		mapBuilder.addNode(Maps.Node.valueOf("F"));
		mapBuilder.addNode(Maps.Node.valueOf("G"));
		mapBuilder.addNode(Maps.Node.valueOf("H"));
		mapBuilder.addNode(Maps.Node.valueOf("I"));
		// 构建路径
		mapBuilder.addPath("A", "B", 1);
		mapBuilder.addPath("A", "F", 2);
		mapBuilder.addPath("A", "D", 4);
		mapBuilder.addPath("A", "C", 1);
		mapBuilder.addPath("A", "G", 5);
		mapBuilder.addPath("C", "G", 3);
		mapBuilder.addPath("G", "H", 1);
		mapBuilder.addPath("H", "B", 4);
		mapBuilder.addPath("B", "F", 2);
		mapBuilder.addPath("E", "F", 1);
		mapBuilder.addPath("D", "E", 1);
		mapBuilder.addPath("H", "I", 1);
		mapBuilder.addPath("C", "I", 1);

		// 构建全局Map
		Maps<String> map = mapBuilder.build();

		// 创建路径搜索器(每次搜索都需要创建新的MapSearcher)
		MapSearcher<String> searcher = new MapSearcher<String>();
		// 创建关闭节点集合
		Set<String> closeNodeIdsSet = new HashSet<String>();
		// closeNodeIdsSet.add("C");

		// 设置初始节点
		searcher.init("A", map, closeNodeIdsSet);
		// 获取结果
		SearchResult<String> result = searcher.getResult("C");
		System.out.println(result);
		// test.printPathInfo();
	}

	static void test2() {

		MapBuilder<Node> mapBuilder = new Maps.MapBuilder<Node>().create();
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
		Node i = new Node("i");

		mapBuilder.addNode(Maps.Node.valueOf(a));
		mapBuilder.addNode(Maps.Node.valueOf(b));
		mapBuilder.addNode(Maps.Node.valueOf(c));
		mapBuilder.addNode(Maps.Node.valueOf(d));
		mapBuilder.addNode(Maps.Node.valueOf(e));
		mapBuilder.addNode(Maps.Node.valueOf(f));
		mapBuilder.addNode(Maps.Node.valueOf(g));
		mapBuilder.addNode(Maps.Node.valueOf(h));
		mapBuilder.addNode(Maps.Node.valueOf(i));

		// 构建路径
		mapBuilder.addPath(a, b, 1);
		mapBuilder.addPath(a, c, 2);
		mapBuilder.addPath(a, d, 4);
		mapBuilder.addPath(a, h, 1);
		mapBuilder.addPath(b, c, 5);
		mapBuilder.addPath(b, g, 3);
		mapBuilder.addPath(d, e, 1);
		mapBuilder.addPath(h, g, 4);
		mapBuilder.addPath(e, f, 2);
		mapBuilder.addPath(e, g, 1);
		mapBuilder.addPath(f, i, 1);
		mapBuilder.addPath(g, i, 1);
		// mapBuilder.addPath(c, i, 1);

		// 构建全局Map
		Maps<Node> map = mapBuilder.build();

		// 创建路径搜索器(每次搜索都需要创建新的MapSearcher)
		MapSearcher<Node> searcher = new MapSearcher<Node>();
		// 创建关闭节点集合
		Set<Node> closeNodeIdsSet = new HashSet<Node>();
		// closeNodeIdsSet.add("C");

		// 设置初始节点
		searcher.init(a, map, closeNodeIdsSet);
		// 获取结果
		SearchResult<Node> result = searcher.getResult(g);
		System.out.println(result);
		searcher.printPathInfo();

		// 设置初始节点
		searcher.init(b, map, closeNodeIdsSet);
		// 获取结果
		result = searcher.getResult(a);
		System.out.println(result);
		// test.printPathInfo();
	}
	
	static void test3() {

		MapBuilder<Node> mapBuilder = new Maps.MapBuilder<Node>().create();
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

		mapBuilder.addNode(Maps.Node.valueOf(a));
		mapBuilder.addNode(Maps.Node.valueOf(b));
		mapBuilder.addNode(Maps.Node.valueOf(c));
		mapBuilder.addNode(Maps.Node.valueOf(d));
		mapBuilder.addNode(Maps.Node.valueOf(e));
		mapBuilder.addNode(Maps.Node.valueOf(f));
		mapBuilder.addNode(Maps.Node.valueOf(g));
		mapBuilder.addNode(Maps.Node.valueOf(h));

		// 构建路径
		mapBuilder.addPath(a, b, 1);
		mapBuilder.addPath(a, c, 2);
		mapBuilder.addPath(a, e, 4);
		
		mapBuilder.addPath(b, c, 3);
		
		mapBuilder.addPath(c, h, 3);
		mapBuilder.addPath(c, a, 2);
		
		mapBuilder.addPath(d, e, 2);
		mapBuilder.addPath(d, f, 5);
		
		mapBuilder.addPath(e, f, 2);
		mapBuilder.addPath(f, g, 1);
		
		mapBuilder.addPath(h, g, 1);

		// 构建全局Map
		Maps<Node> map = mapBuilder.build();

		// 创建路径搜索器(每次搜索都需要创建新的MapSearcher)
		MapSearcher<Node> searcher = new MapSearcher<Node>();
		// 创建关闭节点集合
		Set<Node> closeNodeIdsSet = new HashSet<Node>();
		// closeNodeIdsSet.add("C");

		// 设置初始节点
		searcher.init(b, map, closeNodeIdsSet);
		// 获取结果
		SearchResult<Node> result = searcher.getResult(e);
		System.out.println(result);
		searcher.printPathInfo();
	}
}
