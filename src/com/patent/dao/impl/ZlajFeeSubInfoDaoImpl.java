package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.ZlajFeeSubInfoDao;
import com.patent.module.ZlajFeeSubInfoTb;

@SuppressWarnings("unchecked")
public class ZlajFeeSubInfoDaoImpl implements ZlajFeeSubInfoDao{

	@Override
	public ZlajFeeSubInfoTb get(Session sess, int id) {
		// TODO Auto-generated method stub
		String hql = " from ZlajFeeSubInfoTb as fs where fs.id = "+id;
		List<ZlajFeeSubInfoTb> fsList = sess.createQuery(hql).list();
		if(fsList.size() > 0){
			return fsList.get(0);
		}
		return null;
	}

	@Override
	public void save(Session sess, ZlajFeeSubInfoTb fs) {
		// TODO Auto-generated method stub
		sess.save(fs);
	}

	@Override
	public void delete(Session sess, ZlajFeeSubInfoTb fs) {
		// TODO Auto-generated method stub
		sess.delete(fs);
	}

	@Override
	public void delete(Session sess, int id) {
		// TODO Auto-generated method stub
		sess.delete(this.get(sess, id));
	}

	@Override
	public void update(Session sess, ZlajFeeSubInfoTb fs) {
		// TODO Auto-generated method stub
		sess.update(fs);
	}

	@Override
	public List<ZlajFeeSubInfoTb> findInfoByFeeId(Session sess, Integer feeId) {
		// TODO Auto-generated method stub
		String hql = " from ZlajFeeSubInfoTb as fs where fs.zlajFeeInfoTb.id = "+feeId;
		return sess.createQuery(hql).list();
	}

	@Override
	public List<ZlajFeeSubInfoTb> findInfoByFeeId(Session sess, Integer feeId,
			String currDate) {
		// TODO Auto-generated method stub
		String hql = " from ZlajFeeSubInfoTb as fs where fs.zlajFeeInfoTb.id = "+feeId;
		return sess.createQuery(hql).list();
	}

}
