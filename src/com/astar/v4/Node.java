package com.astar.v4;

/**
 * Astar 的界面点
 * 
 * @author Canyon / 龚阳辉 Time : 2017-09-16 15:00
 */
public class Node implements Comparable<Node> {

	public final static int BAR = 1; // 障碍值
	public final static int PATH = 2; // 路径
	public final static int SEARCH = 3; // 寻过的点
	public final static int START = 4; // 起点
	public final static int END = 5; // 终点

	/**
	 * 标识 = x_y
	 */
	public String label = "";

	// 坐标
	public int x = 0;
	public int y = 0;

	/**
	 * 值 0 可行走区域, 1 不个行走区域(障碍)
	 */
	public int val = 0;

	private int defVal = 0;

	public Node(int x, int y) {
		this(x, y, 0);
	}

	public Node(int x, int y, int val) {
		super();
		this.x = x;
		this.y = y;
		this.val = val;
		this.label = String.format("%s_%s", this.x, this.y);
		this.defVal = val;
	}

	// 可行走区域 - 绿色
	public boolean isGreen() {
		return val != BAR;
	}

	// 非行走区域 - 红色
	public boolean isRed() {
		return val == BAR;
	}
	
	public boolean isSearched(){
		return val == SEARCH;
	}
	
	public String getValStr(){
		switch (val) {
		case SEARCH:
			return "s";
		case START:
			return "B";
		case END:
			return "E";
		default:
			break;
		}
		return String.valueOf(val);
	}

	// ================== 以下用于计算 begin ==================
	// 父节点
	public Node parent;

	// 起点 到 当前点 的代价(准确值)
	private float gWeight;

	// 当前点 到 目标点 的代价(估计值)
	private float hWeight;

	// 总价值
	public float fWeight;

	public float getgWeight() {
		return gWeight;
	}

	public void setgWeight(float gWeight) {
		this.gWeight = gWeight;
		CalcAllWeight();
	}

	public float gethWeight() {
		return hWeight;
	}

	public void sethWeight(float hWeight) {
		this.hWeight = hWeight;
		CalcAllWeight();
	}

	private void CalcAllWeight() {
		this.fWeight = this.gWeight + this.hWeight;
	}

	public void SetSeachVal() {
		if (val == BAR || val == START || val == END)
			return;
		val = SEARCH;
	}

	public void SetPathVal() {
		if (val == BAR || val == START || val == END)
			return;
		val = PATH;
	}

	public void SetStartVal() {
		if (val == BAR)
			return;
		val = START;
	}

	public void SetEndVal() {
		if (val == BAR)
			return;
		val = END;
	}

	public void ClearCalc() {
		this.parent = null;
		this.gWeight = 0;
		this.hWeight = 0;
		this.fWeight = 0;
		this.val = this.defVal;
	}

	// ================== 以下用于计算 end ==================

	@Override
	public int compareTo(Node other) {
		if (other == null)
			return -1;

		if (this.fWeight > other.fWeight)
			return 1;

		if (this.fWeight < other.fWeight)
			return -1;

		return 0;
	}

}
