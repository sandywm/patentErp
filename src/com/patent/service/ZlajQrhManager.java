package com.patent.service;

import java.util.List;

import com.patent.exception.WEBException;
import com.patent.module.ZlajQrh;

public interface ZlajQrhManager {

	/**
	 * 增加确认函信息
	 * @description
	 * @author Administrator
	 * @date 2019-3-26 上午10:18:03
	 * @param zipName 确认函压缩包名称
	 * @param zipPath 压缩包路径
	 * @param createUserId 创建人编号
	 * @param createUserName 创建人姓名
	 * @param createTime 创建时间
	 * @param cpyId 代理机构编号
	 * @return
	 * @throws WEBException
	 */
	Integer addQrh(String zipName,String zipPath,Integer createUserId,String createUserName,String createTime,Integer cpyId) throws WEBException;
	
	/**
	 * 根据主键获取确认函信息
	 * @description
	 * @author Administrator
	 * @date 2019-3-26 上午10:18:57
	 * @param qrhId 主键
	 * @return
	 * @throws WEBException
	 */
	ZlajQrh getEntityInfoById(Integer qrhId) throws WEBException;
	
	/**
	 * 获取代理机构下生成的确认函压缩包记录条数
	 * @description
	 * @author Administrator
	 * @date 2019-3-26 上午10:19:10
	 * @param cpyId
	 * @return
	 * @throws WEBException
	 */
	Integer getCount(Integer cpyId) throws WEBException;
	
	/**
	 * 分页获取指定代理机构下生成的确认函压缩包记录列表
	 * @description
	 * @author Administrator
	 * @date 2019-3-26 上午10:19:35
	 * @param cpyId 代理机构编号
	 * @return
	 * @throws WEBException
	 */
	List<ZlajQrh> listPageInfo(Integer cpyId,Integer pageNo,Integer pageSize) throws WEBException;
}
