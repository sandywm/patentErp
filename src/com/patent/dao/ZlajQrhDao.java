package com.patent.dao;

import java.util.List;

import org.hibernate.Session;

import com.patent.module.ZlajQrh;

public interface ZlajQrhDao {

	/**
	 * 根据主键获取确认函详细信息
	 * @description
	 * @author Administrator
	 * @date 2019-3-26 上午10:06:52
	 * @param sess
	 * @param id 主键
	 * @return
	 */
	ZlajQrh get(Session sess,int id);
	
	/**
	 * 增加确认函信息
	 * @description
	 * @author Administrator
	 * @date 2019-3-26 上午10:07:09
	 * @param sess
	 * @param qrh
	 */
	void save(Session sess,ZlajQrh qrh);
	
	/**
	 * 获取代理机构下所有的客户确认函生成包
	 * @description
	 * @author Administrator
	 * @date 2019-3-26 上午10:10:01
	 * @param sess
	 * @param cpyId 代理机构编号
	 * @return
	 */
	Integer getCount(Session sess,Integer cpyId);
	
	/**
	 * 分页获取指定代理机构下的客户确认函生成包
	 * @description
	 * @author Administrator
	 * @date 2019-3-26 上午10:10:32
	 * @param sess
	 * @param cpyId 代理机构编号
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<ZlajQrh> findPageInfo(Session sess,Integer cpyId,Integer pageNo,Integer pageSize);
}
