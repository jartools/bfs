using UnityEngine;
using System.Collections;
using System.Collections.Generic;

/// <summary>
/// 类名 : 移动到某个位置管理对象(自动寻路)
/// 作者 : Canyon/龚阳辉
/// 日期 : 2017-02-21 20:31
/// 功能 : 处理地图间的跳转(包含不连贯区域地图的跳转)
/// </summary>
public class MgrMoveTarget : Kernel<MgrMoveTarget> {

	ConfigLoaderManager mgrCfg {
		get{
			return ConfigLoaderManager.Instance ();
		}
	}

	Master master{
		get{
			return EntityManager.Instance ().master;
		}
	}

	int curMapId{
		get{
			return MapManager.Instance ().mapID;
		}
	}

	// 跳转队列
	Queue<EM_Edge> queue = new Queue<EM_Edge>();

	// 当前跳转
	EM_Edge curEdge = null;

	// 目标地图ID
	int m_iToMapId = 0;

	// 目标地图的某个坐标点
	Vector3 m_v3ToPos = Vector3.zero;

	// 到达回调
	System.Action m_callCompleted;

	// 是否自动寻路
	[System.NonSerialized]
	public bool m_isAutoMove = false;

	// 地图内跳转
	Queue<EM_Edge> queueArea = new Queue<EM_Edge>();
	Vector3 m_v3ToAreaPos = Vector3.zero;
	System.Action m_callArrived4Area = null;

	// 地图与地图之前
	public EM_Graph graphMap = null;

	// 当前地图区域内
	public EM_GraphArea graphArea =  null;

	// 初始化寻路
	public void Init(){
		graphMap = new EM_Graph();
		graphArea = new EM_GraphArea();

		// mgrCfg.GetMapListConfig (item.GateMapId);
		int needLev = 0;
		foreach (var item in mgrCfg.GetAllTransferGate ()) {
			graphMap.InitNodes (item);
			graphMap.SetLimitLev (item.GateMapId,needLev);
			graphMap.SetLimitLev (item.TargetMapId,needLev);

			graphArea.InitNodes (item);
			graphArea.SetLimitLev (item.GateMapId,needLev);
			graphArea.SetLimitLev (item.TargetMapId,needLev);
		}
	}

	/// <summary>
	/// 取得最近点移动点
	/// </summary>
	/// <returns>The near point.</returns>
	/// <param name="toMapId">To map identifier.</param>
	/// <param name="toPos">To position.</param>
	public Vector3 GetNearPoint(int toMapId,Vector3 toPos){
		if (toMapId <= 0) {
			return toPos;
		}

		int fmMapId = curMapId;
		List<EM_Edge> list = null;
		if (fmMapId != toMapId) {
			graphMap.FindPath (fmMapId, toMapId,master.Lv);
			list = graphMap.GetEdgePath ();

			if (list != null && list.Count  > 0) {
				toPos = list [0].startV3;
				toMapId = list [0].start.belongTo;
			}
		}

		graphArea.FindPathByPos (master.myTransform.position, toPos, master.Lv,fmMapId,toMapId);
		list = graphArea.GetEdgePath ();
		if (list != null && list.Count  > 0) {
			toPos = list [0].startV3;
		}
		return toPos;
	}

	/// <summary>
	/// 移动
	/// </summary>
	/// <param name="toMapId">To map identifier.</param>
	/// <param name="toPos">To position.</param>
	/// <param name="callFinished">Call finished.</param>
	public void MoveTo(int toMapId,Vector3 toPos,System.Action callFinished = null){
		ClearAll ();

		m_callCompleted = callFinished;
		m_v3ToPos = toPos;
		m_iToMapId = toMapId;

		HandleToTarget ();
	}

	/// <summary>
	/// 处理移动到某个点
	/// </summary>
	void HandleToTarget(){
		queue.Clear ();
		queueArea.Clear ();
		m_isAutoMove = false;

		StopAllCoroutines ();

		if (m_iToMapId <= 0) {
			return;
		}

		m_isAutoMove = true;

		int fmMapId = curMapId;

		// Debuger.Log ("From Map = [" + fmMapId + "],To Map = [" + m_iToMapId + "] ");
		if (fmMapId == m_iToMapId) {
			ComputeMoveToPos (master.myTransform.position,m_v3ToPos,MoveToPos,fmMapId,m_iToMapId);
			return;
		}

		graphMap.FindPath (fmMapId, m_iToMapId,master.Lv);
		List<EM_Edge> list = graphMap.GetEdgePath ();

		if (list != null) {
			foreach (var item in list) {
				queue.Enqueue (item);
			}
		}

		MoveToNextMap();
	}

	/// <summary>
	/// 移动到下个地图
	/// </summary>
	void MoveToNextMap(){
		StartCoroutine (MoveToMap());
	}

	/// <summary>
	/// 取得Y值
	/// </summary>
	public float GetY(Vector3 orgPos){
        float y;
		Entity.GetTerrainHeight (orgPos.x, orgPos.z, out y);
        return y;
	}

