using UnityEngine;
using System.Collections;
using System.Collections.Generic;

/// <summary>
/// 类名 : 任务管理对象
/// 作者 : Canyon
/// 日期 : 2017-02-17 17:07
/// 功能 :
/// </summary>
public class MgrTask : Kernel<MgrTask> {

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

	Dictionary<int,EU_Task> m_dicMap = new Dictionary<int, EU_Task> ();
	List<EU_Task> m_outlist = new List<EU_Task>();

	EU_Task m_task = null;
	EU_TaskTarget m_target = null;

	public List<EU_Task> GetList(){
		m_outlist.Clear ();
		m_outlist.AddRange (m_dicMap.Values);

		m_outlist.Sort ((x, y) => {
			if(x.m_locData == null && y.m_locData != null)
				return 1;
			
			if(x.m_locData != null && y.m_locData == null)
				return -1;
			
			if(x.m_locData == null && y.m_locData == null)
				return 1;

			if(x.m_locData.taskType == TaskType.MAIN && y.m_locData.taskType != TaskType.MAIN)
				return -1;

			if(x.m_locData.taskType != TaskType.MAIN && y.m_locData.taskType == TaskType.MAIN)
				return 1;

			if(x.m_locData.taskType != TaskType.MAIN && y.m_locData.taskType != TaskType.MAIN)
			{
				if(x.m_locData.taskType == TaskType.Daily && y.m_locData.taskType != TaskType.Daily)
					return -1;

				if(x.m_locData.taskType != TaskType.Daily && y.m_locData.taskType == TaskType.Daily)
					return 1;
			}
			
			if(x.m_locData.Type < y.m_locData.Type)
				return -1;
			
			if(x.m_locData.ID < y.m_locData.ID)
				return -1;
			
			return 1;
		});
		return m_outlist;
	}

	public void Init(List<SC_Task> list){
		// Clear ();
		Refresh (list);
	}

	public void Refresh(List<SC_Task> list){
		if (list == null || list.Count <= 0)
			return;

		int lens = list.Count;
		SC_Task tmpSvTask = null;

		EU_Task tmpTask = null;
		EU_TaskTarget tmpTarget = null;

		for (int i = 0; i < lens; i++) {
			tmpSvTask = list [i];
			if (tmpSvTask.taskStatus == 3) {
				Remove (tmpSvTask.taskDefineId);
				continue;
			}

			if (m_dicMap.ContainsKey (tmpSvTask.taskDefineId)) {
				tmpTask = m_dicMap [tmpSvTask.taskDefineId];
				tmpTask.Reset (tmpSvTask);
			} else {
				tmpTask = new EU_Task (tmpSvTask);
				m_dicMap.Add (tmpSvTask.taskDefineId,tmpTask);
			}
		}

		CheckCurTaskFinished ();
	}

	public void Remove(EU_Task one){
		if (one == null)
			return;
		Remove (one.m_iTaskId);
	}

	public void Remove(int idKey){
		if (m_dicMap.ContainsKey (idKey))
			m_dicMap.Remove (idKey);
	}

	void Clear(){
		m_dicMap.Clear ();
		m_outlist.Clear ();

		m_task = null;
		m_target = null;
	}

	public void DoClickTarget(EU_TaskTarget target){
		m_target = target;
		m_task = null;
		int toMapId = 0;
		Vector3 toPos = Vector3.zero;
		if (m_target != null) {
			toMapId = target.m_locData.MapID;
			toPos = target.m_v3Pos;

			m_task = m_dicMap [m_target.m_iBelongToId];
		}

		if (m_task != null) {
			if (m_task.isCompletd) {
				CheckCurTaskFinished ();
				return;
			}
		}

		MgrMoveTarget.Instance ().MoveTo (toMapId, toPos,CheckArriveTarget);
	}

	void CheckArriveTarget(){
		if (m_target == null)
			return;
		if (curMapId != m_target.m_locData.MapID) {
			return;
		}

		Vector3 pos = master.myTransform.position;
		Vector2 v2Pos = new Vector2 (pos.x, pos.z);
		Vector2 v2ToPos = new Vector2 (m_target.m_v3Pos.x, m_target.m_v3Pos.z);
		Vector2 v2Diff = v2ToPos - v2Pos;
		if (v2Diff.magnitude > 1) {
			return;
		}

		switch (m_target.m_locData.targetType) {
		case  TaskTargetType.Go2Npc:
			RequestRefreshTarget (m_target.m_iTargetId);
			break;
		}
	}

	void RequestRefreshTarget(int targetId){
		CS_RefreshTaskTargetsReq msgObj = new CS_RefreshTaskTargetsReq (); 
		msgObj.taskTargetId = targetId;
		NetWorkManager.Instance ().Send<CS_RefreshTaskTargetsReq> (Protocol.CS_RefreshTaskTargetsReq, msgObj);
	}

	void CheckCurTaskFinished(){
		if (m_task == null) {
			return;
		}

		if (!m_dicMap.ContainsKey (m_task.m_iTaskId)) {
			return;
		}
		m_task = m_dicMap [m_task.m_iTaskId];

		if (m_task.isCompletd) {
			UI.UIManager.Instance ().OpenWindow ("WindowTaskFrame",m_task);
		}
	}

	public void RequestReceiveRewards(EU_Task one){
		if (one == null || !one.isCompletd) {
			return;
		}

		CS_GetTaskRewardReq msgObj = new CS_GetTaskRewardReq ();
		msgObj.taskDefineId = one.m_iTaskId;
		NetWorkManager.Instance ().Send<CS_GetTaskRewardReq> (Protocol.CS_GetTaskRewardReq, msgObj);
	}
}
