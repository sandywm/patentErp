package com.patent.service;

import java.util.List;

import com.patent.exception.WEBException;
import com.patent.module.FeeImportDealRecordInfo;
import com.patent.module.FeeImportRecordInfo;

public interface FeeImportRecordInfoManager {

	/**
	 * 增加已缴费用导入信息
	 * @description
	 * @author Administrator
	 * @date 2018-12-19 下午03:30:47
	 * @param uploadUserId 上传人
	 * @param cpyId 代理机构编号
	 * @param excelName 费用清单名称
	 * @param uploadTime 上传时间
	 * @param excelPath 文件路径
	 * @return
	 * @throws WEBException
	 */
	Integer addFIR(Integer uploadUserId, Integer cpyId,String excelName, String uploadTime, String excelPath)throws WEBException;
	
	/**
	 * 增加已缴费用导入处理详情
	 * @description
	 * @author Administrator
	 * @date 2018-12-19 下午03:31:31
	 * @param firId 费用导入编号
	 * @param zlNo 专利号
	 * @param feeName 费用名称
	 * @param yearNo 年度(滞纳金出现，其他费用时为0)
	 * @param dealTime 处理时间
	 * @param dealStatus 处理状态
	 * @param dealResult 处理结果
	 * @return
	 * @throws WEBException
	 */
	Integer addFIDR(Integer firId,String zlNo, String feeName, Integer yearNo,String dealTime, String dealStatus,String dealResult)throws WEBException;
	
	/**
	 * 获取指定时间段的费用导入信息记录条数
	 * @description
	 * @author Administrator
	 * @date 2018-12-19 下午03:32:20
	 * @param addDateS 开始时间
	 * @param addDateE 结束时间
	 * @param cpyId 代理机构编号
	 * @return
	 * @throws WEBException
	 */
	Integer getCountByOpt(String addDateS, String addDateE,Integer cpyId)throws WEBException;
	
	/**
	 * 分页获取指定时间段的费用导入信息列表
	 * @description
	 * @author Administrator
	 * @date 2018-12-19 下午03:33:15
	 * @param addDateS 开始时间
	 * @param addDateE 结束时间
	 * @param cpyId 代理机构编号
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws WEBException
	 */
	List<FeeImportRecordInfo> listPageInfoByOpt(String addDateS,String addDateE,Integer cpyId,Integer pageNo,Integer pageSize)throws WEBException;
	
	/**
	 * 根据费用导入编号获取已缴费导入处理详情列表
	 * @description
	 * @author Administrator
	 * @date 2018-12-19 下午03:33:56
	 * @param firId 费用导入编号
	 * @return
	 * @throws WEBException
	 */
	List<FeeImportDealRecordInfo> listInfoByFirId(Integer firId)throws WEBException;
}
