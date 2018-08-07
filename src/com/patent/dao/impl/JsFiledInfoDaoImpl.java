package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.JsFiledInfoDao;
import com.patent.module.JsFiledInfoTb;

@SuppressWarnings("unchecked")
public class JsFiledInfoDaoImpl implements JsFiledInfoDao{

	@Override
	public JsFiledInfoTb get(Session sess, int id) {
		// TODO Auto-generated method stub
		return (JsFiledInfoTb) sess.load(JsFiledInfoTb.class, id);
	}

	@Override
	public void save(Session sess, JsFiledInfoTb jsInfo) {
		// TODO Auto-generated method stub
		sess.save(jsInfo);
	}

	@Override
	public void delete(Session sess, JsFiledInfoTb jsInfo) {
		// TODO Auto-generated method stub
		sess.delete(jsInfo);
	}

	@Override
	public void delete(Session sess, int id) {
		// TODO Auto-generated method stub
		sess.delete(this.get(sess, id));
	}

	@Override
	public List<JsFiledInfoTb> findAllInfo(Session sess,Integer cpyId) {
		// TODO Auto-generated method stub
		String hql = " from JsFiledInfoTb as js where js.cpyInfoTb.id = "+cpyId;
		return sess.createQuery(hql).list();
	}

}
