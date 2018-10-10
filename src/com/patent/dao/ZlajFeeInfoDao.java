package com.patent.dao;

import java.util.List;

import org.hibernate.Session;

import com.patent.module.ZlajFeeInfoTb;

public interface ZlajFeeInfoDao {
	
	/**
	 * 根据主键加载专利案件缴费信息实体
	 * @param sess 持久化操作所需要的Hiberate Session
	 * @param id 需要加载的专利案件缴费信息的主键值
	 * @return 加载的专利案件缴费信息PO
	 */
	ZlajFeeInfoTb get(Session sess,int id);
	
	/**
	 * 保存专利案件缴费信息实体，新增一条专利案件缴费信息记录
	 * @param feeInfo 保存的专利案件缴费信息实例
	 */
	void save(Session sess,ZlajFeeInfoTb feeInfo);
	
	/**
	 * 根据主键删除专利案件缴费信息实体，删除一条专利案件缴费信息记录
	 * @param id 需要删除专利案件缴费信息的主键
	 */
	void delete(Session sess,int id);
	
	/**
	 * 更新一条专利案件缴费信息记录
	 * @param feeInfo 需要更新的专利案件缴费信息
	 */
	void update(Session sess,ZlajFeeInfoTb feeInfo);
	
	
	/**
	*  根据主键编号获取缴费信息
	*  @author  Administrator
	*  @ModifiedBy  
	*  @date  2018-8-21 下午09:19:14
	*  @param sess
	*  @param id 主键
	*  @return
	 */
	ZlajFeeInfoTb getFeeEntityById(Session sess,Integer id);
	
	/**
	 * 根据条件获取费用缴纳信息表
	 * @description
	 * @author wm
	 * @date 2018-9-4 下午04:28:52
	 * @param sess
	 * @param zlId 专利编号
	 * @param feeTypeStatus 费用类型（""表示全部）
	 * @return
	 */
	List<ZlajFeeInfoTb> findInfoByOpt(Session sess,Integer zlId,String feeTypeStatus);
	
	/**
	 * 根据专利编号、费用类型编号获取专利案件缴费列表
	 * @description
	 * @author Administrator
	 * @date 2018-10-10 上午11:16:24
	 * @param sess
	 * @param zlId 专利编号
	 * @param feeTypeId 费用类型
	 * @return
	 */
	List<ZlajFeeInfoTb> findInfoByOpt(Session sess,Integer zlId,Integer feeTypeId);
}
