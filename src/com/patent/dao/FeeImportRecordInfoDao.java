package com.patent.dao;

import java.util.List;

import org.hibernate.Session;

import com.patent.module.FeeImportRecordInfo;

public interface FeeImportRecordInfoDao {

	/**
	 * 根据主键加载已上传缴费清单信息实体
	 * @param sess 持久化操作所需要的Hiberate Session
	 * @param id 需要加载的已上传缴费清单信息的主键值
	 * @return 加载的已上传缴费清单信息PO
	 */
	FeeImportRecordInfo get(Session sess,int id);
	
	/**
	 * 保存已上传缴费清单信息实体，新增一条已上传缴费清单信息记录
	 * @param firInfo 保存的已上传缴费清单信息实例
	 */
	void save(Session sess,FeeImportRecordInfo firInfo);
	
	/**
	 * 根据条件分页获取已上传缴费清单导出信息列表
	 * @description
	 * @author Administrator
	 * @date 2018-12-10 上午11:47:19
	 * @param sess
	 * @param addDateS 开始时间
	 * @param addDateE 结束时间
	 * @param cpyId 代理机构编号
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<FeeImportRecordInfo> findPageInfoByOpt(Session sess,String addDateS,String addDateE,Integer cpyId,Integer pageNo,Integer pageSize);
	
	/**
	 * 根据条件获取已上传缴费清单导出记录条数
	 * @description
	 * @author Administrator
	 * @date 2018-12-10 上午11:47:50
	 * @param sess
	 * @param addDateS 开始时间
	 * @param addDateE 结束时间
	 * @param cpyId 代理机构编号
	 * @return
	 */
	Integer getCountByOpt(Session sess,String addDateS, String addDateE,Integer cpyId);
}
