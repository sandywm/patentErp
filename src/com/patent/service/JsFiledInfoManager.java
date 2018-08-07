package com.patent.service;

import java.util.List;

import com.patent.exception.WEBException;
import com.patent.module.JsFiledInfoTb;

public interface JsFiledInfoManager {

	/**
	 * 增加专业技术区域信息
	 * @author Administrator
	 * @date 2018-8-7 下午10:42:52
	 * @ModifiedBy
	 * @param zyName 专业技术名称
	 * @param cpyId 代理机构编号
	 * @return
	 * @throws WEBException
	 */
	Integer addJF(String zyName,Integer cpyId)throws WEBException;
	
	/**
	 * 获取指定代理机构下的专业技术区域信息列表
	 * @author Administrator
	 * @date 2018-8-7 下午10:43:17
	 * @ModifiedBy
	 * @param cpyId 代理机构编号
	 * @return
	 * @throws WEBException
	 */
	List<JsFiledInfoTb> listInfoByCpyId(Integer cpyId)throws WEBException;
}
