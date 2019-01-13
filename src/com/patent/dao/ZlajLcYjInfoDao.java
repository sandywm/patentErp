package com.patent.dao;

import java.util.List;

import org.hibernate.Session;

import com.patent.module.ZlajLcYjInfoTb;

public interface ZlajLcYjInfoDao {
	/**
	 * 根据主键加载专利案件流程移交信息实体
	 * @param sess 持久化操作所需要的Hiberate Session
	 * @param id 需要加载的专利案件流程移交信息的主键值
	 * @return 加载的专利案件流程移交信息PO
	 */
	ZlajLcYjInfoTb get(Session sess,int id);
	
	/**
	 * 保存专利案件流程移交信息实体，新增一条专利案件流程移交信息记录
	 * @param lcyj 保存的专利案件流程移交信息实例
	 */
	void save(Session sess,ZlajLcYjInfoTb lcyj);
	
	/**
	 * 根据主键删除专利案件流程移交信息实体，删除一条专利案件流程移交信息记录
	 * @param id 需要删除专利案件流程移交信息的主键
	 */
	void delete(Session sess,int id);
	
	/**
	 * 更新一条专利案件流程移交信息记录
	 * @param lcyj 需要更新的专利案件流程移交信息
	 */
	void update(Session sess,ZlajLcYjInfoTb lcyj);
	
	/**
	 * 根据主键获取专利流程移交信息实体
	 * @description
	 * @author Administrator
	 * @date 2018-11-15 上午09:55:25
	 * @param sess
	 * @param id 主键编号
	 * @return
	 */
	ZlajLcYjInfoTb getEntityById(Session sess,Integer id);
	
	/**
	 * 根据条件分页获取申请记录列表(id降序)
	 * @description
	 * @author Administrator
	 * @date 2018-11-15 上午10:07:57
	 * @param applyUserId 申请人编号（0时表示全部）
	 * @param checkStauts 审核状态
	 * @param checkUserId 审核人员（0时表示全部）
	 * @param cpyId 代理机构编号
	 * @param zlTitle 专利名称(""时表示全部)
	 * @param ajNo 案件编号(""时表示全部)
	 * @param zlNo 专利号(""时表示全部)
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<ZlajLcYjInfoTb> findPageInfoByOpt(Session sess,Integer applyUserId,Integer checkStauts,
			Integer checkUserId,Integer cpyId,String zlTitle,String ajNo,String zlNo,
			Integer pageNo,Integer pageSize);
	
	/**
	 * 根据条件获取申请记录条数
	 * @description
	 * @author Administrator
	 * @date 2018-11-15 上午10:08:00
	 * @param applyUserId 申请人编号（0时表示全部）
	 * @param checkStauts 审核状态
	 * @param checkUserId 审核人员（0时表示全部）
	 * @param cpyId 代理机构编号
	 * @param zlTitle 专利名称(""时表示全部)
	 * @param ajNo 案件编号(""时表示全部)
	 * @param zlNo 专利号(""时表示全部)
	 * @return
	 */
	Integer getCountByOpt(Session sess,Integer applyUserId,Integer checkStauts,Integer checkUserId,
			Integer cpyId,String zlTitle,String ajNo,String zlNo);

	/**
	 * 获取指定流程
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2018-11-17 下午08:58:29
	 * @param sess
	 * @param lcTask
	 * @param mxId
	 * @return
	 */
	List<ZlajLcYjInfoTb> findUnCheckInfoByOpt(Session sess,String lcTask,Integer zlId);
	
	/**
	 * 获取指定专利、申请人、流程任务的流程移交实体（未审核）
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2018-11-27 下午08:35:31
	 * @param sess
	 * @param applyUserId 申请人
	 * @param lcTask 流程任务
	 * @param zlId 专利编号
	 * @return
	 */
	ZlajLcYjInfoTb findEntityByOpt(Session sess,Integer applyUserId,String lcTask,Integer zlId);
}
