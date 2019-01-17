package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.ZlajLcInfoDao;
import com.patent.module.ZlajLcInfoTb;

@SuppressWarnings("unchecked")
public class ZlajLcInfoDaoImpl implements ZlajLcInfoDao{

	@Override
	public ZlajLcInfoTb get(Session sess, int id) {
		// TODO Auto-generated method stub
		return (ZlajLcInfoTb) sess.load(ZlajLcInfoTb.class, id);
	}

	@Override
	public void save(Session sess, ZlajLcInfoTb lcInfo) {
		// TODO Auto-generated method stub
		sess.save(lcInfo);
	}

	@Override
	public void delete(Session sess, int id) {
		// TODO Auto-generated method stub
		sess.delete(this.get(sess, id));
	}

	@Override
	public void update(Session sess, ZlajLcInfoTb lcInfo) {
		// TODO Auto-generated method stub
		sess.update(lcInfo);
	}

	@Override
	public List<ZlajLcInfoTb> findInfo(Session sess, Integer ajId) {
		// TODO Auto-generated method stub
		String hql = " from ZlajLcInfoTb as lc where lc.zlajMainInfoTb.id = "+ajId + " order by lc.id desc";
		return sess.createQuery(hql).list();
	}

	@Override
	public List<ZlajLcInfoTb> findInfoById(Session sess, Integer id) {
		// TODO Auto-generated method stub
		String hql = " from ZlajLcInfoTb as lc where lc.id = "+id;
		return sess.createQuery(hql).list();
	}

	@Override
	public List<ZlajLcInfoTb> findInfoByLcMz(Session sess, Integer ajId, String lcMz) {
		// TODO Auto-generated method stub
		String hql = " from ZlajLcInfoTb as lc where lc.zlajMainInfoTb.id = "+ajId + " and lc.lcMz = '"+lcMz+"'";
		return sess.createQuery(hql).list();
	}

	@Override
	public List<ZlajLcInfoTb> findLastInfo(Session sess, Integer ajId) {
		// TODO Auto-generated method stub
		String hql = " from ZlajLcInfoTb as lc where lc.zlajMainInfoTb.id = "+ajId + " and lc.lcEDate = ''  order by lc.lcNo desc";
		return sess.createQuery(hql).setFirstResult(0).setMaxResults(1).list();
	}

	@Override
	public List<ZlajLcInfoTb> findInfoByZlId(Session sess, Integer zlId) {
		// TODO Auto-generated method stub
		String hql = " from ZlajLcInfoTb as lc where lc.zlajMainInfoTb.id = "+zlId + " order by lc.id desc";
		return sess.createQuery(hql).list();
	}

}
