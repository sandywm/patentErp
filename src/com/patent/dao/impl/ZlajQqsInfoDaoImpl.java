package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.ZlajQqsInfoDao;
import com.patent.module.ZlajQqsInfoTb;

public class ZlajQqsInfoDaoImpl implements ZlajQqsInfoDao{

	@Override
	public ZlajQqsInfoTb get(Session sess, int id) {
		// TODO Auto-generated method stub
		return (ZlajQqsInfoTb) sess.load(ZlajQqsInfoTb.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ZlajQqsInfoTb> findInfoByTypeEng(Session sess, String typeEng) {
		// TODO Auto-generated method stub
		String hql = " from ZlajQqsInfoTb as qqs where qqs.zlTypeEng = '"+typeEng+"'";
		return sess.createQuery(hql).list();
	}

}
