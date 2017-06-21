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

		foreach (var node in list) {
			foreach (var edg in node.GetEdgeList()) {
				if (isFm)
					tmpV3 = edg.gate.GateV3;
				else
					tmpV3 = edg.gate.TargetV3;
				
				pos.y = tmpV3.y;
				isCan = NavMesh.CalculatePath (tmpV3, pos, NavMesh.AllAreas, m_navPath);
				if (isCan) {
					ret = isFm ? edg.start : edg.end;
					goto end;
				}
			}
		}

		end : {
			m_navPath.ClearCorners ();
			return ret;
		}
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
}
