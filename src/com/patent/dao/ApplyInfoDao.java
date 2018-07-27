package com.patent.dao;

import java.util.List;

import org.hibernate.Session;

import com.patent.module.ApplyInfoTb;

public interface ApplyInfoDao {
	/**
	 * 根据主键加载申请专利（人/公司）信息实体
	 * @param sess 持久化操作所需要的Hiberate Session
	 * @param id 需要加载的申请专利（人/公司）信息的主键值
	 * @return 加载的申请专利（人/公司）信息PO
	 */
	ApplyInfoTb get(Session sess,int id);
	
	/**
	 * 保存申请专利（人/公司）信息实体，新增一条申请专利（人/公司）信息记录
	 * @param aInfo 保存的申请专利（人/公司）信息实例
	 */
	void save(Session sess,ApplyInfoTb aInfo);
	
	/**
	 * 删除申请专利（人/公司）信息实体，删除一条申请专利（人/公司）信息记录
	 * @param aInfo 删除的申请专利（人/公司）信息实体
	 */
	void delete(Session sess,ApplyInfoTb aInfo);
	
	/**
	 * 根据主键删除申请专利（人/公司）信息实体，删除一条申请专利（人/公司）信息记录
	 * @param id 需要删除申请专利（人/公司）信息的主键
	 */
	void delete(Session sess,int id);
	
	/**
	 * 更新一条申请专利（人/公司）信息记录
	 * @param aInfo 需要更新的申请专利（人/公司）信息
	 */
	void update(Session sess,ApplyInfoTb aInfo);
	
	/**
	 * 根据条件分页获取申请专利（个人/公司）信息列表
	 * @description
	 * @author wm
	 * @date 2018-7-25 上午09:50:35
	 * @param appType 个人/公司类型（""表示全部）
	 * @param appNamePy 个人/公司拼音（模糊查询）（""表示全部）
	 * @param lxrTel 联系人电话（""表示全部）
	 * @return
	 */
	List<ApplyInfoTb> findPageInfoByOpt(Session sess,String appType,String appNamePy,String lxrTel,Integer pageNo,Integer pageSize);
	
	/**
	 * 获取申请专利（个人/公司）信息记录条数
	 * @description
	 * @author wm
	 * @date 2018-7-25 上午09:52:45
	 * @param appType 个人/公司类型（""表示全部）
	 * @param appNamePy 个人/公司拼音（模糊查询）（""表示全部）
	 * @param lxrTel 联系人电话（""表示全部）
	 * @return
	 */
	Integer getCountByOpt(Session sess,String appType,String appNamePy,String lxrTel);
	
	/**
	 * 根据主键获取详细信息
	 * @description
	 * @author wm
	 * @date 2018-7-25 上午09:54:05
	 * @param appId 主键编号
	 * @return
	 */
	ApplyInfoTb getEntityById(Session sess,Integer appId);
	
	/**
	 * 根据用户账号获取申请专利（个人/公司）信息列表
	 * @description
	 * @author wm
	 * @date 2018-7-25 上午11:15:05
	 * @param sess
	 * @param account 账号
	 * @return
	 */
	List<ApplyInfoTb> findInfoByAccount(Session sess,String account);
}
