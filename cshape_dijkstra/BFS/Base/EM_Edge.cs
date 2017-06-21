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

	// 传送门数据
	public uFramework.CFG_TransferGate gate;

	public EM_Edge(EM_Node start,EM_Node end,uFramework.CFG_TransferGate gate) : this(start,end,1,gate){
	}

	public EM_Edge(EM_Node start,EM_Node end,int distance,uFramework.CFG_TransferGate gate){
		this.start = start;
		this.end = end;
		this.gate = gate;
		this.distance = distance;
		this.start.AddEdge (this);
	}
}
