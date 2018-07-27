package com.patent.dao;

import java.util.List;

import org.hibernate.Session;

import com.patent.module.CpyRoleInfoTb;


public interface CpyRoleInfoDao {
	/**
	 * 根据主键加载代理机构角色实体
	 * @param sess 持久化操作所需要的Hiberate Session
	 * @param id 需要加载的代理机构角色的主键值
	 * @return 加载的代理机构角色PO
	 */
	CpyRoleInfoTb get(Session sess,int id);
	
	/**
	 * 保存代理机构角色实体，新增一条代理机构角色记录
	 * @param cpyRole 保存的代理机构角色实例
	 */
	void save(Session sess,CpyRoleInfoTb cpyRole);
	
	/**
	 * 删除代理机构角色实体，删除一条代理机构角色记录
	 * @param cpyRole 删除的代理机构角色实体
	 */
	void delete(Session sess,CpyRoleInfoTb cpyRole);
	
	/**
	 * 根据主键删除代理机构角色实体，删除一条代理机构角色记录
	 * @param id 需要删除代理机构角色的主键
	 */
	void delete(Session sess,int id);
	
	/**
	 * 更新一条代理机构角色记录
	 * @param cpyRole 需要更新的代理机构角色
	 */
	void update(Session sess,CpyRoleInfoTb cpyRole);
	
	/**
	 * 获取指定代理机构下的角色身份
	 * @description
	 * @author wm
	 * @date 2018-7-24 上午09:39:00
	 * @param sess
	 * @param cpyId 代理机构编号
	 * @return
	 */
	List<CpyRoleInfoTb> findInfoByCpyId(Session sess,Integer cpyId);
}
