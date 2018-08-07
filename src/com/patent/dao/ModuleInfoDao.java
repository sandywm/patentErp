package com.patent.dao;

import java.util.List;

import org.hibernate.Session;

import com.patent.module.ModuleInfoTb;

public interface ModuleInfoDao {

	/**
	 * 根据主键加载模块信息实体
	 * @param sess 持久化操作所需要的Hiberate Session
	 * @param id 需要加载的模块信息的主键值
	 * @return 加载的模块信息PO
	 */
	ModuleInfoTb get(Session sess,int id);
	
	/**
	 * 保存模块信息实体，新增一条模块信息记录
	 * @param mInfo 保存的模块信息实例
	 */
	void save(Session sess,ModuleInfoTb mInfo);
	
	/**
	 * 删除模块信息实体，删除一条模块信息记录
	 * @param mInfo 删除的模块信息实体
	 */
	void delete(Session sess,ModuleInfoTb mInfo);
	
	/**
	 * 根据主键删除模块信息实体，删除一条模块信息记录
	 * @param id 需要删除模块信息的主键
	 */
	void delete(Session sess,int id);
	
	/**
	 * 更新一条模块信息记录
	 * @param mInfo 需要更新的模块信息
	 */
	void update(Session sess,ModuleInfoTb mInfo);
	
	/**
	 * 列出所有模块列表
	 * @description
	 * @author wm
	 * @date 2018-8-7 下午05:41:34
	 * @param sess
	 * @return
	 */
	List<ModuleInfoTb> findAllInfo(Session sess);
}
