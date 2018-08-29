package com.patent.service;

import java.util.List;

import com.patent.exception.WEBException;
import com.patent.module.SendEmailCodeInfo;

public interface SendEmailCodeInfoManager {

	/**
	 * 增加发送验证码
	 * @description
	 * @author wm
	 * @date 2018-7-30 上午11:02:46
	 * @param userEmail 用户邮箱地址
	 * @param code 验证码
	 * @param sendTime 发送时间
	 * @return
	 * @throws WEBException
	 */
	Integer addSEC(String userEmail,String code,String sendTime) throws WEBException;
	
	/**
	 * 根据主键修改验证码、发送时间、使用状态
	 * @description
	 * @author wm
	 * @date 2018-7-30 上午11:03:26
	 * @param id
	 * @param code 验证码（""时不修改）
	 * @param sendTime 发送时间（null时不修改）
	 * @param useStatus 使用状态 （-1时不修改）
	 * @return
	 * @throws WEBException
	 */
	boolean updateInfoById(Integer id,String code,String sendTime,Integer useStatus) throws WEBException;
	
	/**
	 * 根据条件获取发送邮箱验证码信息列表
	 * @description
	 * @author wm
	 * @date 2018-7-30 上午11:04:53
	 * @param userEmail
	 * @param code
	 * @return
	 * @throws WEBException
	 */
	List<SendEmailCodeInfo> listSpecInfoByOpt(String userEmail,String code) throws WEBException;
}
