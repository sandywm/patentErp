package com.patent.service;

import java.util.List;

import com.patent.exception.WEBException;
import com.patent.module.ActRoleInfoTb;

public interface ActRoleInfoManager {

	/**
	 * 增加角色动作信息
	 * @author Administrator
	 * @date 2018-8-7 下午10:20:28
	 * @ModifiedBy
	 * @param roleId 角色编号
	 * @param actId 动作编号
	 * @return
	 * @throws WEBException
	 */
	Integer addARole(Integer roleId,Integer actId) throws WEBException; 
	
	/**
	 * 根据角色获取该角色所有动作
	 * @author Administrator
	 * @date 2018-8-7 下午10:21:02
	 * @ModifiedBy
	 * @param roleId
	 * @return
	 * @throws WEBException
	 */
	List<ActRoleInfoTb> listInfoByRoleId(Integer roleId) throws WEBException; 
	
	/**
	 * 删除指定角色指定动作的角色动作绑定关系
	 * @author Administrator
	 * @date 2018-8-7 下午10:25:28
	 * @ModifiedBy
	 * @param roleId 角色编号
	 * @param actId 动作编号
	 * @return
	 * @throws WEBException
	 */
	boolean delInfoByOpt(Integer roleId,Integer actId) throws WEBException; 
}
