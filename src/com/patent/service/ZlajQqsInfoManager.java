package com.patent.service;

import java.util.List;

import com.patent.exception.WEBException;
import com.patent.module.ZlajQqsInfoTb;

public interface ZlajQqsInfoManager {
 
	/**
	 * 根据提交类型获取请求书列表
	 * @description
	 * @author Administrator
	 * @date 2019-3-14 上午10:26:44
	 * @param typeEng
	 * @return
	 * @throws WEBException
	 */
	List<ZlajQqsInfoTb> listInfoByTypeEng(String typeEng) throws WEBException;
	
	/**
	 * 根据主键获取请求书详细信息
	 * @description
	 * @author Administrator
	 * @date 2019-3-14 下午03:39:22
	 * @param id
	 * @return
	 * @throws WEBException
	 */
	ZlajQqsInfoTb getSpecInfoById(Integer id) throws WEBException;
}
