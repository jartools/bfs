using UnityEngine;
using System.Collections;
using System.Collections.Generic;

/// <summary>
/// 类名 : 算法 - 迪杰斯特拉 Dijkstra 算法
/// 作者 : Canyon
/// 日期 : 2017-02-17 10:30
/// 功能 : 
/// </summary>
public class EM_DijkstraBase : EM_IAlgorithm {

	// 图
	protected EM_GraphBase graph;

	// 开始
	protected EM_Node start;

	// 目标
	protected EM_Node target;

	// 打开的节点列表
	protected HashSet<EM_Node> open = new HashSet<EM_Node>();

	// 关闭的节点列表
	protected HashSet<EM_Node> close = new HashSet<EM_Node>();

	// 路径距离
	protected Dictionary<EM_Node,int> path = new Dictionary<EM_Node, int>();

	// 路径信息
	protected Dictionary<EM_Node,List<EM_Node>> pathInfo = new Dictionary<EM_Node, List<EM_Node>>();


	#region EM_IAlgorithm implementation

	// 到达目的地的距离
	public int Distance ()
	{
		if (target == null)
			return 0;
		
		if(path.ContainsKey(target))
			return path [target];
		return 0;
	}

	// 到达目的地的路径节点列表
	public List<EM_Node> GetPathNode ()
	{
		if (target == null)
			return null;
		
		if(pathInfo.ContainsKey(target))
			return pathInfo [target];
		return null;
	}

	// 到达目的地的路径跳转列表
	public List<EM_Edge> GetPathEdge ()
	{
		List<EM_Node> pp = GetPathNode ();
		if (pp == null || pp.Count < 2)
			return null;
		List<EM_Edge> ret = new List<EM_Edge> ();
		int lens = pp.Count;
		EM_Node cur, next;
		EM_Edge edg = null;

		for (int i = 0; i < lens; i++) {
			cur = pp [i];
			if (i < lens - 1)
				next = pp [i + 1];
			else
				next = null;
			edg = cur.GetEdge (next);
			if (edg != null)
				ret.Add (edg);
		}

		return ret;
	}


	#endregion

	// 最短节点
	EM_Node GetShortNode(){
		EM_Node ret = null;
		int minDes = int.MaxValue;
		foreach (var item in path.Keys) {
			if(open.Contains(item)){
				int dis = path[item];
				if(dis < minDes){
					minDes = dis;
					ret = item;
				}
			}
		}
		return ret;
	}

	// 递归算法计算路径，跳转
	protected void ComputePath(){
		EM_Node nearset = GetShortNode ();
		if (nearset == null)
			return;

		close.Add (nearset);
		open.Remove (nearset);

		if (nearset.Equals (target))
			return;

		List<EM_Node> list = null;
		foreach (var item in nearset.GetEdgeList()) {
			if (open.Contains (item.end)) {
				int newDis = path [nearset] + item.distance;
				if (path [item.end] > newDis) {
					path [item.end] = newDis;

					list = new List<EM_Node> (pathInfo[nearset]);
					list.Add (item.end);
					if (pathInfo.ContainsKey (item.end)) {
						pathInfo [item.end] = list;
					} else {
						pathInfo.Add (item.end, list);
					}
				}
			}
		}

		ComputePath ();
	}

	// 清除
	public void Clear()
	{
		this.open.Clear ();
		this.close.Clear ();
		this.path.Clear ();
		this.pathInfo.Clear ();

		this.graph = null;
		this.start = null;
		this.target = null;
	}

	// 执行算法
	protected void Perform(int curLev){
		foreach (var item in graph.GetNodes()) {
			if (start.Equals(item))
				continue;

			if (item.needLevel > curLev)
				continue;

			open.Add (item);
		}

		foreach (var item in start.GetEdgeList()) {
			if (open.Contains (item.end) && !path.ContainsKey (item.end)) {
				path.Add (item.end, item.distance);
				pathInfo.Add (item.end, new List<EM_Node> (){ start, item.end });
			}
		}

		foreach (var item in graph.GetNodes()) {
			if (open.Contains (item) && !path.ContainsKey(item)) {
				path.Add (item, int.MaxValue);
				pathInfo.Add (item, new List<EM_Node> (){ start });
			}
		}

		ComputePath ();
	}
}