	/// <summary>
	/// 移动到下个地图
	/// </summary>
	IEnumerator MoveToMap(){
		yield return null;
		int belongOrg = curMapId;
		int belongTo = m_iToMapId;
		if (queue.Count > 0) {
			curEdge = queue.Peek ();
			// 判断地图是否可以到达
			m_v3ToAreaPos = curEdge.startV3;
			m_v3ToAreaPos.y = GetY(m_v3ToAreaPos);
			belongTo = curEdge.start.belongTo;
			if (isCanMoveToPos (master.myTransform.position, m_v3ToAreaPos,belongOrg,belongTo)) {
				// Debuger.Log ("MoveToMap To Next = " + curEdge.start.label + " --> " + curEdge.end.label + ", door Pos = " + curEdge.gate.GateV3);
				curEdge = queue.Dequeue ();
				master.Move2Pos (m_v3ToAreaPos, () => ArriveNextMap ());
			} else {
				// Debuger.Log ("MoveToMap To Area = " + master.myTransform.position + " --> " + m_v3ToAreaPos + ", door Pos = " + curEdge.gate.GateV3);
				ComputeMoveToPos (master.myTransform.position,m_v3ToAreaPos,MoveToNextMap,belongOrg,belongTo);
			}
		} else {
			ComputeMoveToPos (master.myTransform.position,m_v3ToPos,MoveToPos,belongOrg,belongTo);
		}
	}

	/// <summary>
	/// 到达下个地图
	/// </summary>
	void ArriveNextMap(){
		MyScenesManager.Instance ().EntryMapOk -= MoveToNextMap;
		MyScenesManager.Instance ().EntryMapOk += MoveToNextMap;
	}

	/// <summary>
	/// 同地图里面判断是否可以到某点
	/// </summary>
	public bool isCanMoveToPos(Vector3 fmPos,Vector3 toPos,int belongOrg,int belongTo){
		if (belongOrg != belongTo)
			return false;
		return isCanMoveToPos4Area (fmPos, toPos, belongOrg, belongTo);
	}

	bool isCanMoveToPos4Area(Vector3 fmPos,Vector3 toPos,int belongOrg,int belongTo){
		graphArea.FindPathByPos (fmPos, toPos, master.Lv,belongOrg,belongTo);
		List<EM_Edge> list = graphArea.GetEdgePath ();
		return (list == null || list.Count <= 0);
	}

	/// <summary>
	/// 同地图里面移动到某点
	/// </summary>
	void ComputeMoveToPos(Vector3 fmPos,Vector3 toPos,System.Action callArriveArea,int belongOrg,int belongTo){
		queueArea.Clear ();
		this.m_callArrived4Area = callArriveArea;

		graphArea.FindPathByPos (fmPos, toPos, master.Lv,belongOrg,belongTo);
		List<EM_Edge> list = graphArea.GetEdgePath ();
		if (list != null) {
			foreach (var item in list) {
				queueArea.Enqueue (item);
			}
		}
		MoveToNextArea ();
	}

	/// <summary>
	/// 同地图里面移动到某点
	/// </summary>
	void MoveToNextArea(){
		StartCoroutine (MoveToArea());
	}

	/// <summary>
	/// 同地图里面到达到某点
	/// </summary>
	public void ArriveNextArea(){
		if (!m_isAutoMove)
			return;
		MoveToNextArea ();
	}

	/// <summary>
	/// 同地图里面到某点
	/// </summary>
	IEnumerator MoveToArea(){
		yield return null;
		if (queueArea.Count > 0) {
			curEdge = queueArea.Dequeue ();
			// Debuger.Log ("MoveToArea = " + curEdge.start.label + " --> " + curEdge.end.label + ", door Pos = " + curEdge.gate.GateV3);
			master.Move2Pos (curEdge.startV3,null);
		} else {
			ExcuteCallMoveArea ();
		}
	}

	/// <summary>
	/// 同地图里面到某点
	/// </summary>
	void MoveToPos(){
		if (curMapId == m_iToMapId) {
			m_v3ToPos.y = GetY(m_v3ToPos);
			// Debuger.Log ("MoveToPos Arrive TargetMap = " + master.myTransform.position + " --> " + m_v3ToPos);
			master.Move2Pos (m_v3ToPos, () => {
				ExcuteComplected ();
			});
		} else{
			ClearAll();
		}
	}

	/// <summary>
	/// 最终到达目的地
	/// </summary>
	void ExcuteComplected(){
		// Debuger.Log (" ExcuteComplected Arrived target --> " + m_v3ToPos + ", cur = " + master.myTransform.position);
		ExcuteCall ();
		Clear();
	}

	/// <summary>
	/// 到达目的地后的回调函数
	/// </summary>
	void ExcuteCall(){
		if (m_callCompleted != null) {
			m_callCompleted ();
		}
	}

	/// <summary>
	/// 同地图里面到某点的回调函数
	/// </summary>
	void ExcuteCallMoveArea(){
		if (m_callArrived4Area != null) {
			m_callArrived4Area ();
		}
	}

	/// <summary>
	/// 清空
	/// </summary>
	void Clear(){
		queue.Clear ();
		queueArea.Clear ();

		curEdge = null;
		master.ClearMoveCompletedAction ();
		MyScenesManager.Instance ().EntryMapOk -= MoveToNextMap;

		m_isAutoMove = false;
	}

	// 清空
	public void ClearAll(){
		Clear ();
		m_callCompleted = null;
		m_callArrived4Area = null;
	}
}
