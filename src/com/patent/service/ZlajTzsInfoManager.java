package com.patent.service;

import java.util.List;

import com.patent.exception.WEBException;
import com.patent.module.ZlajTzsInfoTb;

public interface ZlajTzsInfoManager {

	/**
	 * 增加专利通知书信息
	 * @description
	 * @author wm
	 * @date 2018-9-3 上午10:25:26
	 * @param zlId 专利编号
	 * @param tzsName 通知书名称
	 * @param fwrDate 发文日
	 * @param gfrDate 官方绝限日
	 * @return
	 * @throws WEBException
	 */
	Integer addTzs(Integer zlId,String tzsName,String fwrDate,String gfrDate) throws WEBException;
	
	/**
	 * 根据案件编号获取所有的通知书信息列表
	 * @description
	 * @author wm
	 * @date 2018-9-3 上午10:25:57
	 * @param zlId 专利编号
	 * @return
	 * @throws WEBException
	 */
	List<ZlajTzsInfoTb> listInfoByZlId(Integer zlId) throws WEBException;
	
	/**
	 * 根据主键获取通知书信息详情
	 * @description
	 * @author wm
	 * @date 2018-9-3 上午10:43:26
	 * @param id
	 * @return
	 * @throws WEBException
	 */
	ZlajTzsInfoTb getEntityById(Integer id) throws WEBException;
}
