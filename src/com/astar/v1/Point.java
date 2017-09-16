package com.astar.v1;

public class Point {

	// 节点横坐标
	private int x;
	// 节点纵坐标
	private int y;

	// 节点值
	private char value;
	// 父节点
	private Point father;

	/**
	 * 构造函数
	 * 
	 * @param x
	 *            节点横坐标
	 * @param y
	 *            节点纵坐标
	 */
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * 构造函数
	 * 
	 * @param x
	 *            节点横坐标
	 * @param y
	 *            节点纵坐标
	 * @param value
	 *            节点值
	 */
	public Point(int x, int y, char value) {
		this.x = x;
		this.y = y;
		this.value = value;
	}

	public Point getFather() {
		return father;
	}

	public void setFather(Point father) {
		this.father = father;
	}

	public char getValue() {
		return value;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
