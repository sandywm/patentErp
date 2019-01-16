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
	 * @param lcName 流程任务
	 * @param applyCause 申请原因
	 * @param checkUserId 审核人员
	 * @param checkStauts 审核状态
	 * @param checkDate 审核时间
	 * @param cpyId 代理机构编号
	 * @param yjType 移交类型(0：临时移交[只移交当前任务]，1：永久移交[移交该流程负责人])
	 * @return
	 * @throws WEBException
	 */
	Integer addYj(Integer lcmxId,Integer applyUserId,String lcName,String applyCause,Integer checkStatus, String checkDate,
			Integer checkUserId,Integer cpyId,Integer yjType)throws WEBException;
	
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
	 * @param zlTitle 专利名称(""时表示全部)
	 * @param ajNo 案件编号(""时表示全部)
	 * @param zlNo 专利号(""时表示全部)
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws WEBException
	 */
	List<ZlajLcYjInfoTb> listPageInfoByOpt(Integer applyUserId,Integer checkStauts,Integer checkUserId,
			Integer cpyId,String zlTitle,String ajNo,String zlNo,Integer pageNo,Integer pageSize)throws WEBException;
	
	/**
	 * 根据条件获取申请记录条数
	 * @description
	 * @author Administrator
	 * @date 2018-11-15 上午10:08:00
	 * @param applyUserId 申请人编号（0时表示全部）
	 * @param checkStauts 审核状态
	 * @param checkUserId 审核人员（0时表示全部）
	 * @param cpyId 代理机构编号
	 * @param zlTitle 专利名称(""时表示全部)
	 * @param ajNo 案件编号(""时表示全部)
	 * @param zlNo 专利号(""时表示全部)
	 * @return
	 * @throws WEBException
	 */
	Integer getCountByOpt(Integer applyUserId,Integer checkStauts,Integer checkUserId,Integer cpyId,
			String zlTitle,String ajNo,String zlNo)throws WEBException;

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
	
	/**
	 * 获取指定专利指定流程的未审核的流程任务申请列表
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2018-11-17 下午09:02:38
	 * @param lcTask 流程任务
	 * @param zlId 专利编号
	 * @return
	 * @throws WEBException
	 */
	List<ZlajLcYjInfoTb> listUnCheckInfoByOpt(String lcTask,Integer zlId)throws WEBException;
	
	/**
	 * 获取指定专利、申请人、流程任务的流程移交实体（未审核）
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2018-11-27 下午08:40:06
	 * @param applyUserId 申请人
	 * @param lcTask 流程任务
	 * @param zlId 专利编号
	 * @return
	 * @throws WEBException
	 */
	ZlajLcYjInfoTb getEntityByOpt(Integer applyUserId,String lcTask,Integer zlId)throws WEBException;
}
