using UnityEngine;
using System.Collections;
using System.Collections.Generic;

/// <summary>
/// 类名 : 节点
/// 作者 : Canyon
/// 日期 : 2017-02-17 09:10
/// 功能 : 
/// </summary>
public class EM_Node  {

	// 唯一标识
	public object label;

	// 所属 标识
	public int belongTo = 0;

	// 分类
	public int types = 0;

	public int needLevel = 0;

	public List<EM_Edge> edgeList = new List<EM_Edge> ();

	public EM_Node(object label):this(label,0){
	}

	public EM_Node(object label,int belongTo):this(label,belongTo,0){
	}

	public EM_Node(object label,int belongTo,int types):this(label,belongTo,types,0){
	}

	public EM_Node(object label,int belongTo,int types, int needLevel){
		this.label = label;
		this.belongTo = belongTo;
		this.types = types;
		this.needLevel = needLevel;
	}

	public EM_Edge GetEdge(EM_Node node){
		if (node == null)
			return null;
		
		int lens = edgeList.Count;
		if (lens <= 0)
			return null;

		EM_Edge edg = null;
		for (int i = 0; i < lens; i++) {
			edg = edgeList [i];
			if (node.Equals(edg.end))
				return edg;
		}
		return null;
	}

	public void AddEdge(EM_Edge v){
		if (!this.Equals (v.start))
			return;
		
		if (edgeList.Contains (v))
			return;

		edgeList.Add (v);
	}

	public List<EM_Edge> GetEdgeList(){
		return edgeList;
	}
}
