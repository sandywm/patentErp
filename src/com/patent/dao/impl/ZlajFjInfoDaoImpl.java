package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.ZlajFjInfoDao;
import com.patent.module.ZlajFjInfoTb;

@SuppressWarnings("unchecked")
public class ZlajFjInfoDaoImpl implements ZlajFjInfoDao{

	@Override
	public ZlajFjInfoTb get(Session sess, int id) {
		// TODO Auto-generated method stub
		return (ZlajFjInfoTb) sess.load(ZlajFjInfoTb.class, id);
	}

	@Override
	public void save(Session sess, ZlajFjInfoTb fjInfo) {
		// TODO Auto-generated method stub
		sess.save(fjInfo);
	}

	@Override
	public void delete(Session sess, int id) {
		// TODO Auto-generated method stub
		sess.delete(this.get(sess, id));
	}

	@Override
	public void update(Session sess, ZlajFjInfoTb fjInfo) {
		// TODO Auto-generated method stub
		sess.update(fjInfo);
	}

	@Override
	public ZlajFjInfoTb getEntityById(Session sess, Integer id) {
		// TODO Auto-generated method stub
		String hql = " from ZlajFjInfoTb as fj where fj.id = "+id;
		List<ZlajFjInfoTb> fjList = sess.createQuery(hql).list();
		if(fjList.size() > 0){
			return fjList.get(0);
		}
		return null;
	}

	@Override
	public List<ZlajFjInfoTb> findInfoByAjId(Session sess, Integer ajId) {
		// TODO Auto-generated method stub
		String hql = " from ZlajFjInfoTb as fj where fj.zlajMainInfoTb.id = "+ajId + " order by fj.id desc";
		return sess.createQuery(hql).list();
	}

	@Override
	public List<ZlajFjInfoTb> findLastInfoByAjId(Session sess, Integer ajId) {
		// TODO Auto-generated method stub
		String hql = " from ZlajFjInfoTb as fj where fj.zlajMainInfoTb.id = "+ajId + " order by fj.id desc";
		return sess.createQuery(hql).setFirstResult(0).setMaxResults(1).list();
	}

}
