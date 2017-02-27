using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using System.ComponentModel;

/// <summary>
/// 类名 : 任务实体类
/// 作者 : Canyon
/// 日期 : 2017-02-15 11:37
/// 功能 : 结合本地对象，和服务器对象的对象
/// </summary>
public class EU_Task{

	public enum TaskStatus
	{
		None = 0,

		[Description("接受")]
		Accept = 1,

		[Description("完成")]
		Complete = 2,

		[Description("已领奖励")]
		Receive = 3
	}
	
	public uFramework.CFG_Task  m_locData;

	/// <summary>
	/// 本地ID
	/// </summary>
	public int m_iTaskId;

	/// <summary>
	/// 状态[0，1接受,2完成,3已经领取奖励]
	/// </summary>
	public TaskStatus m_iStatus = TaskStatus.None;


	Dictionary<int,EU_TaskTarget> m_dicTarget = new Dictionary<int, EU_TaskTarget> ();

	List<EU_TaskTarget> m_lOuts = new List<EU_TaskTarget> ();

	public string txtName = "",txtTitle = "",txtContent = "";

	public List<EN_Item> m_lItems = new List<EN_Item> ();

	public float m_fSecondCD = 4.0f;

	ConfigLoaderManager mgrCfg {
		get{
			return ConfigLoaderManager.Instance ();
		}
	}

	public bool isCompletd{
		get{ 
			return m_iStatus == TaskStatus.Complete;
		}
	}

	public bool isReceivedReward{
		get{ 
			return m_iStatus == TaskStatus.Receive;
		}
	}

	public EU_Task(SC_Task svData){
		OnInit (svData);
	}

	public List<EU_TaskTarget> m_lTargets{
		get{
			m_lOuts.Clear ();
			m_lOuts.AddRange (m_dicTarget.Values);
			return m_lOuts;
		}
	}

	public void Reset(SC_Task svData){
		m_iTaskId = svData.taskDefineId;
		m_iStatus = (TaskStatus)svData.taskStatus;

		List<SC_TaskTarget> list = svData.taskTargets;
		if (list == null || list.Count <= 0) {
			return;
		}

		m_locData = mgrCfg.GetTask (m_iTaskId);

		txtName = mgrCfg.GetLanguageConfig (m_locData.NameID);
		txtTitle = mgrCfg.GetLanguageConfig (m_locData.TitleID);
		txtContent = mgrCfg.GetLanguageConfig (m_locData.DecsID);
		m_fSecondCD = mgrCfg.GetGlobalConfig (1).TaskNextCD;

		int lens = list.Count;
		SC_TaskTarget tmp = null;

		EU_TaskTarget eutmp = null;
		for (int i = 0; i < lens; i++) {
			tmp = list [i];
			if (m_dicTarget.ContainsKey (m_iTaskId)) {
				eutmp = m_dicTarget [m_iTaskId];
				eutmp.m_iCount = tmp.count;
				eutmp.m_iStatus = tmp.isComplete;
			} else {
				eutmp = new EU_TaskTarget (m_iTaskId, tmp);
				m_dicTarget.Add (m_iTaskId, eutmp);
			}
		}
	}

	public void OnInit(SC_Task svData){
		Clear ();
		Reset (svData);
		OnInitRewards ();
	}

	void Clear(){
		m_dicTarget.Clear ();
		m_lOuts.Clear ();
		m_locData = null;
		m_lItems.Clear ();
	}

	public EU_TaskTarget GetTarget(int targetId){
		if (m_dicTarget.ContainsKey (targetId))
			return m_dicTarget [targetId];
		return null;
	}

	public void OnInitRewards(){
		m_lItems.Clear ();
		// 奖励
		if (m_locData != null && !string.IsNullOrEmpty (m_locData.Rewards) && !"null".Equals (m_locData.Rewards)) {
			string[] arrs = m_locData.Rewards.Split (";".ToCharArray ());
			string[] arrs2;

			foreach (var item in arrs) {
				arrs2 = item.Split (",".ToCharArray ());
				m_lItems.Add(new EN_Item(int.Parse(arrs2[0]),int.Parse(arrs2[1]),int.Parse(arrs2[2])));
			}
		}
	}
}

public class EN_Item{
	public  int m_iType;
	public int m_iID;
	public int m_iNum;

	public EN_Item(int types,int id,int num){
		this.m_iType = types;
		this.m_iID = id;
		this.m_iNum = num;
	}
}