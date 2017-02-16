package com.bfs.v5;
import java.util.Map;

import com.bfs.Node;


public interface V5IAlgorithm {
	/**
     * 执行算法
     */
    void perform(V5Graph g, Node sourceVertex);

    /**
     * 得到路径
     */
    Map<Node, Node> getPath();
}
