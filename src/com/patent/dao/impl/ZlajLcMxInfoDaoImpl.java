package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.ZlajLcMxInfoDao;
import com.patent.module.ZlajLcMxInfoTb;

@SuppressWarnings("unchecked")
public class ZlajLcMxInfoDaoImpl implements ZlajLcMxInfoDao{

	@Override
	public ZlajLcMxInfoTb get(Session sess, int id) {
		// TODO Auto-generated method stub
		return (ZlajLcMxInfoTb) sess.load(ZlajLcMxInfoTb.class, id);
	}

	@Override
	public void save(Session sess, ZlajLcMxInfoTb lcMxInfo) {
		// TODO Auto-generated method stub
		sess.save(lcMxInfo);
	}

	@Override
	public void delete(Session sess, int id) {
		// TODO Auto-generated method stub
		sess.delete(this.get(sess, id));
	}

	@Override
	public void update(Session sess, ZlajLcMxInfoTb lcMxInfo) {
		// TODO Auto-generated method stub
		sess.update(lcMxInfo);
	}

	@Override
	public List<ZlajLcMxInfoTb> findDetailInfoByLcId(Session sess, Integer lcId) {
		// TODO Auto-generated method stub
		String hql = " from ZlajLcMxInfoTb as lcmx where lcmx.zlajLcInfoTb.id = "+lcId + " order by lcmx.id desc";
		return sess.createQuery(hql).list();
	}

	@Override
	public List<ZlajLcMxInfoTb> findLastInfoByLcId(Session sess, Integer lcId) {
		// TODO Auto-generated method stub
		String hql = " from ZlajLcMxInfoTb as lcmx where lcmx.zlajLcInfoTb.id = "+lcId + " order by lcmx.id desc";
		return sess.createQuery(hql).setFirstResult(0).setMaxResults(1).list();
	}

	@Override
	public List<ZlajLcMxInfoTb> findFirstInfoByLcId(Session sess, Integer lcId) {
		// TODO Auto-generated method stub
		String hql = " from ZlajLcMxInfoTb as lcmx where lcmx.zlajLcInfoTb.id = "+lcId + " and lcmx.lcFzUserId = 0";
		return sess.createQuery(hql).setFirstResult(0).setMaxResults(1).list();
	}

	@Override
	public List<ZlajLcMxInfoTb> listUnComInfoByOpt(Session sess,
			String lcMxName, Double lcMxNo, Integer lcId) {
		// TODO Auto-generated method stub
		String hql = " from ZlajLcMxInfoTb as lcmx where lcmx.zlajLcInfoTb.id = "+lcId + " and lcmx.lcMxName = '"+lcMxName+"' and lcmx.lcMxNo = "+lcMxNo + " and lcmx.lcMxEDate = ''";
		return sess.createQuery(hql).list();
	}

	@Override
	public List<ZlajLcMxInfoTb> findDetailInfoById(Session sess, Integer mxId) {
		// TODO Auto-generated method stub
		String hql = " from ZlajLcMxInfoTb as lcmx where lcmx.id = "+mxId;
		return sess.createQuery(hql).list();
	}

	@Override
	public List<ZlajLcMxInfoTb> findSpecInfoInfoByOpt(Session sess,
			Integer zlId, String lcMxName) {
		// TODO Auto-generated method stub
		String hql = " from ZlajLcMxInfoTb as lcmx where lcmx.zlajLcInfoTb.zlajMainInfoTb.id = "+zlId + " and lcmx.lcMxName = '"+lcMxName+"'";
		return sess.createQuery(hql).list();
	}

}
