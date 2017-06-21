using UnityEngine;
using System.Collections;
using System.Collections.Generic;

/// <summary>
/// 类名 : 图
/// 作者 : Canyon/龚阳辉
/// 日期 : 2017-02-17 11:30
/// 功能 : 
/// </summary>
public class EM_GraphBase {

	protected EM_IAlgorithm algorithm;

	// 设置算法
	public void SetAlgorithm(EM_IAlgorithm algorithm){
		this.algorithm = algorithm;
	}

	/// <summary>
	/// 节点s
	/// key : EM_Node.label
	/// </summary>
	Dictionary<string,EM_Node> nodes = new Dictionary<string, EM_Node> ();

	/// <summary>
	/// 返回值(不用随时创建)
	/// </summary>
	List<EM_Node> m_outList = new List<EM_Node>();

	// 取得节点字典
	public Dictionary<string,EM_Node> GetMapNodes(){
		return nodes;
	}

	// 取得列表
	public List<EM_Node> GetNodes(){
		m_outList.Clear ();
		m_outList.AddRange(nodes.Values);
		return m_outList;
	}

	// 添加节点
	public void AddNode(EM_Node node){
		string key = node.label.ToString ();
		if (!nodes.ContainsKey (key)) {
			nodes.Add (key, node);
		}

		AddNode4BelongTo (node);
	}

	/// <summary>
	/// 节点s
	/// key : EM_Node.belongTo
	/// </summary>
	protected Dictionary<int,List<EM_Node>> bl2nodes = new Dictionary<int, List<EM_Node>> ();

	/// <summary>
	/// 添加节点 到 reshop里面
	/// </summary>
	/// <param name="node">Node.</param>
	void AddNode4BelongTo(EM_Node node){
		int key = node.belongTo;
		List<EM_Node> list = null;
		if (bl2nodes.ContainsKey (key)) {
			list = bl2nodes [key];
		} else {
			list = new List<EM_Node> ();
			bl2nodes.Add (key, list);
		}

		if (!list.Contains (node))
			list.Add (node);
	}

	/// <summary>
	/// 取得所属列表
	/// </summary>
	/// <returns>The nodes.</returns>
	/// <param name="belongTo">Belong to.</param>
	public List<EM_Node> GetNodes(int belongTo){
		if (bl2nodes.ContainsKey (belongTo)) {
			return bl2nodes [belongTo];
		}
		return null;
	}

	// 取得路径跳转列表
	public List<EM_Edge> GetEdgePath(){
		return this.algorithm.GetPathEdge ();
	}

	// 初始化节点
	public virtual void InitNodes(uFramework.CFG_TransferGate gate){
		InitNodes (gate, true);
	}

	// 初始化节点
	protected void InitNodes(uFramework.CFG_TransferGate gate, bool isToMap){
		if (isToMap) {
			if (gate.GateMapId == gate.TargetMapId)
				return;
		} else {
			if (gate.GateMapId != gate.TargetMapId)
				return;
		}

		string key = "";
		int belong = 0;
		int types = 0;

		belong = gate.GateMapId;
		types = gate.AreaGID;
		key = isToMap ? belong + "" : belong + "_" + types;

		EM_Node node1 = GetNode (key);
		if(node1 == null)
			node1 = new EM_Node (key,belong,types);
		
		belong = gate.TargetMapId;
		types = gate.ToAreaGID;
		key = isToMap ? belong + "" : belong + "_" + types;

		EM_Node node2 = GetNode (key);
		if(node2 == null)
			node2 = new EM_Node (key,belong,types);
		
		new EM_Edge (node1,node2,gate);

		AddNode (node1);
		AddNode (node2);
	}

	// 取得节点
	public EM_Node GetNode(object label){
		string key = label.ToString ();
		if (nodes.ContainsKey (key)) {
			return nodes [key];
		}
		return null;
	}

	// 设置限定等级
	public void SetLimitLev(object label,int needLev){
		EM_Node node = GetNode (label);
		if (node != null)
			node.needLevel = needLev;
	}

	// 寻路(继承需要实现)
	public virtual void FindPath(params object[] pars){
	}
}
