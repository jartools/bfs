using UnityEngine;
using System.Collections;

/// <summary>
/// 类名 : 跳转
/// 作者 : Canyon
/// 日期 : 2017-02-17 09:37
/// 功能 : 
/// </summary>
public class EM_Edge  {
	public EM_Node start;
	public EM_Node end;

	public int distance = 1;

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
