package com.patent.dao;

import java.util.List;

import org.hibernate.Session;

import com.patent.module.ActRoleInfoTb;

public interface ActRoleInfoDao {
	/**
	 * 根据主键加载角色权限管理信息实体
	 * @param sess 持久化操作所需要的Hiberate Session
	 * @param id 需要加载的角色权限管理信息的主键值
	 * @return 加载的角色权限管理信息PO
	 */
	ActRoleInfoTb get(Session sess,int id);
	
	/**
	 * 保存角色权限管理信息实体，新增一条角色权限管理信息记录
	 * @param arInfo 保存的角色权限管理信息实例
	 */
	void save(Session sess,ActRoleInfoTb arInfo);
	
	/**
	 * 删除角色权限管理信息实体，删除一条角色权限管理信息记录
	 * @param arInfo 删除的角色权限管理信息实体
	 */
	void delete(Session sess,ActRoleInfoTb arInfo);
	
	/**
	 * 根据主键删除角色权限管理信息实体，删除一条角色权限管理信息记录
	 * @param id 需要删除角色权限管理信息的主键
	 */
	void delete(Session sess,int id);
	
	/**
	 * 更新一条角色权限管理信息记录
	 * @param arInfo 需要更新的角色权限管理信息
	 */
	void update(Session sess,ActRoleInfoTb arInfo);
	
	/**
	 * 获取指定角色、指定动作模块编号的模块动作列表
	 * @description
	 * @author wm
	 * @date 2018-8-7 下午05:47:13
	 * @param sess
	 * @param roleId 角色编号 （0时表示全部）
	 * @param maId 模块动作编号（0时表示全部）
	 * @return
	 */
	List<ActRoleInfoTb> findSpecInfoByOpt(Session sess,Integer roleId,Integer maId);
	
	/**
	 * 获取指定角色、指定模块动作ENG的角色动作列表
	 * @description
	 * @author wm
	 * @date 2018-8-10 上午10:26:07
	 * @param sess
	 * @param roleId 角色编号
	 * @param actNameEng 模块动作ENG
	 * @return
	 */
	List<ActRoleInfoTb> findSpecInfoByOpt(Session sess,Integer roleId,String actNameEng);
	
	/**
	 * 获取指定角色、指定模块下已拥有的权限
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2019-1-13 下午07:32:58
	 * @param sess
	 * @param roleId 角色编号
	 * @param modId 模块编号
	 * @return
	 */
	List<ActRoleInfoTb> findSpecInfoByOpt_1(Session sess,Integer roleId,Integer modId);
}
