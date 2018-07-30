package com.patent.dao;

import java.util.List;

import org.hibernate.Session;

import com.patent.module.SendEmailCodeInfo;

public interface SendEmailCodeInfoDao {

	/**
	 * 根据主键加载发送验证码信息实体
	 * @param sess 持久化操作所需要的Hiberate Session
	 * @param id 需要加载的发送验证码信息的主键值
	 * @return 加载的发送验证码信息PO
	 */
	SendEmailCodeInfo get(Session sess,int id);
	
	/**
	 * 保存发送验证码信息实体，新增一条发送验证码信息记录
	 * @param eCInfo 保存的发送验证码信息实例
	 */
	void save(Session sess,SendEmailCodeInfo eCInfo);
	
	/**
	 * 删除发送验证码信息实体，删除一条发送验证码信息记录
	 * @param eCInfo 删除的发送验证码信息实体
	 */
	void delete(Session sess,SendEmailCodeInfo eCInfo);
	
	/**
	 * 根据主键删除发送验证码信息实体，删除一条发送验证码信息记录
	 * @param id 需要删除发送验证码信息的主键
	 */
	void delete(Session sess,int id);
	
	/**
	 * 更新一条发送验证码信息记录
	 * @param eCInfo 需要更新的发送验证码信息
	 */
	void update(Session sess,SendEmailCodeInfo eCInfo);
	
	/**
	 * 根据条件获取邮箱验证码信息列表
	 * @description
	 * @author wm
	 * @date 2018-7-30 上午10:26:55
	 * @param sess
	 * @param email 邮箱地址
	 * @param code 验证码（可为空）
	 * @return
	 */
	List<SendEmailCodeInfo> findSpecInfoByOpt(Session sess,String email,String code);
}
