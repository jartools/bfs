package com.maps.v3;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


public class ENDode<T> {
	/**
	 * 节点主键
	 */
	private T id;

	/**
	 * 节点联通路径 相连节点 - 权重
	 */
	private Map<ENDode<T>, Integer> childs = new HashMap<ENDode<T>, Integer>();

	/**
	 * 构造方法
	 * 
	 * @param id
	 *            节点主键
	 */
	public ENDode(T id) {
		this.id = id;
	}

	/**
	 * 获取实例
	 * 
	 * @param id
	 *            主键
	 * @return
	 */
	public static <T> ENDode<T> valueOf(T id) {
		return new ENDode<T>(id);
	}

	/**
	 * 是否有效 用于动态变化节点的可用性
	 * 
	 * @return
	 */
	public boolean validate() {
		return true;
	}

	public T getId() {
		return id;
	}

	public void setId(T id) {
		this.id = id;
	}

	public Map<ENDode<T>, Integer> getChilds() {
		return childs;
	}

	protected void setChilds(Map<ENDode<T>, Integer> childs) {
		this.childs = childs;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.id).append("[");
		for (Iterator<Entry<ENDode<T>, Integer>> it = childs.entrySet()
				.iterator(); it.hasNext();) {
			Entry<ENDode<T>, Integer> next = it.next();
			sb.append(next.getKey().getId()).append("=")
					.append(next.getValue());
			if (it.hasNext()) {
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}
}
