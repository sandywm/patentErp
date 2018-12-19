package com.patent.dao;

import java.util.List;

import org.hibernate.Session;

import com.patent.module.FeeImportDealRecordInfo;

public interface FeeImportDealRecordInfoDao {
	/**
	 * 根据主键加载已上传缴费清单处理详情处理详情信息实体
	 * @param sess 持久化操作所需要的Hiberate Session
	 * @param id 需要加载的已上传缴费清单处理详情信息的主键值
	 * @return 加载的已上传缴费清单处理详情信息PO
	 */
	FeeImportDealRecordInfo get(Session sess,int id);
	
	/**
	 * 保存已上传缴费清单处理详情信息实体，新增一条已上传缴费清单处理详情信息记录
	 * @param fidrInfo 保存的已上传缴费清单处理详情信息实例
	 */
	void save(Session sess,FeeImportDealRecordInfo fidrInfo);
	
	/**
	 * 根据条件分页获取已上传缴费清单处理详情导出信息列表
	 * @description
	 * @author Administrator
	 * @date 2018-12-10 上午11:47:19
	 * @param sess
	 * @param firId 费用导入编号
	 * @return
	 */
	List<FeeImportDealRecordInfo> findInfoByFirId(Session sess,Integer firId);
}
