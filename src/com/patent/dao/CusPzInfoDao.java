package com.patent.dao;

import java.util.List;

import org.hibernate.Session;

import com.patent.module.CusPzInfo;

public interface CusPzInfoDao {

	/**
	 * 根据条件获取客户汇款平账记录信息列表
	 * @description
	 * @author Administrator
	 * @date 2018-12-24 上午10:12:39
	 * @param sess
	 * @param backFeeId 汇款记录编号(0表示全部)
	 * @param cusId 客户编号(0表示全部)
	 * @return
	 */
	List<CusPzInfo> findInfoByOpt(Session sess,Integer backFeeId,Integer cusId);
	
	/**
	 * 保存客户汇款平账信息
	 * @description
	 * @author Administrator
	 * @date 2018-12-24 上午10:28:08
	 * @param sess
	 * @param pzInfo 保存的客户汇款平账信息
	 */
	void save(Session sess,CusPzInfo pzInfo);
}
