package com.astar.v3;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class AStar_V3 {
	// 来源地址 ：  https://github.com/ClaymanTwinkle/astar
	public final static int BAR = 1; // 障碍值
	public final static int PATH = 2; // 路径
	public final static int SEARCH = 3; // 寻过的点
	public final static int DIRECT_VALUE = 10; // 横竖移动代价
	public final static int OBLIQUE_VALUE = 14; // 斜移动代价

	Queue<Node> openList = new PriorityQueue<Node>(); // 优先队列(升序)
	List<Node> closeList = new ArrayList<Node>();

	/**
	 * 开始算法
	 */
	public void start(MapInfo mapInfo) {
		if (mapInfo == null)
			return;
		// clean
		openList.clear();
		closeList.clear();
		// 开始搜索
		openList.add(mapInfo.start);
		moveNodes(mapInfo);
	}

	/**
	 * 移动当前结点
	 */
	private void moveNodes(MapInfo mapInfo) {
		while (!openList.isEmpty()) {
			if (isCoordInClose(mapInfo.end.x, mapInfo.end.y)) {
				drawPath(mapInfo.maps, mapInfo.end);
				break;
			}
			Node current = openList.poll();
			closeList.add(current);
			addNeighborNodeInOpen(mapInfo, current);
		}
	}

	/**
	 * 在二维数组中绘制路径
	 */
	private void drawPath(int[][] maps, Node end) {
		if (end == null || maps == null)
			return;
		System.out.println("总代价：" + end.G);
		while (end != null) {
			maps[end.y][end.x] = PATH;
			end = end.parent;
		}
	}

	/**
	 * 添加所有邻结点到open表
	 */
	private void addNeighborNodeInOpen(MapInfo mapInfo, Node current) {
		int x = current.x;
		int y = current.y;
		// 左
		addNeighborNodeInOpen(mapInfo, current, x - 1, y, DIRECT_VALUE);
		// 上
		addNeighborNodeInOpen(mapInfo, current, x, y - 1, DIRECT_VALUE);
		// 右
		addNeighborNodeInOpen(mapInfo, current, x + 1, y, DIRECT_VALUE);
		// 下
		addNeighborNodeInOpen(mapInfo, current, x, y + 1, DIRECT_VALUE);
		// 左上
		addNeighborNodeInOpen(mapInfo, current, x - 1, y - 1, OBLIQUE_VALUE);
		// 右上
		addNeighborNodeInOpen(mapInfo, current, x + 1, y - 1, OBLIQUE_VALUE);
		// 右下
		addNeighborNodeInOpen(mapInfo, current, x + 1, y + 1, OBLIQUE_VALUE);
		// 左下
		addNeighborNodeInOpen(mapInfo, current, x - 1, y + 1, OBLIQUE_VALUE);
	}

	/**
	 * 添加一个邻结点到open表
	 */
	private void addNeighborNodeInOpen(MapInfo mapInfo, Node current, int x,
			int y, int value) {
		if (canAddNodeToOpen(mapInfo, x, y)) {
			Node end = mapInfo.end;
			int G = current.G + value; // 计算邻结点的G值
			Node child = findNodeInOpen(x, y);
			if (child == null) {
				int H = calcH(end.x, end.y, x, y); // 计算H值
				if (isEndNode(end.x, end.y, x, y)) {
					child = end;
					child.parent = current;
					child.G = G;
					child.H = H;
				} else {
					child = new Node(x, y, current, G, H);
				}
				openList.add(child);
			} else if (child.G > G) {
				child.G = G;
				child.parent = current;
				openList.add(child);
			}
			mapInfo.maps[y][x] = SEARCH; 
		}
	}

	/**
	 * 从Open列表中查找结点
	 */
	private Node findNodeInOpen(int x, int y) {
		if (openList.isEmpty())
			return null;
		for (Node node : openList) {
			if (node.x == x && node.y == y) {
				return node;
			}
		}
		return null;
	}

	/**
	 * 计算H的估值：“曼哈顿”法，坐标分别取差值相加
	 */
	private int calcH(int x1, int y1, int x2, int y2) {
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}

	/**
	 * 判断结点是否是最终结点
	 */
	private boolean isEndNode(int x1, int y1, int x2, int y2) {
		return x1 == x2 && y1 == y2;
	}

	/**
	 * 判断结点能否放入Open列表
	 */
	private boolean canAddNodeToOpen(MapInfo mapInfo, int x, int y) {
		// 是否在地图中
		if (x < 0 || x >= mapInfo.width || y < 0 || y >= mapInfo.hight)
			return false;
		// 判断是否是不可通过的结点
		if (mapInfo.maps[y][x] == BAR)
			return false;
		// 判断结点是否存在close表
		if (isCoordInClose(x, y))
			return false;

		return true;
	}

	/**
	 * 判断坐标是否在close表中
	 */
	private boolean isCoordInClose(int x, int y) {
		if (closeList.isEmpty())
			return false;
		for (Node node : closeList) {
			if (node.x == x && node.y == y) {
				return true;
			}
		}
		return false;
	}
	
	static public int[][] maps = { 
		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
		{ 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0 },
		{ 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0 },
		{ 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0 },
		{ 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
		{ 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 } };

	public static void main(String[] args) {
		MapInfo info = new MapInfo(maps, new Node(1, 5), new Node(12, 1));
		new AStar_V3().start(info);
		printMap(maps);
	}

	/**
	 * 打印地图
	 */
	public static void printMap(int[][] maps) {
		for (int i = 0; i < maps.length; i++) {
			for (int j = 0; j < maps[i].length; j++) {
				System.out.print(maps[i][j] + " ");
			}
			System.out.println();
		}
	}
}
