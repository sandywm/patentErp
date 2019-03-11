package com.patent.dao;

import java.util.List;

import org.hibernate.Session;

import com.patent.module.ZlajLcMxInfoTb;

public interface ZlajLcMxInfoDao {
	/**
	 * 根据主键加载专利案件流程明细信息实体
	 * @param sess 持久化操作所需要的Hiberate Session
	 * @param id 需要加载的专利案件流程明细信息的主键值
	 * @return 加载的专利案件流程明细信息PO
	 */
	ZlajLcMxInfoTb get(Session sess,int id);
	
	/**
	 * 保存专利案件流程明细信息实体，新增一条专利案件流程明细信息记录
	 * @param lcMxInfo 保存的专利案件流程明细信息实例
	 */
	void save(Session sess,ZlajLcMxInfoTb lcMxInfo);
	
	/**
	 * 根据主键删除专利案件流程明细信息实体，删除一条专利案件流程明细信息记录
	 * @param id 需要删除专利案件流程明细信息的主键
	 */
	void delete(Session sess,int id);
	
	/**
	 * 更新一条专利案件流程明细信息记录
	 * @param lcMxInfo 需要更新的专利案件流程明细信息
	 */
	void update(Session sess,ZlajLcMxInfoTb lcMxInfo);
	
	/**
	 * 根据流程编号获取流程明细列表
	 * @description
	 * @author wm
	 * @date 2018-9-2 下午05:25:33
	 * @param sess
	 * @param lcId 流程编号
	 * @return
	 */
	List<ZlajLcMxInfoTb> findDetailInfoByLcId(Session sess,Integer lcId);
	
	/**
	 * 根据流程编号获取第一条记录(最后一个动作)（id降序排列）
	 * @description
	 * @author wm
	 * @date 2018-9-2 下午05:29:13
	 * @param sess
	 * @param lcId 流程编号
	 * @return
	 */
	List<ZlajLcMxInfoTb> findLastInfoByLcId(Session sess,Integer lcId);
	
	/**
	 * 根据流程主键获取第一个动作（获取未领取的流程明细）
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2018-9-13 下午10:49:40
	 * @param sess
	 * @param lcId 流程编号
	 * @return
	 */
	List<ZlajLcMxInfoTb> findFirstInfoByLcId(Session sess,Integer lcId);
	
	/**
	 * 根据流程明细名称、流程号、流程主键编号获取未完成的流程明细列表
	 * @description
	 * @author Administrator
	 * @date 2018-10-5 上午11:38:33
	 * @param sess
	 * @param lcMxName 流程明细名称
	 * @param lcMxNo 流程号
	 * @param lcId 流程主键编号
	 * @return
	 */
	List<ZlajLcMxInfoTb> listUnComInfoByOpt(Session sess,String lcMxName,Double lcMxNo, Integer lcId);
	
	/**
	 * 根据流程明细主键获取流程明细列表
	 * @description
	 * @author Administrator
	 * @date 2018-10-6 上午10:00:17
	 * @param sess
	 * @param mxId 流程明细主键
	 * @return
	 */
	List<ZlajLcMxInfoTb> findDetailInfoById(Session sess,Integer mxId);
	
	/**
	 * 查看指定专利、指定流程明细有无记录（批量导入时，如果出现先后顺序混乱时使用）
	 * @description
	 * @author Administrator
	 * @date 2018-10-7 下午03:50:34
	 * @param sess
	 * @param zlId 专利编号
	 * @param lcMxName 流程明细
	 * @return
	 */
	List<ZlajLcMxInfoTb> findSpecInfoByOpt(Session sess,Integer zlId,String lcMxName);
	
	/**
	 * 根据条件分页获取任务记录列表(新申请撰稿开始)
	 * 如果是未完成的就还需要获取所有没有移交任务或者移交审核未通过的
	 * 如果是已完成的就不须有后续条件
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2018-11-15 下午09:14:12
	 * @param sess
	 * @param fzUserId 流程负责人编号（0表示全部）
	 * @param comStatus 完成状态（0：未完成，1：已完成）
	 * @param cpyId 代理机构编号（流程负责人大于0时不用传递）
	 * @param zlNo 专利号(""时表示全部)
	 * @param ajNo 案件号(""时表示全部)
	 * @param zlTitle 专利名称(""时表示全部)
	 * @param cusId 客户编号(""时表示全部)
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<ZlajLcMxInfoTb> findLcMxByOpt(Session sess,Integer fzUserId,Integer comStatus,
			String zlNo,String ajNo,String zlTitle,Integer cusId,Integer cpyId,Integer pageNo,Integer pageSize);
	
	/**
	 * 根据条件获取任务记录条数(新申请撰稿开始)
	 * 如果是未完成的就还需要获取所有没有移交任务或者移交审核未通过的
	 * 如果是已完成的就不须有后续条件
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2018-11-15 下午09:15:42
	 * @param sess
	 * @param fzUserId 流程负责人编号（0表示全部）
	 * @param comStatus 完成状态（0：未完成，1：已完成）
	 * @param cpyId 代理机构编号（流程负责人大于0时不用传递）
	 * @param zlNo 专利号(""时表示全部)
	 * @param ajNo 案件号(""时表示全部)
	 * @param zlTitle 专利名称(""时表示全部)
	 * @param cusId 客户编号(""时表示全部)
	 * @return
	 */
	Integer getCountByOpt(Session sess,Integer fzUserId,Integer comStatus,String zlNo,String ajNo,
			String zlTitle,Integer cusId,Integer cpyId);
	
	/**
	 * 获取当前指定负责人指定专利下未完成的流程任务
	 * @description
	 * @author Administrator
	 * @date 2019-1-14 下午01:07:11
	 * @param sess
	 * @param fzUserId 负责人编号
	 * @param zlId 专利编号
	 * @return
	 */
	List<ZlajLcMxInfoTb> findUnComLcMxByOpt(Session sess,Integer fzUserId,Integer zlId);
	
	/**
	 * 获取指定专利的补正通知书、补正提交时的文件
	 * @description
	 * @author Administrator
	 * @date 2019-3-11 上午09:12:55
	 * @param sess
	 * @param zlId 专利编号
	 * @return
	 */
	List<ZlajLcMxInfoTb> findSpecFjInfoByOpt(Session sess,Integer zlId);
}
