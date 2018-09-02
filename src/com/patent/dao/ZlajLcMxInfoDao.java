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
}
