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
	 * @param showStatus 可见状态-- 0可见（默认），1不可见
	 * @param modLevel 模块级别[0(铜牌),1(银牌),2(金牌),3(钻石)]
	 * @param actNameEng 模块动作统称
	 * @return
	 * @throws WEBException
	 */
	Integer addModule(String modName,String modPic,String resUrl,Integer orderNo, Integer showStatus,Integer modLevel,String actNameEng) throws WEBException;
	
	/**
	 * 获取指定模块级别及其以下的模块列表信息
	 * @author Administrator
	 * @date 2018-8-7 下午10:02:24
	 * @ModifiedBy
	 * @param modLevel 模块级别（-1为全部）
	 * @param showStatus 可见状态-- 0可见（默认），1不可见 (-1为全部)
	 * @return
	 * @throws WEBException
	 */
	List<ModuleInfoTb> listInfoByLevel(Integer modLevel,Integer showStatus) throws WEBException;
	
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
	
	/**
	 * 修改指定模块的具体信息
	 * @description
	 * @author wm
	 * @date 2018-8-21 上午11:45:51
	 * @param id
	 * @param modName 模块名称
	 * @param modPic 模块图标
	 * @param resUrl 模块Url动作
	 * @param orderNo 排序号
	 * @param showStatus 可见状态-- 0可见（默认），1不可见
	 * @param modLevel 模块级别[0(铜牌),1(银牌),2(金牌),3(钻石)]
	 * @param actNameEng 模块动作统称
	 * @return
	 * @throws WEBException
	 */
	boolean upModule(Integer id,String modName,String modPic,String resUrl,Integer orderNo,Integer showStatus,Integer modLevel,String actNameEng) throws WEBException;
	
	/**
	 * 删除指定模块
	 * @description
	 * @author wm
	 * @date 2018-8-21 下午03:35:11
	 * @param id
	 * @return
	 * @throws WEBException
	 */
	boolean delModuleById(Integer id) throws WEBException;
	
	/**
	 * 获取指定模块信息
	 * @description
	 * @author wm
	 * @date 2018-8-21 下午03:55:01
	 * @param id
	 * @return
	 * @throws WEBException
	 */
	ModuleInfoTb getEntityById(Integer id) throws WEBException;
	
	/**
	 * 获取当前最大的排序号的模块信息
	 * @description
	 * @author wm
	 * @date 2018-8-25 上午09:10:26
	 * @return
	 * @throws WEBException
	 */
	List<ModuleInfoTb> listMaxOrderInfo() throws WEBException;
}
