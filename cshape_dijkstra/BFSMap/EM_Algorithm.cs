using UnityEngine;
using System.Collections;
using System.Collections.Generic;

/// <summary>
/// 类名 : 算法 接口
/// 作者 : Canyon
/// 日期 : 2017-02-17 10:11
/// 功能 : 
/// </summary>
public interface EM_Algorithm : EM_IAlgorithm{
	void Perform(EM_GraphBase g, int orgMapId,int toMapId, int curLev);
}
