package com.patent.dao;

import java.util.List;

import org.hibernate.Session;

import com.patent.module.ZlajEwyqInfoTb;

public interface ZlajEwyqInfoDao {
	/**
	 * 根据主键加载专利案件信息实体
	 * @param sess 持久化操作所需要的Hiberate Session
	 * @param id 需要加载的专利案件信息的主键值
	 * @return 加载的专利案件信息PO
	 */
	ZlajEwyqInfoTb get(Session sess,int id);
	
	/**
	 * 保存专利案件信息实体，新增一条专利案件信息记录
	 * @param yqInfo 保存的专利案件信息实例
	 */
	void save(Session sess,ZlajEwyqInfoTb yqInfo);
	
	/**
	 * 根据主键删除专利案件信息实体，删除一条专利案件信息记录
	 * @param id 需要删除专利案件信息的主键
	 */
	void delete(Session sess,int id);
	
	/**
	 * 更新一条专利案件信息记录
	 * @param yqInfo 需要更新的专利案件信息
	 */
	void update(Session sess,ZlajEwyqInfoTb yqInfo);
	
	/**
	* 获取专利额外要求信息列表
	*  @author  Administrator
	*  @ModifiedBy  
	*  @date  2018-8-21 下午09:14:17
	*  @param sess
	*  @param yqType 类型（fm,sy,wg）""表示全部
	*  @return
	 */
	List<ZlajEwyqInfoTb> findInfo(Session sess,String yqType);
	
	/**
	*  根据主键编号获取额外要求信息
	*  @author  Administrator
	*  @ModifiedBy  
	*  @date  2018-8-21 下午09:19:14
	*  @param sess
	*  @param id 主键
	*  @return
	 */
	ZlajEwyqInfoTb getEntityById(Session sess,Integer id);
	
	/**
	 * 根据要求内容获取要求信息
	*  @author  Administrator
	*  @ModifiedBy  
	*  @date  2018-8-21 下午09:59:06
	*  @param sess
	*  @param yqContent 要求名字
	*  @return
	 */
	List<ZlajEwyqInfoTb> findInfoByCnt(Session sess,String yqContent);
}
