package com.patent.dao;

import java.util.List;

import org.hibernate.Session;

import com.patent.module.MailInfoTb;

public interface MailInfoDao {
	/**
	 * 根据主键加载邮件实体
	 * @param sess 持久化操作所需要的Hiberate Session
	 * @param id 需要加载的邮件的主键值
	 * @return 加载的邮件PO
	 */
	MailInfoTb get(Session sess,int id);
	
	/**
	 * 保存邮件实体，新增一条邮件记录
	 * @param mail 保存的邮件实例
	 */
	void save(Session sess,MailInfoTb mail);
	
	/**
	 * 删除邮件实体，删除一条邮件记录
	 * @param mail 删除的邮件实体
	 */
	void delete(Session sess,MailInfoTb mail);
	
	/**
	 * 根据主键删除邮件实体，删除一条邮件记录
	 * @param id 需要删除邮件的主键
	 */
	void delete(Session sess,int id);
	
	/**
	 * 更新一条邮件记录
	 * @param mail 需要更新的邮件
	 */
	void update(Session sess,MailInfoTb mail);
	
	/**
	 * 根据条件分页获取邮件列表
	 * @description
	 * @author wm
	 * @date 2018-8-7 上午09:08:30
	 * @param sess
	 * @param acceptUserId 接收人编号
	 * @param userType 接收人类型（cpyUser,appUser,spUser）
	 * @param mailType 邮件类型（""表示全部）
	 * @param mailTitle 邮件标题(""表示全部)
	 * @param readStatus 已读标记（-1表示全部）
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<MailInfoTb> findPageInfoByOpt(Session sess,Integer acceptUserId,String userType,String mailType,
			String mailTitle,Integer readStatus,Integer pageNo,Integer pageSize);
	
	/**
	 * 根据条件获取邮件记录条数
	 * @description
	 * @author wm
	 * @date 2018-8-7 上午09:11:35
	 * @param sess
	 * @param acceptUserId 接收人编号
	 * @param userType 接收人类型（cpyUser,appUser,spUser）
	 * @param mailType 邮件类型（""表示全部）
	 * @param mailTitle 邮件标题(""表示全部)
	 * @param readStatus 已读标记（-1表示全部）
	 * @return
	 */
	Integer getCountByOpt(Session sess,Integer acceptUserId,String userType,String mailType,
			String mailTitle,Integer readStatus);
	
	/**
	 * 获取自己的邮件详情
	 * @description
	 * @author wm
	 * @date 2018-8-17 上午09:52:15
	 * @param sess
	 * @param acceptUserId 接收人编号
	 * @param mailId 主键
	 * @return
	 */
	List<MailInfoTb> findInfoByOpt(Session sess,Integer acceptUserId,Integer mailId);
}
