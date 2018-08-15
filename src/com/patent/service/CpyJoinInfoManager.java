package com.patent.service;

import java.util.List;

import com.patent.exception.WEBException;
import com.patent.module.CpyInfoTb;
import com.patent.module.CpyJoinInfoTb;

public interface CpyJoinInfoManager {

	/**
	 * 增加子/主公司合并申请记录
	 * @author Administrator
	 * @date 2018-8-15 下午11:11:03
	 * @ModifiedBy
	 * @param applyCpyId 发起申请的代理机构编号
	 * @param parCpyId 主机构编号
	 * @param subCpyId 子机构编号
	 * @param parCpyName 主机构名称
	 * @param subCpyName 子机构名称
	 * @param joinStatus 合并状态0(发起申请),1(同意),2(拒绝)
	 * @param applyContent 申请信息
	 * @param applyDate 申请时间
	 * @return
	 * @throws WEBException
	 */
	Integer addCJ(Integer applyCpyId, Integer parCpyId,
			Integer subCpyId, String parCpyName, String subCpyName,
			Integer joinStatus, String applyContent, String applyDate) throws WEBException;
	
	/**
	 * 修改指定合并申请记录的合并状态、操作时间、操作内容
	 * @author Administrator
	 * @date 2018-8-15 下午11:14:43
	 * @ModifiedBy
	 * @param id 主键
	 * @param joinStatus 合并记录
	 * @param czDate 操作时间
	 * @param czContent 操作内容
	 * @return
	 * @throws WEBException
	 */
	boolean updateCJById(Integer id,Integer joinStatus,String czDate, String czContent) throws WEBException;
	
	/**
	 * 删除指定主键的合并申请记录（在没处理处理之前可以删除）
	 * @author Administrator
	 * @date 2018-8-15 下午11:14:50
	 * @ModifiedBy
	 * @param id 主键
	 * @return
	 * @throws WEBException
	 */
	boolean delCJById(Integer id) throws WEBException;
	
	/**
	 * 根据条件获取申请合并记录列表
	 * @author Administrator
	 * @date 2018-8-15 下午11:14:57
	 * @ModifiedBy
	 * @return
	 * @throws WEBException
	 */
	List<CpyJoinInfoTb> listInfoByOpt(Integer parCpyId,Integer subCpyId, Integer joinStatus, 
			Integer applyCpyId,String czDate) throws WEBException;
}
