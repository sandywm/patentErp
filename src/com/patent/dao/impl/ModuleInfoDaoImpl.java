package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.ModuleInfoDao;
import com.patent.module.ModuleInfoTb;

@SuppressWarnings("unchecked")
public class ModuleInfoDaoImpl implements ModuleInfoDao{

	@Override
	public ModuleInfoTb get(Session sess, int id) {
		// TODO Auto-generated method stub
		return (ModuleInfoTb) sess.load( ModuleInfoTb.class, id);
	}

	@Override
	public void save(Session sess, ModuleInfoTb mInfo) {
		// TODO Auto-generated method stub
		sess.save(mInfo);
	}

	@Override
	public void delete(Session sess, ModuleInfoTb mInfo) {
		// TODO Auto-generated method stub
		sess.delete(mInfo);
	}

	@Override
	public void delete(Session sess, int id) {
		// TODO Auto-generated method stub
		sess.delete(this.get(sess, id));
	}

	@Override
	public void update(Session sess, ModuleInfoTb mInfo) {
		// TODO Auto-generated method stub
		sess.update(mInfo);
	}

	@Override
	public List<ModuleInfoTb> findAllInfo(Session sess) {
		// TODO Auto-generated method stub
		String hql = " from ModuleInfoTb";
		return sess.createQuery(hql).list();
	}

}
