using UnityEngine;
using System.Collections;
using System.Collections.Generic;

/// <summary>
/// 类名 : 图
/// 作者 : Canyon
/// 日期 : 2017-02-27 15:30
/// 功能 :  地图内寻路
/// </summary>
public class EM_GraphArea : EM_GraphBase{

	NavMeshPath m_navPath = new NavMeshPath();
	Vector3 m_v3Fm = Vector3.zero;

	private EM_GraphArea(){
		SetAlgorithm (new EM_DijkstraArea ());
	}

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

	EM_Node GetNode(Vector3 pos){
		EM_Node ret = null;
		bool isCan = false;
		foreach (var node in GetNodes ().Values) {
			foreach (var edg in node.GetEdgeList()) {
				m_v3Fm = edg.gate.GateV3;
				pos.y = m_v3Fm.y;
				isCan = NavMesh.CalculatePath (m_v3Fm, pos, NavMesh.AllAreas, m_navPath);
				if (isCan) {
					ret = node;
					goto end;
				}
			}
		}

		end : {
			m_navPath.ClearCorners ();
			return ret;
		}
	}

	public override void InitNodes (uFramework.CFG_TransferGate gate)
	{
		InitNodes (gate,false);
	}

	public void FindPathByPos(Vector3 orgPos,Vector3 toPos,int curLev){
		EM_Node fm = GetNode (orgPos);
		EM_Node to = GetNode (toPos);

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

	static EM_GraphArea _instance;

	static public EM_GraphArea Instance{
		get{
			if (_instance == null)
				_instance = new EM_GraphArea ();
			return _instance;
		}
	}
}
