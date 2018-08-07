package com.patent.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.patent.dao.MailInfoDao;
import com.patent.exception.WEBException;
import com.patent.factory.DaoFactory;
import com.patent.module.MailInfoTb;
import com.patent.service.MailInfoManager;
import com.patent.tools.CurrentTime;
import com.patent.tools.HibernateUtil;
import com.patent.util.Constants;

public class MailInfoManagerImpl implements MailInfoManager{

	MailInfoDao mDao = null;
	Transaction tran = null;
	@Override
	public Integer addMail(String mailType, String sendInfo,
			Integer acceptUserId, String userType, String mailTitle,
			String mailContent) throws WEBException {
		// TODO Auto-generated method stub
		try {
			mDao = (MailInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_MAIL_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			MailInfoTb mail = new MailInfoTb(mailType, sendInfo, acceptUserId,userType, mailTitle, mailContent,
					CurrentTime.getCurrentTime(), 0);
			mDao.save(sess, mail);
			tran.commit();
			return mail.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("增加发送邮件时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateReadStatusById(Integer mailId, Integer readStatus)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			mDao = (MailInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_MAIL_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			MailInfoTb mail = mDao.get(sess, mailId);
			if(mail != null){
				mail.setReadStatus(readStatus);
				mDao.update(sess, mail);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("修改邮件的已读状态时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean delMailById(Integer mailId) throws WEBException {
		// TODO Auto-generated method stub
		try {
			mDao = (MailInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_MAIL_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			mDao.delete(sess, mailId);
			tran.commit();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("删除指定邮件时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public void delBatchSelfMail(Integer acceptUserId, String mailIdStr)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			mDao = (MailInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_MAIL_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			String[] mailIdArr = mailIdStr.split(",");
			Integer mailIdLen = mailIdArr.length;
			if(mailIdLen > 1){
				for(Integer i = 0 ; i < mailIdLen ; i++){
					mDao.delete(sess, Integer.parseInt(mailIdArr[i]));
					if(i % 10 == 0){
						sess.flush();
						sess.clear();
						tran.commit();
						tran = sess.beginTransaction();
					}
				}
				tran.commit();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("批量删除指定邮件时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<MailInfoTb> listPageInfoByOpt(Integer acceptUserId,
			String userType, String mailType, String mailTitle,
			Integer readStatus, Integer pageNo, Integer pageSize)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			mDao = (MailInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_MAIL_INFO);
			Session sess = HibernateUtil.currentSession();
			return mDao.findPageInfoByOpt(sess, acceptUserId, userType, mailType, mailTitle, readStatus, pageNo, pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据条件分页获取邮件列表时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public Integer getCountByOpt(Integer acceptUserId, String userType,
			String mailType, String mailTitle, Integer readStatus)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			mDao = (MailInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_MAIL_INFO);
			Session sess = HibernateUtil.currentSession();
			return mDao.getCountByOpt(sess, acceptUserId, userType, mailType, mailTitle, readStatus);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据条件获取邮件记录条数时出现异常");
		} finally{
			HibernateUtil.closeSession();
		}
	}

}
