using UnityEngine;
using System.Collections;

/// <summary>
/// 类名 : 算法 接口
/// 作者 : Canyon
/// 日期 : 2017-02-17 10:11
/// 功能 : 地图内算法方式
/// </summary>
public interface EM_AlgorithmArea : EM_IAlgorithm {
	void Perform(EM_GraphBase g, int curMapID,int orgAreaGID,int toAreaGID, int curLev);
}
