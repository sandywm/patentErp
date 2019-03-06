package com.patent.service;

import java.util.List;


import com.patent.exception.WEBException;
import com.patent.module.ZlajLcMxInfoTb;

public interface ZlajLcMxInfoManager {

	/**
	 * 增加流程明细信息
	 * @description
	 * @author wm
	 * @date 2018-9-2 下午05:37:05
	 * @param lcId 流程编号
	 * @param fzUserId 流程负责人
	 * @param lcMxName 流程明细名称
	 * @param lcMxNo 流程数字
	 * @param lcMxSDate 开始时间
	 * @param lcMxEDate 完成时间
	 * @param lcMxUpFile 附件
	 * @param lcMxUpUserId 上传人
	 * @param lcMxUpDate 上传时间
	 * @param lcMxUpSize  附件大小
	 * @param lcMxFee 流程所需缴纳的费用
	 * @param lcMxRemark 备注
	 * @param lcPjScore 流程评分
	 * @param lastUpFileBz 上一次上传的补正附件(补正/补正审核时用)
	 * @param lastUpFileBzSc 上一次上传的审核附件(补正/补正审核时用)
	 * @param lastUpFileCus 上一次上传的客户补充文件(补正/补正审核时用)
	 * @param lastUpUserIdBz 上一次补正人员编号
	 * @param lastUpUserIdBzSc 上一次补正人员编号
	 * @param lastUpUserIdCus 上一次客户审核人员编号
	 * @param lastBzScRemark 上一次补正审核的备注
	 * @param lastCusRemark 上一次客户审核的备注
	 * @return
	 * @throws WEBException
	 */
	Integer addLcMx(Integer lcId,Integer fzUserId,String lcMxName, Double lcMxNo, String lcMxSDate, String lcMxEDate,
			String lcMxUpFile, Integer lcMxUpUserId, String lcMxUpDate,String lcMxUpSize,Double lcMxFee,String lcMxRemark,Integer lcPjScore,
			String lastUpFileBz,String lastUpFileBzSc,String lastUpFileCus,Integer lastUpUserIdBz,
			Integer lastUpUserIdBzSc,Integer lastUpUserIdCus,String lastBzScRemark,String lastCusRemark) throws WEBException;
	
	/**
	 * 修改流程负责人、完成时间、备注等（一般是领取）
	 * @description
	 * @author wm
	 * @date 2018-9-2 下午05:38:14
	 * @param id 主键
	 * @param fzUserId 流程负责人(-1时不修改)
	 * @param lcMxName 流程明细名称(""时不修改)
	 * @param lcMxUpFile 附件(""时不修改)
	 * @param lcMxUpUserId 上传人(-1时不修改)
	 * @param lcMxUpDate 上传时间(""时不修改)
	 * @param lcMxUpSize  附件大小(""时不修改)
	 * @param eDate 完成时间
	 * @param lcMxRemark 备注(""不修改)
	 * @param lcPjScore 流程评分（-1不修改）
	 * @return
	 * @throws WEBException
	 */
	boolean updateEdateById(Integer id,Integer fzUserId,String lcMxName,Integer lcMxUpUserId,String lcMxUpFile,
			String lcMxUpDate,String lcMxUpSize,String eDate, String lcMxRemark,Integer lcPjScore) throws WEBException;
	
	/**
	 * 根据流程编号获取所有流程明细
	 * @description
	 * @author wm
	 * @date 2018-9-2 下午05:38:54
	 * @param lcId 流程主键
	 * @return
	 * @throws WEBException
	 */
	List<ZlajLcMxInfoTb> listDetailInfoByLcId(Integer lcId) throws WEBException;
	
	/**
	 * 根据流程主键获取最后一个动作
	 * @description
	 * @author wm
	 * @date 2018-9-2 下午05:39:11
	 * @param lcId 流程主键
	 * @return
	 * @throws WEBException
	 */
	List<ZlajLcMxInfoTb> listLastInfoByLcId(Integer lcId) throws WEBException;
	
	/**
	 * 根据流程主键获取第一个动作（获取未领取的流程明细）
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2018-9-13 下午10:47:27
	 * @param lcId 流程主键
	 * @return
	 * @throws WEBException
	 */
	List<ZlajLcMxInfoTb> listFirstInfoByLcId(Integer lcId) throws WEBException;
	
	/**
	 * 根据流程明细名称、流程号、流程主键编号获取未完成的流程明细列表
	 * @description
	 * @author Administrator
	 * @date 2018-10-5 上午11:36:31
	 * @param lcMxName 流程明细名称
	 * @param lcMxNo 流程号
	 * @param lcId 流程主键编号
	 * @return
	 * @throws WEBException
	 */
	List<ZlajLcMxInfoTb> listUnComInfoByOpt(String lcMxName,Double lcMxNo,Integer lcId) throws WEBException;
	
