package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.ZlajQrhDao;
import com.patent.module.ZlajQrh;
import com.patent.tools.CommonTools;

@SuppressWarnings("unchecked")
public class ZlajQrhDaoImpl implements ZlajQrhDao{

	@Override
	public ZlajQrh get(Session sess, int id) {
		// TODO Auto-generated method stub
		return (ZlajQrh) sess.load(ZlajQrh.class, id);
	}

	@Override
	public void save(Session sess, ZlajQrh qrh) {
		// TODO Auto-generated method stub
		sess.save(qrh);
	}

	@Override
	public Integer getCount(Session sess, Integer cpyId) {
		// TODO Auto-generated method stub
		String hql = "select count(qrh.id) from ZlajQrh as qrh where qrh.cpyId = "+cpyId;
		Object count_obj = sess.createQuery(hql).uniqueResult();
		return CommonTools.longToInt(count_obj);
	}

	@Override
	public List<ZlajQrh> findPageInfo(Session sess, Integer cpyId,
			Integer pageNo, Integer pageSize) {
		// TODO Auto-generated method stub
		String hql = " from ZlajQrh as qrh where qrh.cpyId = "+cpyId;
		int offset = (pageNo - 1) * pageSize;
		if (offset < 0) {
			offset = 0;
		}
		return sess.createQuery(hql).setFirstResult(offset).setMaxResults(pageSize).list();
	}

}
