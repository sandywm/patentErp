package com.patent.dao;

import java.util.List;

import org.hibernate.Session;

import com.patent.module.ZlajTzsInfoTb;

public interface ZlajTzsInfoDao {
	/**
	 * 根据主键加载专利案件通知书信息实体
	 * @param sess 持久化操作所需要的Hiberate Session
	 * @param id 需要加载的专利案件通知书信息的主键值
	 * @return 加载的专利案件通知书信息PO
	 */
	ZlajTzsInfoTb get(Session sess,int id);
	
	/**
	 * 保存专利案件通知书信息实体，新增一条专利案件通知书信息记录
	 * @param tzsInfo 保存的专利案件通知书信息实例
	 */
	void save(Session sess,ZlajTzsInfoTb tzsInfo);
	
	/**
	 * 根据主键删除专利案件通知书信息实体，删除一条专利案件通知书信息记录
	 * @param id 需要删除专利案件通知书信息的主键
	 */
	void delete(Session sess,int id);
	
	/**
	 * 更新一条专利案件通知书信息记录
	 * @param tzsInfo 需要更新的专利案件通知书信息
	 */
	void update(Session sess,ZlajTzsInfoTb tzsInfo);
	
	
	/**
	*  根据主键编号获取通知书信息
	*  @author  Administrator
	*  @ModifiedBy  
	*  @date  2018-8-21 下午09:19:14
	*  @param sess
	*  @param id 主键
	*  @return
	 */
	ZlajTzsInfoTb getEntityById(Session sess,Integer id);
	
	/**
	 * 根据案件编号获取所有通知书
	*  @author  Administrator
	*  @ModifiedBy  
	*  @date  2018-8-21 下午09:59:06
	*  @param sess
	*  @param ajId 案件编号
	*  @return
	 */
	List<ZlajTzsInfoTb> findInfoByAjId(Session sess,Integer ajId);
}
