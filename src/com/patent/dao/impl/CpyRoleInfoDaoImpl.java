package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.CpyRoleInfoDao;
import com.patent.module.CpyRoleInfoTb;

@SuppressWarnings("unchecked")
public class CpyRoleInfoDaoImpl implements CpyRoleInfoDao{

	@Override
	public CpyRoleInfoTb get(Session sess, int id) {
		// TODO Auto-generated method stub
		return (CpyRoleInfoTb) sess.load(CpyRoleInfoTb.class, id);
	}

	@Override
	public void save(Session sess, CpyRoleInfoTb cpyRole) {
		// TODO Auto-generated method stub
		sess.save(cpyRole);
	}

	@Override
	public void delete(Session sess, CpyRoleInfoTb cpyRole) {
		// TODO Auto-generated method stub
		sess.delete(cpyRole);
	}

	@Override
	public void delete(Session sess, int id) {
		// TODO Auto-generated method stub
		sess.delete(this.get(sess, id));
	}

	@Override
	public void update(Session sess, CpyRoleInfoTb cpyRole) {
		// TODO Auto-generated method stub
		sess.update(cpyRole);
	}

	@Override
	public List<CpyRoleInfoTb> findInfoByCpyId(Session sess, Integer cpyId) {
		// TODO Auto-generated method stub
		String hql = " from CpyRoleInfoTb as cr where cr.cpyInfoTb.id = "+cpyId;
		return sess.createQuery(hql).list();
	}

}
