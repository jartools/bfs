using UnityEngine;
using System.Collections;
using System.Collections.Generic;

/// <summary>
/// 类名 : 算法 - 迪杰斯特拉 Dijkstra 算法
/// 作者 : Canyon
/// 日期 : 2017-02-17 10:30
/// 功能 : 
/// </summary>
public class EM_Dijkstra : EM_DijkstraBase, EM_Algorithm {
	
	#region EM_Algorithm implementation

	public void Perform(EM_GraphBase g, int orgMapId, int toMapId,int curLev)
	{
		Clear ();

		this.graph = g;
		this.start = graph.GetNode (orgMapId);
		this.target = graph.GetNode (toMapId);

		if (this.start == null) {
			Debuger.LogError ("开始点为Null");
			return;
		}

		if (this.target == null) {
			Debuger.LogError ("目标点为Null");
			return;
		}

		this.close.Add (start);

		Perform (curLev);
	}

	#endregion
}
