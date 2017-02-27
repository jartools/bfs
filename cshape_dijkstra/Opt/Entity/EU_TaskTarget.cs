using UnityEngine;
using System.Collections;

/// <summary>
/// 类名 : 任务目标实体类
/// 作者 : Canyon
/// 日期 : 2017-02-15 11:37
/// 功能 : 结合本地对象，和服务器对象的对象
/// </summary>
public class EU_TaskTarget  {
	
	public uFramework.CFG_TaskTarget  m_locData;

	/// <summary>
	/// 本地ID
	/// </summary>
	public int m_iTargetId;

	/// <summary>
	/// 完成值
	/// </summary>
	public int m_iCount;

	/// <summary>
	/// 状态[0否,1是]
	/// </summary>
	public int m_iStatus;

	/// <summary>
	/// 与任务的关系
	/// </summary>
	public int m_iBelongToId;

	string strDefDesc = "";

	public Vector3 m_v3Pos = Vector3.zero;

	ConfigLoaderManager mgrCfg {
		get{
			return ConfigLoaderManager.Instance ();
		}
	}

	public EU_TaskTarget(int belongTo,SC_TaskTarget svData){
		OnInit (belongTo,svData);
	}

	public string info{
		get{
			if (m_iCount < m_locData.VarInt)
				return  string.Format(strDefDesc,(m_iCount + "/" + m_locData.VarInt));

			return string.Format(strDefDesc,"已完成");;
		}
	}

	public void OnInit(int belongTo, SC_TaskTarget svData){
		m_iBelongToId = belongTo;
		m_iCount = svData.count;
		m_iTargetId = svData.taskTargetId;
		m_iStatus = svData.isComplete;
		m_locData = mgrCfg.GetTaskTarget (m_iTargetId);

		strDefDesc = mgrCfg.GetLanguageConfig (m_locData.DescID);
		m_v3Pos.x = m_locData.posX;
		m_v3Pos.z = m_locData.posZ;
	}
}
