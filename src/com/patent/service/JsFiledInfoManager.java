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
	 * 根据条件获取专业技术区域信息列表
	 * @author Administrator
	 * @date 2018-8-7 下午10:43:17
	 * @ModifiedBy
	 * @param cpyId 代理机构编号
	 * @param jsIdStr 员工擅长专业编号组合（""时表示全部）
	 * @return
	 * @throws WEBException
	 */
	List<JsFiledInfoTb> listInfoByOpt(Integer cpyId,String jsIdStr)throws WEBException;
	
	/**
	 * 修改指定主键编号的专业名称
	 * @description
	 * @author wm
	 * @date 2018-8-8 上午09:11:55
	 * @param jfId 主键
	 * @param zyName 专业名称
	 * @return
	 * @throws WEBException
	 */
	boolean updateJfById(Integer jfId,String zyName)throws WEBException;
	
	/**
	 * 删除指定专业编号的专业信息（没有员工擅长此专业时）
	 * @description
	 * @author wm
	 * @date 2018-8-8 上午09:12:33
	 * @param jfId 专业编号
	 * @return
	 * @throws WEBException
	 */
	boolean delJfById(Integer jfId)throws WEBException;
	
	/**
	 * 分页获取指定代理机构下的专业列表
	 * @description
	 * @author wm
	 * @date 2018-8-8 上午09:28:42
	 * @param cpyId 代理机构编号
	 * @return
	 * @throws WEBException
	 */
	List<JsFiledInfoTb> listPageInfoByCpyId(Integer cpyId,Integer pageNo,Integer pageSize)throws WEBException;
	
	/**
	 * 获取指定代理机构下的专业记录条数
	 * @description
	 * @author wm
	 * @date 2018-8-8 上午09:28:46
	 * @param cpyId 代理机构编号
	 * @return
	 * @throws WEBException
	 */
	Integer getCountByCpyId(Integer cpyId)throws WEBException;
	
	/**
	 * 获取指定代理机构下指定专业名字的信息列表
	 * @description
	 * @author wm
	 * @date 2018-8-8 上午10:17:14
	 * @param cpyId 代理机构编号
	 * @param zyName 专业名字
	 * @return
	 * @throws WEBException
	 */
	List<JsFiledInfoTb> listInfoByOpt_1(Integer cpyId,String zyName)throws WEBException;
}
