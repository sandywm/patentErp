package com.patent.service;

import java.util.List;


import com.patent.exception.WEBException;
import com.patent.module.FeeTypeInfoTb;
import com.patent.module.ZlajFeeInfoTb;
import com.patent.module.ZlajFeeSubInfoTb;

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
	 * @description(退换费用、退换状态、优惠费用初始为0)
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
	 * @param yearFeeNo 第年度数字（不是年费的为0）
	 * @param feeRange 年费范围####-##-##:####-##-##（不是年费为""）
	 * @param addStatus 自动增加年费标记(0:已增加,1:未增加)-不是年费默认为0
	 * @param backDate 退换时间
	 * @param feeBatchNo 缴费批次号
	 * @param bankSerialNo 银行缴费流水号
	 * @param fpDate 开发票时间
	 * @param fpNo 票号
	 * @return
	 * @throws WEBException
	 */
	Integer addZLFee(Integer zlId,Integer appUserId,Integer geeTypeId,Double feePrice,Double feeRate,String feeEndDateCpy,String feeEndDateGf,String feeRemark,Integer feeStatus,
			Integer cpyId,Integer djStatus,String feeJnDate,String feeUpZd,String tzsArea,Integer yearFeeNo,String feeRange,
			Integer addStatus,String backDate,String feeBatchNo,String bankSerialNo,String fpDate,String fpNo) throws WEBException;
	
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
	
	/**
	 * 获取指定代理机构下指定专利的所有费用（按照官方期限升序排列）
	 * @description
	 * @author Administrator
	 * @date 2018-10-30 上午08:54:13
	 * @param zlId 专利编号
	 * @param cpyId 代理机构编号
	 * @return
	 * @throws WEBException
	 */
	List<ZlajFeeInfoTb> listAllFeeByZlId(Integer zlId,Integer cpyId) throws WEBException ;
	
	/**
	 * 增加费用信息子表
	 * @description
	 * @author Administrator
	 * @date 2018-11-3 上午09:10:25
	 * @param feeRange 费用范围
	 * @param latePrice 滞纳金
	 * @param feeId 费用编号
	 * @param feeTypeId 费用类型编号
	 * @param remark 备注
	 * @return
	 * @throws WEBException
	 */
	Integer addFeeSubInfo(String feeRange,Double latePrice,Integer feeId,Integer feeTypeId,String remark)throws WEBException;
	
	
	/**
	 * 根据费用编号获取费用子表列表
	 * @description
	 * @author Administrator
	 * @date 2018-11-3 上午09:11:13
	 * @param feeId 费用编号
	 * @return
	 * @throws WEBException
	 */
	List<ZlajFeeSubInfoTb> listInfoByFeeId(Integer feeId)throws WEBException;
	
	/**
	 * 根据主键修改专利代缴费用的退换信息
	 * @description
	 * @author Administrator
	 * @date 2018-11-8 上午10:35:37
	 * @param feeId 费用编号
	 * @param backDate 退换日期
	 * @param backFee 退换费用（大于0时修改）
	 * @param backStatus 退换状态（-1时不修改）
	 * @param discountsFee 优惠费用(大于0时才修改)
	 * @return
	 * @throws WEBException
	 */
	boolean updateBackFeeInfoById(Integer feeId,String backDate,Double backFee,Integer backStatus,Double discountsFee)throws WEBException;
	
	/**
	 * 根据条件获取缴费列表
	 * @description
	 * @author Administrator
	 * @date 2018-11-12 上午10:01:22
	 * @param zlId 专利编号
	 * @param feeTypeStatus 费用类型（""表示全部）
	 * @param djStatus 代缴状态（-1为全部）
	 * @param feeStatus 缴费状态（-1为全部）
	 * @param backStatus 退换状态（-1为全部）--代缴的时候才出现
	 * @param cpyId 代理机构编号
	 * @return
	 * @throws WEBException
	 */
	List<ZlajFeeInfoTb> listAllFeeByOpt(Integer zlId,String feeTypeStatus,Integer djStatus,Integer feeStatus,Integer backStatus,Integer cpyId)throws WEBException;
	
	/**
	 * 获取指定专利指定年度的费用信息
	 * @description
	 * @author Administrator
	 * @date 2018-11-19 下午04:08:49
	 * @param zlId 专利编号
	 * @param yearNo 年度
	 * @return
	 * @throws WEBException
	 */
	List<ZlajFeeInfoTb> listYearFeeByOpt(Integer zlId,Integer yearNo)throws WEBException;
	
	/**
	 * 根据主键修改费用的缴费批次号、银行缴费流水号、开票时间、票号
	 * @description
	 * @author Administrator
	 * @date 2018-12-5 上午10:34:46
	 * @param feeId
	 * @param feeBatchNo 费用的缴费批次号(""时不修改)
	 * @param bankSerialNo 银行缴费流水号(""时不修改)
	 * @param fpDate 开票时间(""时不修改)
	 * @param fpNo 票号(""时不修改)
	 * @return
	 * @throws WEBException
	 */
	boolean updateFeeInfoById(Integer feeId,String feeBatchNo,String bankSerialNo,String fpDate,String fpNo)throws WEBException;
	
	/**
	 * 根据条件分页/不分页获取费用列表（获取未缴费的费用列表时专利必须在正常状态下）
	 * @description 未交费状态下时按照代理机构期限升序排列
	 * @author Administrator
	 * @date 2018-12-5 上午10:44:14
	 * @param cpyId 代理机构编号
	 * @param feeStatus 费用缴纳状态--在未交费的状态下不分页
	 * @param diffDays 距离代理机构期限天数(-1表示全部)--只在未缴纳费用中使用
	 * @param zlNo 专利/申请号(""表示全部)
	 * @param ajNo 案件编号(""表示全部)
	 * @param cusId 客户/申请人编号(0表示全部)
	 * @param sDate 开始时间(缴费时间)--feeStatus=1的状态下使用
	 * @param eDate 结束时间(缴费时间)--feeStatus=1的状态下使用
	 * @param pageNo 页码
	 * @param pageSize 每页记录条数
	 * @return
	 * @throws WEBException
	 */
	List<ZlajFeeInfoTb> listInfoByOpt(Integer cpyId,Integer feeStatus,Integer diffDays,String zlNo,String ajNo,Integer cusId,String sDate,String eDate,Integer pageNo,Integer pageSize)throws WEBException;
	
	/**
	 * 获取指定代理机构下已缴费的费用记录条数（专利正常条件下）
	 * @description
	 * @author Administrator
	 * @date 2018-12-5 上午10:46:42
	 * @param cpyId 代理机构编号
	 * @param zlNo 专利/申请号(""表示全部)
	 * @param ajNo 案件编号(""表示全部)
	 * @param cusId 客户/申请人编号(0表示全部)
	 * @param sDate 开始时间(缴费时间)--feeStatus=1的状态下使用
	 * @param eDate 结束时间(缴费时间)--feeStatus=1的状态下使用
	 * @return
	 * @throws WEBException
	 */
	Integer getCountByOpt(Integer cpyId,String zlNo,String ajNo,Integer cusId,String sDate,String eDate)throws WEBException;
	
	/**
	 * 根据费用编号修改费用金额
	 * @description
	 * @author Administrator
	 * @date 2018-12-6 上午09:33:40
	 * @param feeId
	 * @param newFeePrice 费用金额
	 * @return
	 * @throws WEBException
	 */
	boolean updateFeePriceById(Integer feeId,Double newFeePrice)throws WEBException;
	
	/**
	 * 获取指定代理机构下已交费用、实收费用、未收费用统计（在已交费用模式下）
	 * @description
	 * @author Administrator
	 * @date 2018-12-11 上午11:05:14
	 * @param cpyId 代理机构编号
	 * @param zlNo 专利/申请号(""表示全部)
	 * @param ajNo 案件编号(""表示全部)
	 * @param cusId 客户/申请人编号(0表示全部)
	 * @param sDate 开始时间(缴费时间)
	 * @param eDate 结束时间(缴费时间)
	 * @return
	 * @throws WEBException
	 */
	List<Object> getTjFeeInfoByOpt(Integer cpyId,String zlNo, String ajNo, Integer cusId, String sDate, String eDate)throws WEBException;
	
}
