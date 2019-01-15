package com.patent.dao;

import java.util.List;

import org.hibernate.Session;

import com.patent.module.FeeTypeInfoTb;

public interface FeeTypeInfoDao {

	/**
	 * 根据主键加载费用类型信息实体
	 * @param sess 持久化操作所需要的Hiberate Session
	 * @param id 需要加载的费用类型信息的主键值
	 * @return 加载的费用类型信息PO
	 */
	FeeTypeInfoTb getTypeEntityById(Session sess,int id);
	
	/**
	 * 根据条件获取费用信息列表
	 * @description
	 * @author wm
	 * @date 2018-9-4 下午04:24:11
	 * @param sess
	 * @param feeStatus （""表示全部）
	 * @return
	 */
	List<FeeTypeInfoTb> findInfoByStatus(Session sess,String feeStatus);
	
	/**
	 * 根据费用名称获取费用类型信息列表
	 * @description
	 * @author Administrator
	 * @date 2018-10-14 上午09:29:36
	 * @param sess
	 * @param feeName 费用名称
	 * @return
	 */
	List<FeeTypeInfoTb> findInfoByName(Session sess,String feeName);
	
	/**
	 * 根据专利类型获取专利费用信息列表
	 * @description
	 * @author Administrator
	 * @date 2019-1-15 下午03:54:08
	 * @param sess
	 * @param zlType 专利类型(fm,xx,wg)
	 * @return
	 */
	List<FeeTypeInfoTb> findInfoByzlType(Session sess,String zlType);
}
