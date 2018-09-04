package com.patent.service;

import java.util.List;

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
}
