package com.astar.v4;

/**
 * Astar 的 算法
 * @author Canyon / 龚阳辉
 * Time : 2017-09-16 15:29
 */
public interface IAlgorithm {
	
	public enum SearchSlash {
		None, // 不走对角线
		Slash, // 走对角线
		SlashJugde, // 走对角线 并且 要判断上,下，左,右,减少对角线
		Nearly, // 最近点
		NearlySlash,// 最近点
		NearlySlashJugde,// 最近点
	}
	
	/***
	 * 是否取最近点(在无法达到目标的时候)
	 * @return
	 */
	boolean isNearlay();
	
	/**
	 * 算法
	 * @param graph
	 * @param start
	 * @param end
	 * @param dynamicClose 动态关闭节点
	 * @return
	 */
	Node perform(Graph graph,Node start,Node end,Node[] dynamicClose);
}
