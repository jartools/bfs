package com.bfs;
import java.util.Map;


public interface Algorithm {
	/**
     * 执行算法
     */
    void perform(Graph g, String sourceVertex);

    /**
     * 得到路径
     */
    Map<String, String> getPath();
}
