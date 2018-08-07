package com.patent.dao;

import java.util.List;

import org.hibernate.Session;

import com.patent.module.JsFiledInfoTb;

public interface JsFiledInfoDao {

	/**
	 * 根据主键加载专业技术区域信息实体
	 * @param sess 持久化操作所需要的Hiberate Session
	 * @param id 需要加载的专业技术区域信息的主键值
	 * @return 加载的专业技术区域信息PO
	 */
	JsFiledInfoTb get(Session sess,int id);
	
	/**
	 * 保存专业技术区域信息实体，新增一条专业技术区域信息记录
	 * @param jsInfo 保存的专业技术区域信息实例
	 */
	void save(Session sess,JsFiledInfoTb jsInfo);
	
	/**
	 * 删除专业技术区域信息实体，删除一条专业技术区域信息记录
	 * @param jsInfo 删除的专业技术区域信息实体
	 */
	void delete(Session sess,JsFiledInfoTb jsInfo);
	
	/**
	 * 根据主键删除专业技术区域信息实体，删除一条专业技术区域信息记录
	 * @param id 需要删除专业技术区域信息的主键
	 */
	void delete(Session sess,int id);
	
	/**
	 * 根据代理机构编号浏览所有的专业技术区域
	 * @author Administrator
	 * @date 2018-8-7 下午10:32:18
	 * @ModifiedBy
	 * @param cpyId 代理机构编号
	 * @param sess
	 * @return
	 */
	List<JsFiledInfoTb> findAllInfo(Session sess,Integer cpyId);
}
