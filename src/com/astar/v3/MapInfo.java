package com.astar.v3;

public class MapInfo {
	public int[][] maps; // 二维数组的地图
	public int width; // 地图的宽
	public int hight; // 地图的高
	public Node start; // 起始结点
	public Node end; // 最终结点

	public MapInfo(int[][] maps,Node start, Node end) {
		this.maps = maps;
		this.width = this.maps[0].length;
		this.hight = this.maps.length;
		this.start = start;
		this.end = end;
	}
}
