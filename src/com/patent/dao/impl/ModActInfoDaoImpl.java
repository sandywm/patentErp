package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.ModActInfoDao;
import com.patent.module.ModActInfoTb;

@SuppressWarnings("unchecked")
public class ModActInfoDaoImpl implements ModActInfoDao{

	@Override
	public ModActInfoTb get(Session sess, int id) {
		// TODO Auto-generated method stub
		return (ModActInfoTb) sess.load(ModActInfoTb.class, id);
	}

	@Override
	public void save(Session sess, ModActInfoTb maInfo) {
		// TODO Auto-generated method stub
		sess.save(maInfo);
	}

	@Override
	public void delete(Session sess, ModActInfoTb maInfo) {
		// TODO Auto-generated method stub
		sess.delete(maInfo);
	}

	@Override
	public void delete(Session sess, int id) {
		// TODO Auto-generated method stub
		sess.delete(this.get(sess, id));
	}

	@Override
	public void update(Session sess, ModActInfoTb maInfo) {
		// TODO Auto-generated method stub
		sess.update(maInfo);
	}

	@Override
	public List<ModActInfoTb> findSpecInfo(Session sess, Integer modId) {
		// TODO Auto-generated method stub
		String hql = " from ModActInfoTb as ma where ma.moduleInfoTb.id = "+modId + " order by ma.orderNo asc";
		return sess.createQuery(hql).list();
	}

}
