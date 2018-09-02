package com.patent.dao;

import java.util.List;

import org.hibernate.Session;

import com.patent.module.ZlajLcInfoTb;

public interface ZlajLcInfoDao {
	/**
	 * 根据主键加载专利案件流程信息实体
	 * @param sess 持久化操作所需要的Hiberate Session
	 * @param id 需要加载的专利案件流程信息的主键值
	 * @return 加载的专利案件流程信息PO
	 */
	ZlajLcInfoTb get(Session sess,int id);
	
	/**
	 * 保存专利案件流程信息实体，新增一条专利案件流程信息记录
	 * @param lcInfo 保存的专利案件流程信息实例
	 */
	void save(Session sess,ZlajLcInfoTb lcInfo);
	
	/**
	 * 根据主键删除专利案件流程信息实体，删除一条专利案件流程信息记录
	 * @param id 需要删除专利案件流程信息的主键
	 */
	void delete(Session sess,int id);
	
	/**
	 * 更新一条专利案件流程信息记录
	 * @param lcInfo 需要更新的专利案件流程信息
	 */
	void update(Session sess,ZlajLcInfoTb lcInfo);
	
	/**
	* 获取专利额外要求信息列表(按照流程编号降序排列)
	*  @author  Administrator
	*  @ModifiedBy  
	*  @date  2018-8-21 下午09:14:17
	*  @param sess
	*  @param yqType 类型（fm,sy,wg）""表示全部
	*  @return
	 */
	List<ZlajLcInfoTb> findInfo(Session sess,Integer ajId);
	
	/**
	 * 根据主键编号获取额外要求信息
	*  @author  Administrator
	*  @ModifiedBy  
	*  @date  2018-8-21 下午09:59:06
	*  @param sess
	*  @param id 主键
	*  @return
	 */
	List<ZlajLcInfoTb> findInfoById(Session sess,Integer id);
}
