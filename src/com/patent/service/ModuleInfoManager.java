package com.patent.service;

import java.util.List;

import com.patent.exception.WEBException;
import com.patent.module.ModuleInfoTb;

public interface ModuleInfoManager {
	
	/**
	 * 增加模块
	 * @author Administrator
	 * @date 2018-8-7 下午09:59:34
	 * @ModifiedBy
	 * @param modName 模块名称
	 * @param modPic 模块图标
	 * @param resUrl 模块Url动作
	 * @param orderNo 排序号
	 * @param modLevel 模块级别[0(铜牌),1(银牌),2(金牌),3(钻石)]
	 * @return
	 * @throws WEBException
	 */
	Integer addModule(String modName,String modPic,String resUrl,Integer orderNo,Integer modLevel) throws WEBException;
	
	/**
	 * 获取指定模块级别及其以下的模块列表信息
	 * @author Administrator
	 * @date 2018-8-7 下午10:02:24
	 * @ModifiedBy
	 * @param modLevel 模块级别（-1为全部）
	 * @return
	 * @throws WEBException
	 */
	List<ModuleInfoTb> listInfoByLevel(Integer modLevel) throws WEBException;
	
	/**
	 * 根据模块名字获取模块列表信息
	 * @author Administrator
	 * @date 2018-8-8 下午10:39:37
	 * @ModifiedBy
	 * @param modName 模块名字
	 * @return
	 * @throws WEBException
	 */
	List<ModuleInfoTb> listInfoByName(String modName) throws WEBException;
}
