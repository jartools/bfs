using UnityEngine;
using System.Collections;
using System.Collections.Generic;

/// <summary>
/// 类名 : 算法 接口
/// 作者 : Canyon
/// 日期 : 2017-02-17 10:11
/// 功能 : 
/// </summary>
public interface EM_IAlgorithm {

	// 到达目的地的距离
	int Distance ();

	// 到达目的地的路径列表
	List<EM_Node> GetPathNode ();

	// 到达目的地的跳转列表
	List<EM_Edge> GetPathEdge ();
}
