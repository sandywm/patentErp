package com.patent.dao;

import java.util.List;

import org.hibernate.Session;

import com.patent.module.FeeExportRecordInfo;

public interface FeeExportRecordInfoDao {

	/**
	 * 根据主键加载未交费清单信息实体
	 * @param sess 持久化操作所需要的Hiberate Session
	 * @param id 需要加载的未交费清单信息的主键值
	 * @return 加载的未交费清单信息PO
	 */
	FeeExportRecordInfo get(Session sess,int id);
	
	/**
	 * 保存未交费清单信息实体，新增一条未交费清单信息记录
	 * @param frInfo 保存的未交费清单信息实例
	 */
	void save(Session sess,FeeExportRecordInfo frInfo);
	
	/**
	 * 根据条件分页获取未交费清单导出信息列表
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
	List<FeeExportRecordInfo> findPageInfoByOpt(Session sess,String addDateS,String addDateE,Integer cpyId,Integer pageNo,Integer pageSize);
	
	/**
	 * 根据条件获取未交费清单导出记录条数
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
