package com.patent.service;

import java.util.List;

import com.patent.exception.WEBException;
import com.patent.module.ActRoleInfoTb;

public interface ActRoleInfoManager {

	/**
	 * 增加角色动作信息
	 * @author Administrator
	 * @date 2018-8-7 下午10:20:29
	 * @ModifiedBy
	 * @param roleId 角色编号
	 * @param actId 动作编号
	 * @return
	 * @throws WEBException
	 */
	Integer addARole(Integer roleId,Integer actId) throws WEBException; 
	
	/**
	 * 批量增加角色动作绑定关系
	 * @description
	 * @author wm
	 * @date 2018-8-9 下午05:06:16
	 * @param roleId 角色编号
	 * @param actIdStr 动作编号组合
	 * @throws WEBException
	 */
	void addBatchARole(Integer roleId,String actIdStr) throws WEBException;
	
	/**
	 * 根据角色、模块动作编号获取角色动作绑定列表
	 * @author Administrator
	 * @date 2018-8-7 下午10:21:02
	 * @ModifiedBy
	 * @param roleId  角色编号（0表示全部）
	 * @param maId 模块动作编号（0表示全部）
	 * @return
	 * @throws WEBException
	 */
	List<ActRoleInfoTb> listInfoByOpt(Integer roleId,Integer maId) throws WEBException; 
	
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
	
	/**
	 * 批量删除指定主键编号组合的角色动作绑定关系
	 * @author Administrator
	 * @date 2018-8-9 下午10:26:28
	 * @ModifiedBy
	 * @param idStr 主键编号组合
	 * @throws WEBException
	 */
	void delBatchInfoById(String idStr) throws WEBException; 
	
	/**
	 * 获取指定角色、指定模块动作ENG的角色动作列表
	 * @description
	 * @author wm
	 * @date 2018-8-10 上午10:42:17
	 * @param roleId 角色编号
	 * @param actNameEng 模块动作ENG
	 * @return
	 * @throws WEBException
	 */
	List<ActRoleInfoTb> listInfoByOpt(Integer roleId,String actNameEng) throws WEBException; 
	
	/**
	 * 获取指定角色、指定模块下已拥有的权限
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2019-1-13 下午07:40:11
	 * @param roleId 角色编号
	 * @param modId 模块编号
	 * @return
	 * @throws WEBException
	 */
	List<ActRoleInfoTb> listInfoByOpt_1(Integer roleId,Integer modId) throws WEBException;
}
