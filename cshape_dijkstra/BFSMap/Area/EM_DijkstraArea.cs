using UnityEngine;
using System.Collections;
using System.Collections.Generic;

/// <summary>
/// 类名 : 算法 - 迪杰斯特拉 Dijkstra 算法
/// 作者 : Canyon
/// 日期 : 2017-02-27 15:40
/// 功能 : 计算相同地图类的创送
/// </summary>
public class EM_DijkstraArea : EM_DijkstraBase, EM_AlgorithmArea {

	#region EM_AlgorithmArea implementation

	public void Perform(EM_GraphBase g, int curMapID,int orgAreaGID,int toAreaGID,int curLev)
	{
		Clear ();

		this.graph = g;

		object key = curMapID + "_" + orgAreaGID;
		this.start = graph.GetNode (key);

		key = curMapID + "_" + toAreaGID;
		this.target = graph.GetNode (key);

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
