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
	
	/**
	 * 列出指定模块下指定模块动作中文/英文的模块动作列表
	 * @description
	 * @author wm
	 * @date 2018-8-9 上午08:54:58
	 * @param modId 模块编号
	 * @param actNameChi 模块动作中文
	 * @param actNameEng 模块动作英文
	 * @return
	 * @throws WEBException
	 */
	List<ModActInfoTb> listSpecInfoByOpt(Integer modId,String actNameChi, String actNameEng) throws WEBException;
	
	/**
	 * 删除指定主键的模块动作
	 * @description
	 * @author wm
	 * @date 2018-8-9 上午10:19:03
	 * @param id 主键
	 * @return
	 * @throws WEBException
	 */
	boolean delMActById(Integer id) throws WEBException;
	
	/**
	 * 修改指定模块的模块动作
	 * @description
	 * @author wm
	 * @date 2018-8-21 上午11:41:34
	 * @param id
	 * @param actNameChi ""时不修改
	 * @param actNameEng ""时不修改
	 * @param orderNo -1时不修改
	 * @return
	 * @throws WEBException
	 */
	boolean upMActById(Integer id,String actNameChi,String actNameEng,Integer orderNo) throws WEBException;
	
	/**
	 * 根据主键获取模块动作信息
	 * @description
	 * @author wm
	 * @date 2018-8-21 下午03:43:39
	 * @param id
	 * @return
	 * @throws WEBException
	 */
	List<ModActInfoTb> listSpecInfoById(Integer id) throws WEBException;
}
