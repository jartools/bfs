using UnityEngine;
using System.Collections;
using System.Collections.Generic;

/// <summary>
/// 类名 : 图
/// 作者 : Canyon/龚阳辉
/// 日期 : 2017-02-27 15:30
/// 功能 :  地图内寻路
/// </summary>
public class EM_GraphArea : EM_GraphBase{

	NavMeshPath m_navPath = new NavMeshPath();

	NavMeshHit m_navHit = new NavMeshHit();

	public EM_GraphArea(){
		SetAlgorithm (new EM_DijkstraArea ());
	}

	// 寻路
	public override void FindPath(params object[] pars){
		if (pars == null || pars.Length != 4)
			return;

		if (!(this.algorithm is EM_DijkstraArea)) {
			return;
		}

		int curMapID = 0,orgAreaGID = 0,toAreaGID = 0,curLev = 0;

		curMapID = (int)pars [0];
		orgAreaGID = (int)pars [1];
		toAreaGID = (int)pars [2];
		curLev = (int)pars [3];
		((EM_DijkstraArea)this.algorithm).Perform (this, curMapID, orgAreaGID, toAreaGID,curLev);
	}

	// 取得节点
	EM_Node GetNode(Vector3 pos,int belongId = -1,bool isFm = true){
		EM_Node ret = null;
		bool isCan = false;
		List<EM_Node> list = null;
		if (belongId > 0) {
			list = GetNodes (belongId);
		} else {
			list = GetNodes ();
		}

		if (list == null || list.Count <= 0) {
			goto end;
		}

		Vector3 tmpV3 = Vector3.zero;

		int lensNavPath = 0;
		Vector3 navEnd = Vector3.zero;
		Vector3 diffSub = Vector3.zero;

		Vector3 sourceV3 = Vector3.zero;
		Vector3 targetV3 = Vector3.zero;

		foreach (var node in list) {
			foreach (var edg in node.GetEdgeList()) {
				if (isFm)
					tmpV3 = edg.startV3;
				else
					tmpV3 = edg.endV3;

				sourceV3 = CorrectPos (pos);
				targetV3 = CorrectPos (tmpV3);

				isCan = NavMesh.CalculatePath (sourceV3, targetV3, NavMesh.AllAreas, m_navPath);
				lensNavPath = m_navPath.corners.Length;
				if (isCan && lensNavPath > 0) {
					navEnd = m_navPath.corners [lensNavPath - 1];
					diffSub = navEnd - targetV3;
					diffSub.y = 0;

					if (diffSub.sqrMagnitude < 0.5f) {
						ret = isFm ? edg.start : edg.end;
						goto end;
					}
				}
			}
		}

		end : {
			m_navPath.ClearCorners ();
			return ret;
		}
	}

	// 校准点
	Vector3 CorrectPos(Vector3 org,bool isCorrect = true){
		if (isCorrect) {
			if (NavMesh.SamplePosition (org, out m_navHit, 1.0f, NavMesh.AllAreas)) {
				return m_navHit.position;
			}
		}
		return org;
	}

	// 初始化节点
	public override void InitNodes (uFramework.CFG_TransferGate gate)
	{
		InitNodes (gate,false);
	}

	// 寻路
	public void FindPathByPos(Vector3 orgPos,Vector3 toPos,int curLev,int belongOrg = 0,int belongTo = 0){
		EM_Node fm = GetNode (orgPos,belongOrg);
		EM_Node to = GetNode (toPos,belongTo,false);

		if (this.algorithm is EM_DijkstraBase) {
			((EM_DijkstraBase)this.algorithm).Clear ();
		}

		if (fm == null || to == null) {
			return;
		}

		if (fm.belongTo != to.belongTo) {
			return;
		}

		FindPath (fm.belongTo, fm.types, to.types, curLev);
	}

	/// <summary>
	/// 判断两个点是否可以到达(Navmesh的逻辑)
	/// </summary>
	public bool IsCanArrive4NavMesh(Vector3 fmV3,Vector3 toV3){
		bool isResult = false;

		Vector3 sourceV3 = CorrectPos (fmV3);
		Vector3 targetV3 = CorrectPos (toV3);

		int lensNavPath = 0;
		Vector3 navEnd = Vector3.zero;
		Vector3 diffSub = Vector3.zero;

		bool isCan = NavMesh.CalculatePath (sourceV3, targetV3, NavMesh.AllAreas, m_navPath);
		lensNavPath = m_navPath.corners.Length;
		if (isCan && lensNavPath > 0) {
			navEnd = m_navPath.corners [lensNavPath - 1];
			diffSub = navEnd - targetV3;
			diffSub.y = 0;

			isResult = (diffSub.sqrMagnitude < 0.5f);
		}

		m_navPath.ClearCorners ();
		return isResult;
	}

	void TestNavMesh(int belongTo){
		float y = 9.6f; // 与高度有关的
		Vector3 vv = new Vector3 (53, y, 16.7f);
		EM_Node toY = GetNode(vv,belongTo,false);

		bool kkk1 = NavMesh.CalculatePath (vv, new Vector3(93.2f,16.36f,95.9f), NavMesh.AllAreas, m_navPath);

		bool kkk2 = NavMesh.CalculatePath (vv, new Vector3(79.13f,26.81f,104f), NavMesh.AllAreas, m_navPath);

		bool kkk3 = NavMesh.CalculatePath (vv, new Vector3(76f,26.81f,114.53f), NavMesh.AllAreas, m_navPath);

		bool kkk4 = NavMesh.CalculatePath (vv, new Vector3(91.24f,16.36f,91.15f), NavMesh.AllAreas, m_navPath);
	}
}
