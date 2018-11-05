package com.patent.dao;

import java.util.List;

import org.hibernate.Session;

import com.patent.module.ZlajFeeSubInfoTb;

public interface ZlajFeeSubInfoDao {
	
	/**
	 * 根据主键加载角色权限管理信息实体(重写)
	 * @param sess 持久化操作所需要的Hiberate Session
	 * @param id 需要加载的角色权限管理信息的主键值
	 * @return 加载的角色权限管理信息PO
	 */
	ZlajFeeSubInfoTb get(Session sess,int id);
	
	/**
	 * 保存角色权限管理信息实体，新增一条角色权限管理信息记录
	 * @param fs 保存的角色权限管理信息实例
	 */
	void save(Session sess,ZlajFeeSubInfoTb fs);
	
	/**
	 * 删除角色权限管理信息实体，删除一条角色权限管理信息记录
	 * @param fs 删除的角色权限管理信息实体
	 */
	void delete(Session sess,ZlajFeeSubInfoTb fs);
	
	/**
	 * 根据主键删除角色权限管理信息实体，删除一条角色权限管理信息记录
	 * @param id 需要删除角色权限管理信息的主键
	 */
	void delete(Session sess,int id);
	
	/**
	 * 更新一条角色权限管理信息记录
	 * @param arInfo 需要更新的角色权限管理信息
	 */
	void update(Session sess,ZlajFeeSubInfoTb fs);
	
	/**
	 * 根据专利费用编号获取子费用列表
	 * @description
	 * @author Administrator
	 * @date 2018-11-3 上午09:04:46
	 * @param sess
	 * @param feeId 专利费用编号
	 * @return
	 */
	List<ZlajFeeSubInfoTb> findInfoByFeeId(Session sess,Integer feeId);
}
