package com.astar.v4;

/**
 * Astar 的 算法
 * @author Canyon / 龚阳辉
 * Time : 2017-09-16 15:29
 */
public interface IAlgorithm {
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
