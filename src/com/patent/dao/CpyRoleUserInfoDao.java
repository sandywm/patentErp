package com.patent.dao;

import java.util.List;

import org.hibernate.Session;

import com.patent.module.CpyRoleUserInfoTb;

public interface CpyRoleUserInfoDao {
	/**
	 * 根据主键加载代理机构用户角色关联实体
	 * @param sess 持久化操作所需要的Hiberate Session
	 * @param id 需要加载的代理机构用户角色关联的主键值
	 * @return 加载的代理机构用户角色关联PO
	 */
	CpyRoleUserInfoTb get(Session sess,int id);
	
	/**
	 * 保存代理机构用户角色关联实体，新增一条代理机构用户角色关联记录
	 * @param cpyRuInfo 保存的代理机构用户角色关联实例
	 */
	void save(Session sess,CpyRoleUserInfoTb cpyRuInfo);
	
	/**
	 * 删除代理机构用户角色关联实体，删除一条代理机构用户角色关联记录
	 * @param cpyRuInfo 删除的代理机构用户角色关联实体
	 */
	void delete(Session sess,CpyRoleUserInfoTb cpyRuInfo);
	
	/**
	 * 根据主键删除代理机构用户角色关联实体，删除一条代理机构用户角色关联记录
	 * @param id 需要删除代理机构用户角色关联的主键
	 */
	void delete(Session sess,int id);
	
	/**
	 * 更新一条代理机构用户角色关联记录
	 * @param cpyRuInfo 需要更新的代理机构用户角色关联
	 */
	void update(Session sess,CpyRoleUserInfoTb cpyRuInfo);
	
	/**
	 * 根据条件获取用户角色关联信息列表
	 * @description
	 * @author wm
	 * @date 2018-7-24 下午05:23:57
	 * @param sess
	 * @param roleId 角色编号（0时为全部）
	 * @param userId 用户编号（0时为全部）
	 * @return
	 */
	List<CpyRoleUserInfoTb> findInfoByOpt(Session sess,Integer roleId,Integer userId);
}
