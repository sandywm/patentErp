package com.patent.service;

import java.util.List;

import com.patent.exception.WEBException;
import com.patent.module.FeeExportRecordInfo;

public interface FeeExportRecordInfoManager {

	/**
	 * 增加未交费导出信息
	 * @description
	 * @author Administrator
	 * @date 2018-12-10 下午03:56:51
	 * @param excelName 费用清单名称
	 * @param addTime 增加时间
	 * @param addUserId 生成人编号
	 * @param excelPath 费用路径
	 * @param cpyId 代理机构编号
	 * @return
	 * @throws WEBException
	 */
	Integer addFER(String excelName,String addTime,Integer addUserId,String excelPath,Integer cpyId)throws WEBException;
	
	/**
	 * 根据条件分页获取未交费清单导出信息列表
	 * @description
	 * @author Administrator
	 * @date 2018-12-10 下午03:57:42
	 * @param addDateS 开始时间
	 * @param addDateE 结束时间
	 * @param cpyId 代理机构编号
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws WEBException
	 */
	List<FeeExportRecordInfo> listPageInfoByOpt(String addDateS, String addDateE, Integer cpyId, Integer pageNo,Integer pageSize)throws WEBException;
	
	/**
	 * 根据条件获取未交费清单导出记录条数
	 * @description
	 * @author Administrator
	 * @date 2018-12-10 下午03:58:08
	 * @param addDateS 开始时间
	 * @param addDateE 结束时间
	 * @param cpyId 代理机构编号
	 * @return
	 * @throws WEBException
	 */
	Integer getCountByOpt(String addDateS, String addDateE, Integer cpyId)throws WEBException;
}
