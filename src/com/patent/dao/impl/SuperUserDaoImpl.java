package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.SuperUserDao;
import com.patent.module.SuperUser;

@SuppressWarnings("unchecked")
public class SuperUserDaoImpl implements SuperUserDao{

	@Override
	public SuperUser get(Session sess, int id) {
		// TODO Auto-generated method stub
		return (SuperUser) sess.load(SuperUser.class, id);
	}

	@Override
	public void save(Session sess, SuperUser su) {
		// TODO Auto-generated method stub
		sess.save(su);
	}

	@Override
	public void delete(Session sess, SuperUser su) {
		// TODO Auto-generated method stub
		sess.delete(su);
	}

	@Override
	public void delete(Session sess, int id) {
		// TODO Auto-generated method stub
		sess.delete(this.get(sess, id));
	}

	@Override
	public void update(Session sess, SuperUser su) {
		// TODO Auto-generated method stub
		sess.update(su);
	}

	@Override
	public List<SuperUser> findInfoByAccount(Session sess,String account) {
		// TODO Auto-generated method stub
		String hql = " from SuperUser as su where su.account = '"+account+"'";
		return sess.createQuery(hql).list();
	}

	@Override
	public List<SuperUser> findInfoById(Session sess, Integer userId) {
		// TODO Auto-generated method stub
		String hql = " from SuperUser as su";
		if(userId > 0){
			hql += " where su.id = "+userId;
		}
		return sess.createQuery(hql).list();
	}

}
