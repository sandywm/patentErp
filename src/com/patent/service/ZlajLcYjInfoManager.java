package com.patent.service;

import java.util.List;

import com.patent.exception.WEBException;
import com.patent.module.ZlajLcYjInfoTb;

public interface ZlajLcYjInfoManager {

	/**
	 * 增加流程移交申请记录
	 * @description
	 * @author Administrator
	 * @date 2018-11-15 上午10:07:22
	 * @param lcmxId 流程明细编号
	 * @param applyUserId 申请人
	 * @param applyCause 申请原因
	 * @param checkUserId 审核人员
	 * @param cpyId 代理机构编号
	 * @return
	 * @throws WEBException
	 */
	Integer addYj(Integer lcmxId,Integer applyUserId,String applyCause,Integer checkUserId,Integer cpyId)throws WEBException;
	
	/**
	 * 审核指定申请的信息
	 * @description
	 * @author Administrator
	 * @date 2018-11-15 上午10:07:53
	 * @param yjId 移交任务主键
	 * @param checkStatus 审核状态
	 * @param checkUserId 审核人员
	 * @return
	 * @throws WEBException
	 */
	boolean updateYjInfoById(Integer yjId,Integer checkStatus,Integer checkUserId)throws WEBException;
	
	/**
	 * 根据条件分页获取申请记录列表
	 * @description
	 * @author Administrator
	 * @date 2018-11-15 上午10:07:57
	 * @param applyUserId 申请人编号（0时表示全部）
	 * @param checkStauts 审核状态
	 * @param checkUserId 审核人员（0时表示全部）
	 * @param cpyId 代理机构编号
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws WEBException
	 */
	List<ZlajLcYjInfoTb> listPageInfoByOpt(Integer applyUserId,Integer checkStauts,Integer checkUserId,Integer cpyId,Integer pageNo,Integer pageSize)throws WEBException;
	
	/**
	 * 根据条件获取申请记录条数
	 * @description
	 * @author Administrator
	 * @date 2018-11-15 上午10:08:00
	 * @param applyUserId 申请人编号（0时表示全部）
	 * @param checkStauts 审核状态
	 * @param checkUserId 审核人员（0时表示全部）
	 * @param cpyId 代理机构编号
	 * @return
	 * @throws WEBException
	 */
	Integer getCountByOpt(Integer applyUserId,Integer checkStauts,Integer checkUserId,Integer cpyId)throws WEBException;

	/**
	 * 根据移交流程主键获取移交流程申请信息
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2018-11-15 下午10:49:51
	 * @param yjId 主键
	 * @return
	 * @throws WEBException
	 */
	ZlajLcYjInfoTb getEntityById(Integer yjId)throws WEBException;
}
