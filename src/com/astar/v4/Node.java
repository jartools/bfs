package com.astar.v4;

/**
 * Astar 的界面点
 * 
 * @author Canyon / 龚阳辉 Time : 2017-09-16 15:00
 */
public class Node {
	
	public final static int BAR = 1; // 障碍值
	public final static int PATH = 2; // 路径
	public final static int SEARCH = 3; // 寻过的点
	
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
	
	public Node(int x, int y){
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
	
	public void SetSeachVal(){
		if(val == BAR)
			return;
		val = SEARCH;
	}
	
	public void SetPathVal(){
		if(val == BAR)
			return;
		val = PATH;
	}
	
	public void ClearCalc(){
		this.parent = null;
		this.gWeight = 0;
		this.hWeight = 0;
		this.fWeight = 0;
		this.val = this.defVal;
	}
	// ================== 以下用于计算 end ==================

}
