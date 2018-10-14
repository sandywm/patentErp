package com.patent.service;

import java.util.List;

import org.hibernate.Session;

import com.patent.exception.WEBException;
import com.patent.module.FeeTypeInfoTb;
import com.patent.module.ZlajFeeInfoTb;

public interface ZlajFeeInfoManager{

	/**
	 * 根据条件获取缴费信息列表
	 * @description
	 * @author wm
	 * @date 2018-9-4 下午04:59:06
	 * @param zlId 专利编号
	 * @param feeTypeStatus 费用类型[gf(官费),dlf(代理费),nf(年费),jlj(奖励金)]""表示全部
	 * @return
	 * @throws WEBException
	 */
	List<ZlajFeeInfoTb> listInfoByOpt(Integer zlId,String feeTypeStatus) throws WEBException;
	
	/**
	 * 根据主键编号获取实体信息
	 * @description
	 * @author wm
	 * @date 2018-9-4 下午05:00:42
	 * @param id 主键编号
	 * @return
	 * @throws WEBException
	 */
	ZlajFeeInfoTb getFeeEntityById(Integer id) throws WEBException;
	
	/**
	 * 根据费用标记获取费用类型信息列表
	 * @description
	 * @author wm
	 * @date 2018-9-4 下午05:01:01
	 * @param feeStatus 费用标记（""表示全部）
	 * @return
	 * @throws WEBException
	 */
	List<FeeTypeInfoTb> listInfoByStatus(String feeStatus) throws WEBException;
	
	/**
	 * 根据专利编号、费用类型获取专利案件缴费列表
	 * @description
	 * @author Administrator
	 * @date 2018-10-10 上午11:07:01
	 * @param zlId 专利编号
	 * @param feeTypeId 费用类型
	 * @return
	 * @throws WEBException
	 */
	List<ZlajFeeInfoTb> listInfoByOpt(Integer zlId,Integer feeTypeId) throws WEBException;
	
	/**
	 * 根据费用名称获取费用类型信息列表
	 * @description
	 * @author Administrator
	 * @date 2018-10-14 上午09:29:36
	 * @param sess
	 * @param feeName 费用名称
	 * @return
	 */
	List<FeeTypeInfoTb> listInfoByName(String feeName) throws WEBException ;
	
	/**
	 * 初始增加专利缴费信息
	 * @description
	 * @author Administrator
	 * @date 2018-10-10 上午11:21:53
	 * @param zlId 专利编号
	 * @param appUserId 申请缴纳费用人员编号
	 * @param geeTypeId 费用类型
	 * @param feePrice 费用金额
	 * @param feeRate 费减
	 * @param feeEndDateCpy 费用期限(机构)
	 * @param feeEndDateGf 官方期限
	 * @param feeRemark 备注
	 * @param feeStatus 费用缴纳状态（默认0未交，1：已交）
	 * @param cpyId 所属公司
	 * @param djStatus 是否代缴（默认0(自缴)，1(代缴)）
	 * @param feeJnDate 缴费时间
	 * @param feeUpZd 缴费账单
	 * @param tzsArea 缴费属于哪个通知书的收费明细
	 * @return
	 * @throws WEBException
	 */
	Integer addZLFee(Integer zlId,Integer appUserId,Integer geeTypeId,Double feePrice,Double feeRate,String feeEndDateCpy,String feeEndDateGf,String feeRemark,Integer feeStatus,
			Integer cpyId,Integer djStatus,String feeJnDate,String feeUpZd,String tzsArea) throws WEBException;
	
	/**
	 * 修改缴费信息
	 * @description
	 * @author Administrator
	 * @date 2018-10-10 上午11:24:55
	 * @param id
	 * @param feePrice 费用金额
	 * @param feeRemark 备注
	 * @param feeStatus 费用缴纳状态（默认0未交，1：已交）
	 * @param djStatus 是否代缴（默认0(自缴)，1(代缴)）
	 * @param feeJnDate 缴费时间
	 * @param feeUpZd 缴费账单
	 * @return
	 * @throws WEBException
	 */
	boolean updateFeeInfoById(Integer id,Double feePrice,String feeRemark,Integer feeStatus,Integer djStatus,String feeJnDate,String feeUpZd) throws WEBException;
	
	/**
	 * 根据主键修改费用期限
	 * @description
	 * @author Administrator
	 * @date 2018-10-11 上午09:54:19
	 * @param id
	 * @param feeEndDateCpy 代理期限
	 * @param feeEndDateGf 官方期限
	 * @return
	 * @throws WEBException
	 */
	boolean updateFeeInfoById(Integer id,String feeEndDateCpy,String feeEndDateGf) throws WEBException;
}
