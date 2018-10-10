package com.patent.service;

import java.util.List;

import org.hibernate.Session;

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
	 * @return
	 * @throws WEBException
	 */
	Integer addLcMx(Integer lcId,Integer fzUserId,String lcMxName, Double lcMxNo, String lcMxSDate, String lcMxEDate,
			String lcMxUpFile, Integer lcMxUpUserId, String lcMxUpDate,String lcMxUpSize,Double lcMxFee,String lcMxRemark) throws WEBException;
	
	/**
	 * 修改流程负责人、完成时间、备注等（一般是领取）
	 * @description
	 * @author wm
	 * @date 2018-9-2 下午05:38:14
	 * @param id 主键
	 * @param fzUserId 流程负责人(-1时不修改)
	 * @param lcMxUpFile 附件(""时不修改)
	 * @param lcMxUpUserId 上传人(-1时不修改)
	 * @param lcMxUpDate 上传时间(""时不修改)
	 * @param lcMxUpSize  附件大小(""时不修改)
	 * @param eDate 完成时间
	 * @param lcMxRemark 备注(""不修改)
	 * @return
	 * @throws WEBException
	 */
	boolean updateEdateById(Integer id,Integer fzUserId,Integer lcMxUpUserId,String lcMxUpFile,
			String lcMxUpDate,String lcMxUpSize,String eDate, String lcMxRemark) throws WEBException;
	
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
}
