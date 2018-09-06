package com.patent.service;

import java.util.List;

import com.patent.exception.WEBException;
import com.patent.module.ZlajLcInfoTb;

public interface ZlajLcInfoManager {

	/**
	 * 增加流程信息
	 * @description
	 * @author wm
	 * @date 2018-8-31 上午10:45:48
	 * @param ajId 案卷编号
	 * @param lcName 流程名称
	 * @param lcDetail 流程内容
	 * @param sDate 流程开始时间
	 * @param cpyDate 流程最后期限-机构
	 * @param comDate 流程完成时间
	 * @param gfDate 流程官方绝限
	 * @return
	 * @throws WEBException
	 */
	Integer addLcInfo(Integer ajId,String lcName,String lcDetail,String sDate,String cpyDate,
			String comDate,String gfDate) throws WEBException;
	
	/**
	 * 修改指定编号的流程信息
	 * @description
	 * @author wm
	 * @date 2018-8-31 上午10:47:01
	 * @param id
	 * @param lcName 流程名称（""不修改）
	 * @param lcDetail 流程内容（""不修改）
	 * @param sDate 流程开始时间 （""不修改）
	 * @param cpyDate 流程最后期限-机构（""不修改）
	 * @param gfDate 流程官方绝限（""不修改）
	 * @return
	 * @throws WEBException
	 */
	boolean updateLcBasicInfoById(Integer id,String lcName,String lcDetail,String sDate,String cpyDate,
			String gfDate) throws WEBException;
	
	/**
	 * 修改流程完成时间
	 * @description
	 * @author wm
	 * @date 2018-8-31 下午04:35:28
	 * @param id
	 * @param comDate 完成时间
	 * @return
	 * @throws WEBException
	 */
	boolean updateComInfoById(Integer id,String comDate) throws WEBException;
	
	
	/**
	 * 根据主键获取流程信息
	 * @description
	 * @author wm
	 * @date 2018-8-31 上午10:48:45
	 * @param id
	 * @return
	 * @throws WEBException
	 */
	List<ZlajLcInfoTb> listLcInfoById(Integer id) throws WEBException;
	
	/**
	 * 根据案件编号获取该案件所有的流程列表（编号降序排列）
	 * @description
	 * @author wm
	 * @date 2018-8-31 上午10:49:21
	 * @param ajId 案件编号
	 * @return
	 * @throws WEBException
	 */
	List<ZlajLcInfoTb> listLcInfoByAjId(Integer ajId) throws WEBException;
}