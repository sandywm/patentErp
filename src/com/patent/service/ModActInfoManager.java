package com.patent.service;

import java.util.List;

import com.patent.exception.WEBException;
import com.patent.module.ModActInfoTb;

public interface ModActInfoManager {

	/**
	 * 增加模块动作信息
	 * @author Administrator
	 * @date 2018-8-7 下午10:13:06
	 * @ModifiedBy
	 * @param actNameChi 模块动作中文
	 * @param actNameEng 模块动作英文
	 * @param orderNo 排序号
	 * @param modId 模块编号
	 * @return
	 * @throws WEBException
	 */
	Integer addMAct(String actNameChi,String actNameEng,Integer orderNo,Integer modId) throws WEBException;
	
	/**
	 * 浏览指定模块的模块动作列表（升序）
	 * @author Administrator
	 * @date 2018-8-7 下午10:13:52
	 * @ModifiedBy
	 * @param modId 模块编号
	 * @return
	 * @throws WEBException
	 */
	List<ModActInfoTb> listInfoByModId(Integer modId) throws WEBException;
}