	/**
	 * 根据流程明细主键编号获取流程明细列表
	 * @description
	 * @author Administrator
	 * @date 2018-10-6 上午10:01:50
	 * @param mxId 流程明细主键
	 * @return
	 */
	List<ZlajLcMxInfoTb> listDetailInfoById(Integer mxId) throws WEBException;
	
	/**
	 * 查看指定专利、指定流程明细有无记录（批量导入时，如果出现先后顺序混乱时使用）
	 * @description
	 * @author Administrator
	 * @date 2018-10-7 下午03:54:45
	 * @param zlId 专利编号
	 * @param lcmxName 流程明细名称
	 * @return
	 * @throws WEBException
	 */
	List<ZlajLcMxInfoTb> listSpecInfoInfoByOpt(Integer zlId,String lcmxName) throws WEBException;
	
	/**
	 * 修改流程明细名字（只有在之前导入缴费通知书的时候不知道申请日期，无法推算缴费截止日期时才会出现，等导入了受理通知书，将进行修改）
	 * @description
	 * @author Administrator
	 * @date 2018-10-11 上午09:46:46
	 * @param id
	 * @param lcMxName 流程明细名称
	 * @return 
	 * @throws WEBException
	 */
	boolean updateEdateById(Integer id,String lcMxName) throws WEBException;
	
	/**
	 * 修改指定流程明细的流程号
	 * @description
	 * @author Administrator
	 * @date 2018-10-11 下午04:17:56
	 * @param id
	 * @param mxNo 流程号
	 * @return
	 * @throws WEBException
	 */
	boolean updateMxNoById(Integer id,Double mxNo) throws WEBException;
	
	/**
	 * 根据条件分页获取任务记录列表(新申请撰稿开始)
	 * 如果是未完成的就还需要获取所有没有移交任务或者移交审核未通过的
	 * 如果是已完成的就不须有后续条件
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2018-11-15 下午09:14:12
	 * @param fzUserId 流程负责人编号（0表示全部）
	 * @param comStatus 完成状态（0：未完成，1：已完成）
	 * @param cpyId 代理机构编号（流程负责人大于0时不用传递）
	 * @param zlNo 专利号(""时表示全部)
	 * @param ajNo 案件号(""时表示全部)
	 * @param zlTitle 专利名称(""时表示全部)
	 * @param cusId 客户编号(""时表示全部)
	 * @return
	 * @throws WEBException
	 */
	List<ZlajLcMxInfoTb> listLcMxByOpt(Integer fzUserId,Integer comStatus, String zlNo,String ajNo,
			String zlTitle,Integer cusId,Integer cpyId, Integer pageNo, Integer pageSize) throws WEBException;
	
	/**
	 * 根据条件获取任务记录条数(新申请撰稿开始)
	 * 如果是未完成的就还需要获取所有没有移交任务或者移交审核未通过的
	 * 如果是已完成的就不须有后续条件
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2018-11-15 下午09:15:42
	 * @param fzUserId 流程负责人编号（0表示全部）
	 * @param comStatus 完成状态（0：未完成，1：已完成）
	 * @param cpyId 代理机构编号（流程负责人大于0时不用传递）
	 * @param zlNo 专利号(""时表示全部)
	 * @param ajNo 案件号(""时表示全部)
	 * @param zlTitle 专利名称(""时表示全部)
	 * @param cusId 客户编号(""时表示全部)
	 * @return
	 * @throws WEBException
	 */
	Integer getCountByOpt(Integer fzUserId,Integer comStatus, String zlNo,String ajNo,
			String zlTitle,Integer cusId,Integer cpyId) throws WEBException;
	
	/**
	 * 修改指定流程明细编号的移交审核状态
	 * @description
	 * @author Administrator
	 * @date 2018-11-24 上午11:07:11
	 * @param lcmxId 主键
	 * @param checkStatus 审核状态（0：未审核，1:审核通过，2：审核未通过）
	 * @return
	 * @throws WEBException
	 */
	boolean updateYjCheckStatus(Integer lcmxId,Integer checkStatus) throws WEBException;
	
	/**
	 * 修改指定流程明细的流程负责人(流程分配修改负责人时使用)
	 * @description
	 * @author Administrator
	 * @date 2019-1-14 上午09:43:15
	 * @param id
	 * @param fzUserId 负责人编号
	 * @param lcMxRemark 流程明细备注
	 * @return
	 * @throws WEBException
	 */
	boolean updateFzrInfoById(Integer id,Integer fzUserId,String lcMxRemark) throws WEBException;
	
	/**
	 * 获取当前指定负责人指定专利下未完成的流程任务
	 * @description
	 * @author Administrator
	 * @date 2019-1-14 下午01:12:18
	 * @param fzUserId 负责人编号
	 * @param zlId 专利编号
	 * @return
	 * @throws WEBException
	 */
	List<ZlajLcMxInfoTb> listUnComLcMxByOpt(Integer fzUserId,Integer zlId) throws WEBException;
}
