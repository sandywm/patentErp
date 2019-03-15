package com.patent.service;

import java.util.List;


import com.patent.exception.WEBException;
import com.patent.module.ZlajZdSubmitTb;

public interface ZlajZdSubmitManager {

	/**
	 * 增加主动提交
	 * @description
	 * @author Administrator
	 * @date 2019-3-14 上午10:24:43
	 * @param qqsId 请求书编号
	 * @param userId 提交人编号
	 * @param zlId 专利编号
	 * @param qqsFjPath 上传附件
	 * @param uploadDate 上传日期
	 * @param cpyId 代理机构
	 * @return
	 * @throws WEBException
	 */
	Integer addZZS(Integer qqsId, Integer userId,Integer zlId, String qqsFjPath, String uploadDate,Integer cpyId) throws WEBException;
	
	/**
	 * 获取指定条件下的主动提交记录列表
	 * @description
	 * @author Administrator
	 * @date 2019-3-14 上午10:25:24
	 * @param cpyId 代理机构编号
	 * @param zlTitle 专利名称
	 * @param zlNo 专利号
	 * @param signStatus 签收状态(0:全部,1:未签收，2：已签收)
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws WEBException
	 */
	List<ZlajZdSubmitTb> listPageInfoByOpt(Integer cpyId,String zlTitle,String zlNo,Integer signStatus,Integer pageNo,Integer pageSize) throws WEBException;
	
	/**
	 * 获取指定条件下的主动提交记录条数
	 * @description
	 * @author Administrator
	 * @date 2019-3-14 上午10:25:48
	 * @param cpyId 代理机构编号
	 * @param zlTitle 专利名称
	 * @param zlNo 专利号
	 * @param signStatus 签收状态(0:全部,1:未签收，2：已签收)
	 * @return
	 * @throws WEBException
	 */
	Integer getCountByOpt(Integer cpyId,String zlTitle,String zlNo,Integer signStatus) throws WEBException;
	
	/**
	 * 根据专利号、请求书名称、签收状态获取主动提交信息列表
	 * @description
	 * @author Administrator
	 * @date 2019-3-14 上午10:26:09
	 * @param cpyId 代理机构编号
	 * @param zlNo 专利编号
	 * @param qqsName 请求书名称
	 * @param signStatus 签收状态(0:全部,1:未签收，2：已签收)
	 * @return
	 */
	List<ZlajZdSubmitTb> listInfoByZlNo(Integer cpyId,String zlNo,String qqsName,Integer signStatus) throws WEBException ;
}
