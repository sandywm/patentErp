package com.patent.service;

import java.util.List;

import com.patent.exception.WEBException;
import com.patent.module.SuperUser;

public interface SuperUserManager {

	/**
	 * 增加平台用户
	 * @description
	 * @author wm
	 * @date 2018-7-26 上午08:29:01
	 * @param account 账号
	 * @param password 密码
	 * @param userName 真实姓名
	 * @param userType 用户身份
	 * @return
	 * @throws WEBException
	 */
	Integer addSUser(String account,String password,String userName,String userType) throws WEBException;
	
	/**
	 * 删除指定平台用户
	 * @description
	 * @author wm
	 * @date 2018-7-26 上午08:29:33
	 * @param id
	 * @return
	 * @throws WEBException
	 */
	boolean delSUser(Integer id) throws WEBException;
	
	/**
	 * 修改指定用户的信息
	 * @description
	 * @author wm
	 * @date 2018-7-26 上午08:29:44
	 * @param password 密码 （""时不修改）
	 * @param userName 用户姓名（""时不修改）
	 * @return
	 * @throws WEBException
	 */
	boolean updateSUserById(Integer userId,String password,String userName) throws WEBException;
	
	/**
	 * 根据用户编号获取用户信息
	 * @description
	 * @author wm
	 * @date 2018-7-26 上午08:30:05
	 * @param userId 用户编号(0时为全部)
	 * @return
	 * @throws WEBException
	 */
	List<SuperUser> listInfoById(Integer userId) throws WEBException;
	
	/**
	 * 根据用户账号获取用户信息
	 * @description
	 * @author wm
	 * @date 2018-7-26 上午08:30:19
	 * @param account
	 * @return
	 * @throws WEBException
	 */
	List<SuperUser> listInfoByAccount(String account) throws WEBException;
	
	/**
	 * 根据用户编号修改用户有效状态
	 * @description
	 * @author wm
	 * @date 2018-7-26 上午08:30:55
	 * @param userId 用户编号
	 * @param yxStatus 用户有效状态（-1不修改）
	 * @param loginTimes 登录次数（-1不修改）
	 * @return
	 * @throws WEBException
	 */
	boolean updateStatusById(Integer userId,Integer yxStatus,Integer loginTimes) throws WEBException;
	
	
}
