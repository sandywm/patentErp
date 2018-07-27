package com.patent.dao;

import java.util.List;

import org.hibernate.Session;

import com.patent.module.SuperUser;

public interface SuperUserDao {
	/**
	 * 根据主键加载超级用户信息实体
	 * @param sess 持久化操作所需要的Hiberate Session
	 * @param id 需要加载的超级用户信息的主键值
	 * @return 加载的超级用户信息PO
	 */
	SuperUser get(Session sess,int id);
	
	/**
	 * 保存超级用户信息实体，新增一条超级用户信息记录
	 * @param su 保存的超级用户信息实例
	 */
	void save(Session sess,SuperUser su);
	
	/**
	 * 删除超级用户信息实体，删除一条超级用户信息记录
	 * @param su 删除的超级用户信息实体
	 */
	void delete(Session sess,SuperUser su);
	
	/**
	 * 根据主键删除超级用户信息实体，删除一条超级用户信息记录
	 * @param id 需要删除超级用户信息的主键
	 */
	void delete(Session sess,int id);
	
	/**
	 * 更新一条超级用户信息记录
	 * @param su 需要更新的超级用户信息
	 */
	void update(Session sess,SuperUser su);
	
	/**
	 * 根据账号获取超级用户信息
	 * @description
	 * @author wm
	 * @date 2018-7-26 上午08:19:11
	 * @param account
	 * @return
	 */
	List<SuperUser> findInfoByAccount(Session sess,String account);
	
	/**
	 * 根据用户编号获取用户信息列表
	 * @description
	 * @author wm
	 * @date 2018-7-26 上午08:38:19
	 * @param sess
	 * @param userId 用户编号(0时表示全部)
	 * @return
	 */
	List<SuperUser> findInfoById(Session sess,Integer userId);
}
