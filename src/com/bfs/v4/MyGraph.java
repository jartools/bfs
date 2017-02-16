package com.bfs.v4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.bfs.Edge;
import com.bfs.Node;
import com.bfs.TransformGate;

public class MyGraph {
	public static void main(String args[]) {

		// 构造本对象
		MyGraph search = new MyGraph();

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
		Edge ab = new Edge(a, b, new TransformGate(iD++, fromMapID++, toMapID++));
		Edge ac = new Edge(a, c, new TransformGate(iD++, fromMapID++, toMapID++));
		Edge ad = new Edge(a, d, new TransformGate(iD++, fromMapID++, toMapID++));
		Edge ah = new Edge(a, h, new TransformGate(iD++, fromMapID++, toMapID++));

		// [4-5]
		Edge bc = new Edge(b, c, new TransformGate(iD++, fromMapID++, toMapID++));
		Edge bg = new Edge(b, g, new TransformGate(iD++, fromMapID++, toMapID++));

		// [6]
		Edge de = new Edge(d, e, new TransformGate(iD++, fromMapID++, toMapID++));

		// [7-8]
		Edge ef = new Edge(e, f, new TransformGate(iD++, fromMapID++, toMapID++));
		Edge eg = new Edge(e, g, new TransformGate(iD++, fromMapID++, toMapID++));

		// [9]
		Edge hg = new Edge(h, g, new TransformGate(iD++, fromMapID++, toMapID++));

		// 建立它们的关系
		a.edgeList.add(ab);
		a.edgeList.add(ac);
		a.edgeList.add(ad);
		a.edgeList.add(ah);

		b.edgeList.add(bc);
		b.edgeList.add(bg);

		d.edgeList.add(de);

		e.edgeList.add(ef);
		e.edgeList.add(eg);

		h.edgeList.add(hg);

		// 广度遍历
		System.out.println("广度遍历如下：");
		search.widthSearch(a, g);

	}

	void widthSearch(Node start, Node end) {
		List<Node> visited = new ArrayList<Node>();

		// 用队列存放所有依次要访问元素
		Queue<Node> q = new LinkedList<Node>();
		// 把当前的元素加入到队列尾
		q.add(start);

		Map<Node, Node> path = new HashMap<Node, Node>();
		Node cur, to;
		while (!q.isEmpty()) {
			cur = q.poll();
			// 被访问过了，就不访问，防止死循环
			if (!visited.contains(cur)) {
				visited.add(cur);
				System.out.println("查找的节点是：" + cur.label);
				for (int i = 0; i < cur.edgeList.size(); i++) {
					// 把它的下一层，加入到队列中
					to = cur.edgeList.get(i).end;
					q.add(to);
					path.put(to, cur);
				}
			}
		}

		List<Node> list = new ArrayList<Node>();
		list.add(end);

		Node location = path.get(end);
		for (; null != location && !location.equals(start);) {
			list.add(location);
			location = path.get(location);
		}

		if (!end.equals(start))
			list.add(start);

		Collections.reverse(list);

		int lens = list.size();
		List<Object> obj = new ArrayList<Object>();
		Edge edg = null;
		for (int i = 0; i < lens; i++) {
			cur = list.get(i);
			to = null;
			
			System.out.println("-->" + cur.label);
			
			if (i < lens - 1)
				to = list.get(i + 1);
			edg = cur.getEdge(to);
			if(edg != null)
				obj.add(edg.obj);
		}

		for (Object object : obj) {
			System.out.println(object);
		}
	}
}
