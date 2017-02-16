package com.bfs.v6;

import java.util.Iterator;
import java.util.List;

public class SearchResult<T> {
	/**
	 * 最短路径结果
	 */
	List<T> path;
	/**
	 * 最短距离
	 */
	Integer distance;

	public List<T> getPath() {
		return path;
	}

	public Integer getDistance() {
		return distance;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("path:");
		if (this.path != null) {
			for (Iterator<T> it = this.path.iterator(); it.hasNext();) {
				sb.append(it.next());
				if (it.hasNext()) {
					sb.append("->");
				}
			}
		}else{
			sb.append("is null");
		}
		sb.append("\n").append("distance:").append(distance);
		return sb.toString();
	}

	protected static <T> SearchResult<T> valueOf(List<T> path, Integer distance) {
		SearchResult<T> r = new SearchResult<T>();
		r.path = path;
		r.distance = distance;
		return r;
	}
}
