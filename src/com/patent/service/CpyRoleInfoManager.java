package com.patent.service;

import java.util.List;

import com.patent.exception.WEBException;
import com.patent.module.CpyRoleInfoTb;
import com.patent.module.CpyRoleUserInfoTb;

public interface CpyRoleInfoManager {

	/**
	 * 增加代理机构角色
	 * @description
	 * @author wm
	 * @date 2018-7-24 下午05:20:23
	 * @param roleName 角色名称
	 * @param roleProfile 角色简介
	 * @param cpyId 代理机构编号
	 * @return
	 * @throws WEBException
	 */
	Integer addRole(String roleName,String roleProfile,Integer cpyId) throws WEBException;
	
	/**
	 * 修改代理机构角色
	 * @description
	 * @author wm
	 * @date 2018-7-24 下午05:20:36
	 * @param id
	 * @param roleName 角色名称
	 * @param roleProfile 角色简介
	 * @return
	 * @throws WEBException
	 */
	boolean updateRoleById(Integer id,String roleName,String roleProfile) throws WEBException;
	
	/**
	 * 增加员工角色关联信息
	 * @description
	 * @author wm
	 * @date 2018-7-24 下午05:28:31
	 * @param roleId 角色编号
	 * @param userId 员工编号
	 * @return
	 * @throws WEBException
	 */
	Integer addRoleUser(Integer roleId,Integer userId) throws WEBException;
	
	/**
	 * 删除指定用户指定角色身份
	 * @description
	 * @author wm
	 * @date 2018-7-24 下午05:33:41
	 * @param roleId
	 * @param userId
	 * @return
	 * @throws WEBException
	 */
	boolean delRoleUserByOpt(Integer roleId,Integer userId) throws WEBException;
	
	/**
	 * 获取指定代理机构的角色信息列表
	 * @description
	 * @author wm
	 * @date 2018-7-25 上午08:55:36
	 * @param cpyId 指定代理机构编号
	 * @return
	 * @throws WEBException
	 */
	List<CpyRoleInfoTb> listInfoByCpyId(Integer cpyId) throws WEBException;
	
	/**
	 * 获取指定代理机构员工的角色信息列表
	 * @description
	 * @author wm
	 * @date 2018-7-25 下午03:45:22
	 * @param userId
	 * @return
	 * @throws WEBException
	 */
	List<CpyRoleUserInfoTb> listInfoByUserId(Integer userId) throws WEBException;
	
	/**
	 * 获取指定代理机构指定角色名称的信息列表
	 * @description
	 * @author wm
	 * @date 2018-8-4 下午05:25:36
	 * @param cpyId 代理机构编号
	 * @param roleName 角色名称
	 * @return
	 * @throws WEBException
	 */
	List<CpyRoleInfoTb> listInfoByOpt(Integer cpyId,String roleName) throws WEBException;
	
	/**
	 * 根据主键获取角色信息列表
	 * @description
	 * @author wm
	 * @date 2018-8-4 下午05:39:39
	 * @param id 主键
	 * @return
	 * @throws WEBException
	 */
	List<CpyRoleInfoTb> listInfoById(Integer id) throws WEBException;
	
	/**
	 * 根据角色编号获取用户列表
	 * @author Administrator
	 * @date 2018-8-4 下午10:23:16
	 * @ModifiedBy
	 * @param roleId 角色编号
	 * @return
	 * @throws WEBException
	 */
	List<CpyRoleUserInfoTb> listInfoByRoleId(Integer roleId) throws WEBException;
	
	/**
	 * 根据角色编号删除角色信息
	 * @author Administrator
	 * @date 2018-8-4 下午10:28:11
	 * @ModifiedBy
	 * @param roleId 角色编号
	 * @return
	 * @throws WEBException
	 */
	boolean delRoleById(Integer roleId) throws WEBException;
}
