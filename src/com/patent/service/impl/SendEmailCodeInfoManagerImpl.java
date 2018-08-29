package com.patent.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.patent.dao.SendEmailCodeInfoDao;
import com.patent.exception.WEBException;
import com.patent.factory.DaoFactory;
import com.patent.module.SendEmailCodeInfo;
import com.patent.service.SendEmailCodeInfoManager;
import com.patent.tools.HibernateUtil;
import com.patent.util.Constants;

public class SendEmailCodeInfoManagerImpl implements SendEmailCodeInfoManager{

	SendEmailCodeInfoDao secDao = null;
	Transaction tran = null;
	@Override
	public Integer addSEC(String userEmail, String code, String sendTime)
			throws WEBException {
		// TODO Auto-generated method stub
		try {
			secDao = (SendEmailCodeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_SEND_MAIL_CODE_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			SendEmailCodeInfo sec = new SendEmailCodeInfo(userEmail,code,sendTime,0);
			secDao.save(sess, sec);
			tran.commit();
			return sec.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("增加发送邮件验证码时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public boolean updateInfoById(Integer id, String code, String sendTime,
			Integer useStatus) throws WEBException {
		// TODO Auto-generated method stub
		try {
			secDao = (SendEmailCodeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_SEND_MAIL_CODE_INFO);
			Session sess = HibernateUtil.currentSession();
			tran = sess.beginTransaction();
			SendEmailCodeInfo sec = secDao.get(sess, id);
			if(sec != null){
				if(!code.equals("")){
					sec.setCode(code);
				}
				if(sendTime != null){
					sec.setSendTime(sendTime);
				}
				if(useStatus.equals(0) || useStatus.equals(1)){
					sec.setUseStatus(useStatus);
				}
				secDao.update(sess, sec);
				tran.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据主键修改发送邮件验证码信息时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

	@Override
	public List<SendEmailCodeInfo> listSpecInfoByOpt(String userEmail,
			String code) throws WEBException {
		// TODO Auto-generated method stub
		try {
			secDao = (SendEmailCodeInfoDao) DaoFactory.instance(null).getDao(Constants.DAO_SEND_MAIL_CODE_INFO);
			Session sess = HibernateUtil.currentSession();
			return secDao.findSpecInfoByOpt(sess, userEmail, code);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WEBException("根据条件获取发送邮件验证码时出现异常!");
		} finally{
			HibernateUtil.closeSession();
		}
	}

}
