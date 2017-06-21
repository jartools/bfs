using UnityEngine;
using System.Collections;
using System.Collections.Generic;

/// <summary>
/// 类名 : 图
/// 作者 : Canyon/龚阳辉
/// 日期 : 2017-02-17 11:30
/// 功能 : 大地图跳转
/// </summary>
public class EM_Graph : EM_GraphBase{
	public EM_Graph(){
		SetAlgorithm (new EM_Dijkstra ());
	}

	// 寻路
	public override void FindPath(params object[] pars){
		if (pars == null || pars.Length != 3)
			return;

		if (!(this.algorithm is EM_Dijkstra)) {
			return;
		}

		int orgMapId = 0,toMapId = 0,curLev = 0;

		orgMapId = (int)pars [0];
		toMapId = (int)pars [1];
		curLev = (int)pars [2];
		((EM_Dijkstra)this.algorithm).Perform (this, orgMapId, toMapId, curLev);
	}
}
