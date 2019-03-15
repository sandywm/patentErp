package com.patent.dao;

import java.util.List;

import org.hibernate.Session;

import com.patent.module.ZlajQqsInfoTb;

public interface ZlajQqsInfoDao {
	/**
	 * 根据主键加载请求书信息实体
	 * @param sess 持久化操作所需要的Hiberate Session
	 * @param id 需要加载的请求书信息的主键值
	 * @return 加载的请求书信息PO
	 */
	ZlajQqsInfoTb get(Session sess,int id);
	
	/**
	 * 根据请求类型获取请求书列表
	 * @description
	 * @author Administrator
	 * @date 2019-3-14 上午09:59:11
	 * @param sess
	 * @param typeEng 请求类型
	 * @return
	 */
	List<ZlajQqsInfoTb> findInfoByTypeEng(Session sess,String typeEng);
}
