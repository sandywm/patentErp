package com.patent.service;

import java.util.List;

import com.patent.exception.WEBException;
import com.patent.module.CpyUserInfo;

public interface CpyUserInfoManager {
	
	/**
	 * 增加代理机构员工信息
	 * @description
	 * @author wm
	 * @date 2018-7-24 上午09:20:14
	 * @param userName 员工姓名
	 * @param userNamePy 员工姓名拼音
	 * @param account 员工账号
	 * @param password 员工密码
	 * @param userSex 员工性别
	 * @param userEmail 员工邮箱
	 * @param userTel 员工电话
	 * @param userInDate 员工入职时间
	 * @param userScFiledName 员工擅长专业领域
	 * @param userScFieldIdStr 员工擅长专业领域
	 * @return
	 * @throws WEBException
	 */
	Integer addCpyUser(Integer cpyId, String userName,String userNamePy,
			String userAccount, String userPassword, String userSex,
			String userEmail, String userTel, String userInDate,
			String userScFieldIdStr, String userScFiledName) throws WEBException;
	
	/**
	 * 修改代理机构员工基本信息
	 * @description
	 * @author wm
	 * @date 2018-7-24 上午09:21:20
	 * @param id
	 * @param userName 员工姓名（""不修改）
	 * @param userNamePy 员工姓名拼音（""不修改）
	 * @param userSex 员工性别（""不修改）
	 * @param userEmail 员工邮箱（""不修改）
	 * @param userTel 员工电话（""不修改）
	 * @return
	 * @throws WEBException
	 */
	boolean updateBasicInfoById(Integer id,String userName,String userNamePy,
			String userSex,String userEmail,String userTel) throws WEBException;
	
	/**
	 * 修改代理机构员工高级信息
	 * @description
	 * @author wm
	 * @date 2018-7-24 上午09:23:43
	 * @param id
	 * @param newUserZxNum 新增的员工撰写数量（大于0修改）
	 * @param userScFiledIdStr 员工擅长专业领域修改(""时不修改)
	 * @param userScFiledNameStr 员工擅长专业领域修改(""时不修改)
	 * @param newUserExper 新增的员工经验（大于0修改）
	 * @return
	 * @throws WEBException
	 */
	boolean updateInfoById(Integer id,Integer newUserZxNum,String userScFiledIdStr,String userScFiledNameStr,Integer newUserExper) throws WEBException;
	
	/**
	 * 修改员工登录信息
	 * @description
	 * @author wm
	 * @date 2018-7-24 上午09:30:43
	 * @param id
	 * @param lastLoginDate 员工最后登录时间(""不修改)
	 * @param loginTimes 员工登录次数
	 * @return
	 * @throws WEBException
	 */
	boolean updateLoginInfoById(Integer id,String lastLoginDate,Integer loginTimes) throws WEBException;
	
	/**
	 * 删除指定员工
	 * @description
	 * @author wm
	 * @date 2018-7-24 上午09:32:12
	 * @param id
	 * @return
	 * @throws WEBException
	 */
	boolean delSpecUserById(Integer id) throws WEBException;
	
	/**
	 * 根据条件分页获取代理机构员工记录列表
	 * @description
	 * @author wm
	 * @date 2018-7-24 上午09:33:45
	 * @param cpyId 代理机构编号
	 * @param userLzStatus 员工离职状态（-1表示全部）
	 * @param userYxStatus 员工账号有效状态(-1表示全部)
	 * @param roleId 员工角色（-1表示全部）
	 * @param userNamePy 用户姓名拼音（""表示全部）
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws WEBException
	 */
	List<CpyUserInfo> listPageInfoByOpt(Integer cpyId,Integer userLzStatus,Integer userYxStatus,Integer roleId, 
			String userNamePy,Integer pageNo, Integer pageSize) throws WEBException;
	
	/**
	 * 根据条件获取代理机构员工记录条数
	 * @description
	 * @author wm
	 * @date 2018-7-24 上午09:34:21
	 * @param cpyId 代理机构编号
	 * @param userLzStatus 员工离职状态（-1表示全部）
	 * @param userYxStatus 员工账号有效状态(-1表示全部)
	 * @param roleId 员工角色（-1表示全部）
	 * @param userNamePy 用户姓名拼音（""表示全部）
	 * @return
	 * @throws WEBException
	 */
	Integer getCountByOpt(Integer cpyId,Integer userLzStatus,Integer userYxStatus,Integer roleId, 
			String userNamePy) throws WEBException;
	
	/**
	 * 根据账号获取代理机构员工账号信息列表
	 * @description
	 * @author wm
	 * @date 2018-7-25 上午10:41:36
	 * @param account 账号
	 * @return
	 * @throws WEBException
	 */
	List<CpyUserInfo> listSpecInfoByAccount(String account) throws WEBException;
	
	/**
	 * 根据主键编号获取代理机构用户详细信息
	 * @param id 主键
	 * @return
	 * @throws WEBException
	 */
	CpyUserInfo getEntityById(Integer id) throws WEBException;
	
	/**根据用户编号修改用户密码
	 * @author Administrator
	 * @date 2018-7-31 下午08:55:44
	 * @ModifiedBy
	 * @param id 主键
	 * @param newPass 新密码
	 * @return
	 * @throws WEBException
	 */
	boolean updatePassById(Integer id,String newPass) throws WEBException;
	
	/**
	 * 根据主键修改用户离职、账号有效状态
	 * @description
	 * @author wm
	 * @date 2018-8-3 下午04:39:59
	 * @param id
	 * @param outDate 离职时间（""时不修改）
	 * @param lzSatatus 离职状态（-1时不修改）
	 * @param yxStatus 有效状态（-1时不修改）
	 * @return
	 * @throws WEBException
	 */
	boolean updateUserInfoById(Integer id,String outDate,Integer lzSatatus,Integer yxStatus) throws WEBException;
	
	/**
	 * 获取指定代理机构所有的管理员用户信息（在职、有效）--定时发送到期邮件用
	 * @description
	 * @author wm
	 * @date 2018-8-6 下午05:10:30
	 * @param cpyId 代理机构编号
	 * @param roleName 角色(管理员)
	 * @return
	 * @throws WEBException
	 */
	List<CpyUserInfo> listManagerInfoByOpt(Integer cpyId,String roleName) throws WEBException;
	
	/**
	 * 获取指定代理机构下擅长指定专业的员工列表
	 * @description
	 * @author wm
	 * @date 2018-8-8 上午11:04:46
	 * @param scFieldId 擅长专业编号
	 * @param cpyId 代理机构编号
	 * @return
	 * @throws WEBException
	 */
	List<CpyUserInfo> listInfoByOpt(Integer cpyId,Integer scFieldId) throws WEBException;
}
