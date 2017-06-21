using UnityEngine;
using System.Collections;

/// <summary>
/// 类名 : 跳转
/// 作者 : Canyon/龚阳辉
/// 日期 : 2017-02-17 09:37
/// 功能 : 
/// </summary>
public class EM_Edge  {
	// 开始节点
	public EM_Node start;

	// 结束节点
	public EM_Node end;

	// 距离
	public int distance = 1;

	// 开始节点位置
	public Vector3 startV3 = Vector3.zero;

	// 结束节点位置
	public Vector3 endV3 = Vector3.zero;

	// 数据源
	public object data = null;

	public EM_Edge(EM_Node start,EM_Node end,uFramework.CFG_TransferGate gate) : this(start,end,1,gate){
	}

//	public EM_Edge(EM_Node start,EM_Node end,uFramework.CFG_TransferGate gate) : this(start,end,1,gate){
//	}

	public EM_Edge(EM_Node start,EM_Node end,int distance,object data){
		this.start = start;
		this.end = end;
		this.data = data;
		this.distance = distance;
		this.start.AddEdge (this);

		if (data is uFramework.CFG_TransferGate) {
			uFramework.CFG_TransferGate _gate = (uFramework.CFG_TransferGate)data;
			startV3 = _gate.GateV3;
			endV3 = _gate.TargetV3;
		}
	}
}
