package com.patent.dao;

import java.util.List;

import org.hibernate.Session;

import com.patent.module.CustomerFmrInfoTb;

public interface CustomerFmrInfoDao {
	/**
	 * 根据主键加载专利机构客户发明人信息实体
	 * @param sess 持久化操作所需要的Hiberate Session
	 * @param id 需要加载的专利机构客户发明人信息的主键值
	 * @return 加载的专利机构客户发明人信息PO
	 */
	CustomerFmrInfoTb get(Session sess,int id);
	
	/**
	 * 保存专利机构客户发明人信息实体，新增一条专利机构客户发明人信息记录
	 * @param cusLxrInfo 保存的专利机构客户发明人信息实例
	 */
	void save(Session sess,CustomerFmrInfoTb cusLxrInfo);
	
	/**
	 * 删除专利机构客户发明人信息实体，删除一条专利机构客户发明人信息记录
	 * @param cusLxrInfo 删除的专利机构客户发明人信息实体
	 */
	void delete(Session sess,CustomerFmrInfoTb cusLxrInfo);
	
	/**
	 * 根据主键删除专利机构客户发明人信息实体，删除一条专利机构客户发明人信息记录
	 * @param id 需要删除专利机构客户发明人信息的主键
	 */
	void delete(Session sess,int id);
	
	/**
	 * 更新一条专利机构客户发明人信息记录
	 * @param cusLxrInfo 需要更新的专利机构客户发明人信息
	 */
	void update(Session sess,CustomerFmrInfoTb cusLxrInfo);
	
	/**
	 * 获取指定客户的发明人信息列表
	 * @description
	 * @author wm
	 * @date 2018-8-19 下午04:26:38
	 * @param sess
	 * @param cusId 客户编号
	 * @return
	 */
	List<CustomerFmrInfoTb> findInfoByCusId(Session sess,Integer cusId);
	
	/**
	 * 根据主键、代理机构编号获取发明人信息
	 * @description
	 * @author wm
	 * @date 2018-8-21 上午10:31:51
	 * @param sess
	 * @param fmrId 发明人编号（0时表示全部）
	 * @param cpyId 代理机构编号
	 * @return
	 */
	List<CustomerFmrInfoTb> findInfoByOpt(Session sess,Integer fmrId,Integer cpyId);
}
