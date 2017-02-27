using UnityEngine;
using System.Collections;
using System.Collections.Generic;

/// <summary>
/// 类名 : 移动到某个位置管理对象(自动寻路)
/// 作者 : Canyon
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

	Queue<EM_Edge> queue = new Queue<EM_Edge>();
	EM_Edge curEdge = null;

	int m_iToMapId = 0;
	Vector3 m_v3ToPos = Vector3.zero;

	System.Action m_callCompleted;

	// 是否自动寻路
	bool m_isAutoMove = false;

	// 地图内跳转
	Queue<EM_Edge> queueArea = new Queue<EM_Edge>();
	Vector3 m_v3ToAreaPos = Vector3.zero;
	System.Action m_callArrived4Area = null;

	public void MoveTo(int toMapId,Vector3 toPos,System.Action callFinished = null){
		m_callCompleted = callFinished;
		m_v3ToPos = toPos;
		m_iToMapId = toMapId;

		HandleToTarget ();
	}

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

		Debuger.Log ("From Map = [" + fmMapId + "],To Map = [" + m_iToMapId + "] ");
		if (fmMapId == m_iToMapId) {
			ComputeMoveToPos (master.myTransform.position,m_v3ToPos,MoveToPos);
			return;
		}

		EM_Graph.Instance.FindPath (fmMapId, m_iToMapId,master.Lv);
		List<EM_Edge> list = EM_Graph.Instance.GetEdgePath ();

		if (list != null) {
			foreach (var item in list) {
				queue.Enqueue (item);
			}
		}

		MoveToNextMap();
	}

	void MoveToNextMap(){
		StartCoroutine (MoveToMap());
	}

	IEnumerator MoveToMap(){
		yield return null;
		if (queue.Count > 0) {
			curEdge = queue.Peek ();
			// 判断地图是否可以到达
			m_v3ToAreaPos = curEdge.gate.GateV3;
			m_v3ToAreaPos.y = master.myTransform.position.y;
			if (isCanMoveToPos (master.myTransform.position, m_v3ToAreaPos)) {
				Debuger.Log ("MoveToMap To Next = " + curEdge.start.label + " --> " + curEdge.end.label + ", door Pos = " + curEdge.gate.GateV3);
				curEdge = queue.Dequeue ();
				master.Move2Pos (m_v3ToAreaPos, () => ArriveNextMap ());
			} else {
				Debuger.Log ("MoveToMap To Area = " + master.myTransform.position + " --> " + m_v3ToAreaPos + ", door Pos = " + curEdge.gate.GateV3);
				ComputeMoveToPos (master.myTransform.position,m_v3ToAreaPos,MoveToNextMap);
			}
		} else {
			ComputeMoveToPos (master.myTransform.position,m_v3ToPos,MoveToPos);
		}
	}

	void ArriveNextMap(){
		MyScenesManager.Instance ().EntryMapOk -= MoveToNextMap;
		MyScenesManager.Instance ().EntryMapOk += MoveToNextMap;
	}

	bool isCanMoveToPos(Vector3 fmPos,Vector3 toPos){
		toPos.y = fmPos.y;
		EM_GraphArea.Instance.FindPathByPos (fmPos, toPos, master.Lv);
		List<EM_Edge> list = EM_GraphArea.Instance.GetEdgePath ();
		return (list == null || list.Count <= 0);
	}

	void ComputeMoveToPos(Vector3 fmPos,Vector3 toPos,System.Action callArriveArea){
		queueArea.Clear ();
		this.m_callArrived4Area = callArriveArea;

		toPos.y = fmPos.y;

		EM_GraphArea.Instance.FindPathByPos (fmPos, toPos, master.Lv);
		List<EM_Edge> list = EM_GraphArea.Instance.GetEdgePath ();
		if (list != null) {
			foreach (var item in list) {
				queueArea.Enqueue (item);
			}
		}
		MoveToNextArea ();
	}


	void MoveToNextArea(){
		StartCoroutine (MoveToArea());
	}

	public void ArriveNextArea(){
		if (!m_isAutoMove)
			return;
		MoveToNextArea ();
	}

	IEnumerator MoveToArea(){
		yield return null;
		if (queueArea.Count > 0) {
			curEdge = queueArea.Dequeue ();
			Debuger.Log ("MoveToArea = " + curEdge.start.label + " --> " + curEdge.end.label + ", door Pos = " + curEdge.gate.GateV3);
			master.Move2Pos (curEdge.gate.GateV3,null);
		} else {
			ExcuteCallMoveArea ();
		}
	}

	void MoveToPos(){
		if (curMapId == m_iToMapId) {
			m_v3ToPos.y = master.myTransform.position.y;
			Debuger.Log ("MoveToPos Arrive TargetMap = " + master.myTransform.position + " --> " + m_v3ToPos);
			master.Move2Pos (m_v3ToPos, () => {
				ExcuteComplected ();
			});
		} else{
			Clear();
		}
	}

	void ExcuteComplected(){
		Debuger.Log (" ExcuteComplected Arrived target --> " + m_v3ToPos + ", cur = " + master.myTransform.position);
		ExcuteCall ();
		Clear();
	}

	void ExcuteCall(){
		if (m_callCompleted != null) {
			m_callCompleted ();
			m_callCompleted = null;
		}
	}

	void ExcuteCallMoveArea(){
		if (m_callArrived4Area != null) {
			m_callArrived4Area ();
			m_callArrived4Area = null;
		}
	}

	void Clear(){
		queue.Clear ();
		queueArea.Clear ();

		curEdge = null;
		master.ClearMoveCompletedAction ();
		MyScenesManager.Instance ().EntryMapOk -= MoveToNextMap;

		m_callCompleted = null;
		m_isAutoMove = false;
		m_callArrived4Area = null;
	}
}
