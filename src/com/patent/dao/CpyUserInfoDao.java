package com.patent.dao;

import java.util.List;

import org.hibernate.Session;

import com.patent.module.CpyUserInfo;


public interface CpyUserInfoDao {
	/**
	 * 根据主键加载代理机构用户实体
	 * @param sess 持久化操作所需要的Hiberate Session
	 * @param id 需要加载的代理机构用户的主键值
	 * @return 加载的代理机构用户PO
	 */
	CpyUserInfo get(Session sess,int id);
	
	/**
	 * 保存代理机构用户实体，新增一条代理机构用户记录
	 * @param cpyUser 保存的代理机构用户实例
	 */
	void save(Session sess,CpyUserInfo cpyUser);
	
	/**
	 * 删除代理机构用户实体，删除一条代理机构用户记录
	 * @param cpyUser 删除的代理机构用户实体
	 */
	void delete(Session sess,CpyUserInfo cpyUser);
	
	/**
	 * 根据主键删除代理机构用户实体，删除一条代理机构用户记录
	 * @param id 需要删除代理机构用户的主键
	 */
	void delete(Session sess,int id);
	
	/**
	 * 更新一条代理机构用户记录
	 * @param cpyUser 需要更新的代理机构用户
	 */
	void update(Session sess,CpyUserInfo cpyUser);
	
	/**
	 * 根据账号获取用户信息
	 * @description
	 * @author wm
	 * @date 2018-7-22 上午09:43:18
	 * @param sess
	 * @param account 账号
	 * @return
	 */
	List<CpyUserInfo> findInfoByAccount(Session sess,String account);
	
	/**
	 * 根据主键编号获取代理机构用户信息
	 * @description
	 * @author wm
	 * @date 2018-7-22 上午09:44:55
	 * @param sess
	 * @param id
	 * @return
	 */
	List<CpyUserInfo> findInfoById(Session sess,Integer id);
	
	/**
	 * 根据条件分页获取代理机构员工记录列表
	 * @description
	 * @author wm
	 * @date 2018-7-22 上午09:51:51
	 * @param sess
	 * @param cpyId 代理机构编号
	 * @param userLzStatus 员工离职状态（-1表示全部）
	 * @param userYxStatus 员工账号有效状态(-1表示全部)
	 * @param roleId 员工角色（-1表示全部）
	 * @param userNamePy 用户姓名拼音（""表示全部）
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<CpyUserInfo> findPageInfoByOpt(Session sess,Integer cpyId,Integer userLzStatus,Integer userYxStatus,
			Integer roleId,String userNamePy,Integer pageNo,Integer pageSize);
	
	
	/**
	 * 根据条件获取代理机构员工记录条数
	 * @description
	 * @author wm
	 * @date 2018-7-22 上午09:58:35
	 * @param sess
	 * @param cpyId 代理机构编号
	 * @param userLzStatus 员工离职状态（-1表示全部）
	 * @param userYxStatus 员工账号有效状态(-1表示全部)
	 * @param roleId 员工角色（-1表示全部）
	 * @param userNamePy 用户姓名拼音（""表示全部）
	 * @return
	 */
	Integer getCountByOpt(Session sess,Integer cpyId,Integer userLzStatus,Integer userYxStatus,Integer roleId,String userNamePy);
	
	/**
	 * 获取指定代理机构所有的管理员用户信息（在职、有效）--定时发送到期邮件用
	 * @description
	 * @author wm
	 * @date 2018-8-6 下午05:12:14
	 * @param sess
	 * @param cpyId 代理机构编号
	 * @param roleName 角色(管理员)
	 * @return
	 */
	List<CpyUserInfo> findManagerInfoByOpt(Session sess,Integer cpyId,String roleName);
	
	/**
	 * 获取指定代理机构下擅长指定专业的员工列表
	 * @description
	 * @author wm
	 * @date 2018-8-8 上午10:55:50
	 * @param sess
	 * @param scFieldId 擅长专业编号
	 * @param cpyId 代理机构编号
	 * @return
	 */
	List<CpyUserInfo> findInfoByOpt(Session sess,Integer scFieldId,Integer cpyId);
}
