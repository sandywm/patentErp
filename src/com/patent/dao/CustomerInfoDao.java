package com.patent.dao;

import java.util.List;

import org.hibernate.Session;

import com.patent.module.CustomerInfoTb;

public interface CustomerInfoDao {
	/**
	 * 根据主键加载专利机构客户信息实体
	 * @param sess 持久化操作所需要的Hiberate Session
	 * @param id 需要加载的专利机构客户信息的主键值
	 * @return 加载的专利机构客户信息PO
	 */
	CustomerInfoTb get(Session sess,int id);
	
	/**
	 * 保存专利机构客户信息实体，新增一条专利机构客户信息记录
	 * @param cusInfo 保存的专利机构客户信息实例
	 */
	void save(Session sess,CustomerInfoTb cusInfo);
	
	/**
	 * 删除专利机构客户信息实体，删除一条专利机构客户信息记录
	 * @param cusInfo 删除的专利机构客户信息实体
	 */
	void delete(Session sess,CustomerInfoTb cusInfo);
	
	/**
	 * 根据主键删除专利机构客户信息实体，删除一条专利机构客户信息记录
	 * @param id 需要删除专利机构客户信息的主键
	 */
	void delete(Session sess,int id);
	
	/**
	 * 更新一条专利机构客户信息记录
	 * @param cusInfo 需要更新的专利机构客户信息
	 */
	void update(Session sess,CustomerInfoTb cusInfo);
	
	/**
	 * 分页获取指定条件的客户管理信息列表
	 * @description
	 * @author wm
	 * @date 2018-8-19 上午10:17:27
	 * @param sess
	 * @param cpyId 代理机构编号
	 * @param cusName 客户名称(""时表示全部)
	 * @return
	 */
	List<CustomerInfoTb> findPageInfoByCpyId(Session sess,Integer cpyId,String cusName,Integer pageNo,Integer pageSize);
	
	/**
	 * 获取指定条件的客户管理信息记录条数
	 * @description
	 * @author wm
	 * @date 2018-8-19 上午11:32:08
	 * @param sess
	 * @param cpyId
	 * @return
	 */
	Integer getCountByCpyId(Session sess,Integer cpyId,String cusName);
	
	/**
	 * 根据主键和代理机构编号获取专利机构管理信息列表
	 * @description
	 * @author wm
	 * @date 2018-8-19 下午05:02:55
	 * @param sess
	 * @param cpyId 代理机构编号
	 * @param id 主键
	 * @return
	 */
	List<CustomerInfoTb> findInfoByOpt(Session sess,Integer cpyId,Integer id);
}
