package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.ZlajTzsInfoDao;
import com.patent.module.ZlajTzsInfoTb;

@SuppressWarnings("unchecked")
public class ZlajTzsInfoDaoImpl implements ZlajTzsInfoDao{

	@Override
	public ZlajTzsInfoTb get(Session sess, int id) {
		// TODO Auto-generated method stub
		return (ZlajTzsInfoTb) sess.load(ZlajTzsInfoTb.class, id);
	}

	@Override
	public void save(Session sess, ZlajTzsInfoTb tzsInfo) {
		// TODO Auto-generated method stub
		sess.save(tzsInfo);
	}

	@Override
	public void delete(Session sess, int id) {
		// TODO Auto-generated method stub
		sess.delete(this.get(sess, id));
	}

	@Override
	public void update(Session sess, ZlajTzsInfoTb tzsInfo) {
		// TODO Auto-generated method stub
		sess.update(tzsInfo);
	}

	@Override
	public ZlajTzsInfoTb getEntityById(Session sess, Integer id) {
		// TODO Auto-generated method stub
		String hql = " from ZlajTzsInfoTb as tzs where tzs.id = "+id;
		List<ZlajTzsInfoTb> tzsList = sess.createQuery(hql).list();
		if(tzsList.size() > 0){
			return tzsList.get(0);
		}
		return null;
	}

	@Override
	public List<ZlajTzsInfoTb> findInfoByAjId(Session sess, Integer ajId) {
		// TODO Auto-generated method stub
		String hql = " from ZlajTzsInfoTb as tzs where tzs.zlajMainInfoTb.id = "+ajId;
		return sess.createQuery(hql).list();
	}

	@Override
	public List<ZlajTzsInfoTb> findInfoByOpt(Session sess, Integer ajId,
			String fwSerial) {
		// TODO Auto-generated method stub
		String hql = " from ZlajTzsInfoTb as tzs where tzs.zlajMainInfoTb.id = "+ajId + " and tzs.fwSerial = '"+fwSerial+"'";
		return sess.createQuery(hql).list();
	}

}
