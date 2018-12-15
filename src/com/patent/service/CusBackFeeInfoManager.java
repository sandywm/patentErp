/**
 * 
 */
package com.patent.service;

import java.util.List;

import com.patent.exception.WEBException;
import com.patent.module.CusBackFeeInfo;

public interface CusBackFeeInfoManager {

	/**
	 * 增加客户汇款记录
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2018-12-15 下午10:06:23
	 * @param backFeePrice 汇款金额
	 * @param backDate 汇款时间
	 * @param backType 汇款类型
	 * @param cusId 客户编号
	 * @param cpyId 代理机构编号
	 * @param operateUserId 操作人员
	 * @param operateTime 操作时间
	 * @param remark 备注
	 * @return
	 * @throws WEBException
	 */
	Integer addCBF(String backFeePrice,String backDate,String backType,Integer cusId,
			Integer cpyId,Integer operateUserId,String operateTime,String remark) throws WEBException;
	
	/**
	 * 根据条件分页获取客户汇款信息列表（汇款时间降序排列）
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2018-12-15 下午10:07:17
	 * @param cpyId 代理机构编号
	 * @param cusId 客户编号(0表示全部)
	 * @param sDate 汇款开始时间(""表示全部)
	 * @param eDate 汇款结束时间(""表示全部)
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws WEBException
	 */
	List<CusBackFeeInfo> listPageInfoByOpt(Integer cpyId,Integer cusId,String sDate,
			String eDate,Integer pageNo,Integer pageSize) throws WEBException;
	
	/**
	 * 根据条件获取客户汇款记录条数
	 * @author  Administrator
	 * @ModifiedBy  
	 * @date  2018-12-15 下午10:08:33
	 * @param cpyId 代理机构编号
	 * @param cusId 客户编号(0表示全部)
	 * @param sDate 汇款开始时间(""表示全部)
	 * @param eDate 汇款结束时间(""表示全部)
	 * @return
	 * @throws WEBException
	 */
	Integer getCountByOpt(Integer cpyId,Integer cusId,String sDate,String eDate) throws WEBException;
}
