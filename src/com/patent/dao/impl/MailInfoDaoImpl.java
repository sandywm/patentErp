package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.MailInfoDao;
import com.patent.module.MailInfoTb;
import com.patent.tools.CommonTools;

@SuppressWarnings("unchecked")
public class MailInfoDaoImpl implements MailInfoDao{

	@Override
	public MailInfoTb get(Session sess, int id) {
		// TODO Auto-generated method stub
		return (MailInfoTb) sess.load(MailInfoTb.class, id);
	}

	@Override
	public void save(Session sess, MailInfoTb mail) {
		// TODO Auto-generated method stub
		sess.save(mail);
	}

	@Override
	public void delete(Session sess, MailInfoTb mail) {
		// TODO Auto-generated method stub
		sess.delete(mail);
	}

	@Override
	public void delete(Session sess, int id) {
		// TODO Auto-generated method stub
		sess.delete(this.get(sess, id));
	}

	@Override
	public void update(Session sess, MailInfoTb mail) {
		// TODO Auto-generated method stub
		sess.update(mail);
	}

	@Override
	public List<MailInfoTb> findPageInfoByOpt(Session sess,
			Integer acceptUserId, String userType, String mailType,
			String mailTitle, Integer readStatus, Integer pageNo,
			Integer pageSize) {
		// TODO Auto-generated method stub
		String hql = " from MailInfoTb as m where m.acceptUserId = "+acceptUserId + " and m.userType = '"+userType+"'";
		if(!mailType.equals("")){
			hql += " and m.mailType = '"+mailType+"'";
		}
		if(!mailTitle.equals("")){
			hql += " and m.mailTitle like '%"+mailTitle+"%'";
		}
		if(!readStatus.equals(-1)){
			hql += " and m.readStatus = "+readStatus;
		}
		hql += " order by m.id desc";
		int offset = (pageNo - 1) * pageSize;
		if (offset < 0) {
			offset = 0;
		}
		return sess.createQuery(hql).setFirstResult(offset).setMaxResults(pageSize).list();
	}

	@Override
	public Integer getCountByOpt(Session sess, Integer acceptUserId,
			String userType, String mailType, String mailTitle,
			Integer readStatus) {
		// TODO Auto-generated method stub
		String hql = "select count(m.id) from MailInfoTb as m where m.acceptUserId = "+acceptUserId + " and m.userType = '"+userType+"'";
		if(!mailType.equals("")){
			hql += " and m.mailType = '"+mailType+"'";
		}
		if(!mailTitle.equals("")){
			hql += " and m.mailTitle like '%"+mailTitle+"%'";
		}
		if(!readStatus.equals(-1)){
			hql += " and m.readStatus = "+readStatus;
		}
		Object count_obj = sess.createQuery(hql).uniqueResult();
		return CommonTools.longToInt(count_obj);
	}

	@Override
	public List<MailInfoTb> findInfoByOpt(Session sess, Integer acceptUserId, Integer mailId) {
		// TODO Auto-generated method stub
		String hql = " from MailInfoTb as m where m.id = "+mailId+" and  m.acceptUserId = "+acceptUserId;
		return sess.createQuery(hql).list();
	}

	@Override
	public List<MailInfoTb> findInfoByZlId(Session sess, Integer zlId) {
		// TODO Auto-generated method stub
		String hql = " from MailInfoTb as m where m.zlId = "+zlId;
		return sess.createQuery(hql).list();
	}

}
