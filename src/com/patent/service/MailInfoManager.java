package com.patent.service;

import java.util.List;

import com.patent.exception.WEBException;
import com.patent.module.MailInfoTb;

public interface MailInfoManager {

	/**
	 * 增加发送邮件
	 * @description
	 * @author wm
	 * @date 2018-8-7 上午09:24:22
	 * @param mailType 邮件类型
	 * @param sendInfo 发送人邮箱
	 * @param acceptUserId 接收人编号
	 * @param userType 接收人类型
	 * @param mailTitle 邮件标题
	 * @param mailContent 邮件内容
	 * @return
	 * @throws WEBException
	 */
	Integer addMail(String mailType,String sendInfo,Integer acceptUserId,String userType,String mailTitle,String mailContent) throws WEBException;
	
	/**
	 * 修改指定邮件的已读状态
	 * @description
	 * @author wm
	 * @date 2018-8-7 上午09:25:06
	 * @param mailId 邮件编号
	 * @param readStatus 已读状态
	 * @return
	 * @throws WEBException
	 */
	boolean updateReadStatusById(Integer mailId,Integer readStatus) throws WEBException;
	
	/**
	 * 批量修改指定邮件的已读状态
	 * @description
	 * @author wm
	 * @date 2018-8-29 下午05:24:34
	 * @param mailIdStr
	 * @param readStatus
	 * @return
	 * @throws WEBException
	 */
	void updateBatchStatusByIdStr(String mailIdStr,Integer readStatus) throws WEBException;
	
	/**
	 * 删除指定邮件
	 * @description
	 * @author wm
	 * @date 2018-8-7 上午09:25:28
	 * @param mailId 邮件编号
	 * @return
	 * @throws WEBException
	 */
	boolean delMailById(Integer mailId) throws WEBException;
	
	/**
	 * 批量删除指定邮件
	 * @description
	 * @author wm
	 * @date 2018-8-7 上午09:25:33
	 * @param acceptUserId 接收人编号
	 * @param mailIdStr 批量邮件编号组合
	 * @throws WEBException
	 */
	void delBatchSelfMail(Integer acceptUserId,String mailIdStr) throws WEBException;
	
	/**
	 * 根据条件分页获取邮件列表
	 * @description
	 * @author wm
	 * @date 2018-8-7 上午09:25:36
	 * @param acceptUserId 接收人编号
	 * @param userType 接收人类型
	 * @param mailType 邮件类型
	 * @param mailTitle 邮件标题
	 * @param readStatus 已读状态
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws WEBException
	 */
	List<MailInfoTb> listPageInfoByOpt(Integer acceptUserId,String userType,String mailType,
			String mailTitle,Integer readStatus,Integer pageNo,Integer pageSize) throws WEBException;
	
	/**
	 * 根据条件获取邮件记录条数
	 * @description
	 * @author wm
	 * @date 2018-8-7 上午09:25:40
	 *@param acceptUserId 接收人编号
	 * @param userType 接收人类型
	 * @param mailType 邮件类型
	 * @param mailTitle 邮件标题
	 * @param readStatus 已读状态
	 * @return
	 * @throws WEBException
	 */
	Integer getCountByOpt(Integer acceptUserId,String userType,String mailType,
			String mailTitle,Integer readStatus) throws WEBException;
	
	/**
	 * 获取自己的邮件详情
	 * @description
	 * @author wm
	 * @date 2018-8-17 上午09:50:53
	 * @param acceptUserId 接收人编号
	 * @param userType 接收人类型
	 * @param mailId 主键
	 * @return
	 * @throws WEBException
	 */
	List<MailInfoTb> listInfoByOpt(Integer acceptUserId,String userType,Integer mailId) throws WEBException;
}
