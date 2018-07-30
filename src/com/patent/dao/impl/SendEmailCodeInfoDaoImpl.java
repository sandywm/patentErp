package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.SendEmailCodeInfoDao;
import com.patent.module.SendEmailCodeInfo;

@SuppressWarnings("unchecked")
public class SendEmailCodeInfoDaoImpl implements SendEmailCodeInfoDao{

	@Override
	public SendEmailCodeInfo get(Session sess, int id) {
		// TODO Auto-generated method stub
		return (SendEmailCodeInfo) sess.load(SendEmailCodeInfo.class, id);
	}

	@Override
	public void save(Session sess, SendEmailCodeInfo eCInfo) {
		// TODO Auto-generated method stub
		sess.save(eCInfo);
	}

	@Override
	public void delete(Session sess, SendEmailCodeInfo eCInfo) {
		// TODO Auto-generated method stub
		sess.delete(eCInfo);
	}

	@Override
	public void delete(Session sess, int id) {
		// TODO Auto-generated method stub
		sess.delete(this.get(sess, id));
	}

	@Override
	public void update(Session sess, SendEmailCodeInfo eCInfo) {
		// TODO Auto-generated method stub
		sess.update(eCInfo);
	}

	@Override
	public List<SendEmailCodeInfo> findSpecInfoByOpt(Session sess,
			String email, String code) {
		// TODO Auto-generated method stub
		String hql = " from SendEmailCodeInfo as sec where 1=1";
		if(!email.equals("")){
			hql += " and sec.userEmail = '"+email+"'";
		}
		if(!code.equals("")){
			hql += " and sec.code = '"+code+"'";
		}
		return sess.createQuery(hql).list();
	}

}
