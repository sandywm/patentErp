package com.patent.service;

import java.util.List;

import com.patent.exception.WEBException;
import com.patent.module.ZlajFjInfoTb;

public interface ZlajFjInfoManager {

	/**
	 * 增加附件信息
	 * @description
	 * @author wm
	 * @date 2018-9-3 下午04:09:56
	 * @param ajId 案件编号
	 * @param fjName 附件名称
	 * @param fjVersion 附件版本
	 * @param fjType 附件类型
	 * @param fjGs 附件格式
	 * @param fjDx 附件大小
	 * @param upUserId 上传人编号
	 * @param upDate 上传日期
	 * @return
	 * @throws WEBException
	 */
	Integer addFj(Integer ajId,String fjName,String fjVersion,String fjType,String fjGs,String fjDx,Integer upUserId,String upDate) throws WEBException;
	
	/**
	 * 根据案件编号获取附件信息列表（id降序排列）
	 * @description
	 * @author wm
	 * @date 2018-9-3 下午04:10:40
	 * @param ajId 案件编号
	 * @return
	 * @throws WEBException
	 */
	List<ZlajFjInfoTb> listInfoByAjId(Integer ajId) throws WEBException;
}
