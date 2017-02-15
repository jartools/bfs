package com.bfs.v2;
import java.util.Map;


public interface V2IAlgorithm {
	/**
     * 执行算法
     */
    void perform(V2Graph g, String sourceVertex);

    /**
     * 得到路径
     */
    Map<String, String> getPath();
}
