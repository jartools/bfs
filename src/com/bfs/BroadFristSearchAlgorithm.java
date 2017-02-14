package com.bfs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/**
 * 封装BFS算法
 */
public class BroadFristSearchAlgorithm implements Algorithm {
	// 保存已经访问过的地点
    private List<String> visitedVertex;
    // 保存最短路径
    private Map<String, String> path;


    @Override
    public void perform(Graph g, String sourceVertex) {
        if (null == visitedVertex) {
            visitedVertex = new ArrayList<>();
        }
        if (null == path) {
            path = new HashMap<String, String>();
        }

        BFS(g, sourceVertex);
    }

    @Override
    public Map<String, String> getPath() {
        return path;
    }

    private void BFS(Graph g, String sourceVertex) {
        Queue<String> queue = new LinkedList<String>();
        // 标记起点
        visitedVertex.add(sourceVertex);
        // 起点入列
        queue.add(sourceVertex);

        while (false == queue.isEmpty()) {
            String ver = queue.poll();

            List<String> toBeVisitedVertex = g.getAdj().get(ver);
            for (String v : toBeVisitedVertex) {
                if (false == visitedVertex.contains(v)) {
                    visitedVertex.add(v);
                    path.put(v, ver);
                    queue.add(v);
                }
            }
        }
    }
    
   
    
    public static void main(String[] args) {
    	 // String[] vertex = {"North Gate", "South Gate", "Classroom", "Square", "Toilet", "Canteen"};
    	    Edge[] edges = {
    	            new Edge("North Gate", "Classroom"),
    	            new Edge("North Gate", "Square"),
//    	            new Edge("Classroom", "Toilet"),
//    	            new Edge("Square", "Toilet"),
    	            new Edge("Square", "Canteen"),
    	            new Edge("Toilet", "Square"),
    	            new Edge("Toilet", "South Gate"),
    	    };
    	    
        Graph g = new Graph(new BroadFristSearchAlgorithm());
        
        for (Edge edge : edges) {
			g.addEdge(edge.from, edge.to);
		}	
        g.done();
        
        g.firstVertax = "North Gate";

        Stack<String> result = g.findPathTo("South Gate");
        System.out.println("BFS: From [North Gate] to [South Gate]:");
        while (!result.isEmpty()) {
            System.out.println(result.pop());
        }
    }

}
