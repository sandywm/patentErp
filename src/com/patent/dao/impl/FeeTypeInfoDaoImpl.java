package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.FeeTypeInfoDao;
import com.patent.module.FeeTypeInfoTb;

@SuppressWarnings("unchecked")
public class FeeTypeInfoDaoImpl implements FeeTypeInfoDao{

	@Override
	public FeeTypeInfoTb getTypeEntityById(Session sess, int id) {
		// TODO Auto-generated method stub
		String hql = " from FeeTypeInfoTb as ft where ft.id = "+id;
		List<FeeTypeInfoTb> ftList = sess.createQuery(hql).list();
		if(ftList.size() > 0){
			return ftList.get(0);
		}
		return null;
	}

	@Override
	public List<FeeTypeInfoTb> findInfoByStatus(Session sess, String feeStatus) {
		// TODO Auto-generated method stub
		String hql = " from FeeTypeInfoTb as ft";
		if(!feeStatus.equals("")){
			hql += " and ft.feeStatus = '"+feeStatus+"'";
		}
		return sess.createQuery(hql).list();
	}

	@Override
	public List<FeeTypeInfoTb> findInfoByName(Session sess, String feeName) {
		// TODO Auto-generated method stub
		String hql = " from FeeTypeInfoTb as ft where ft.feeName = '"+feeName+"'";
		return sess.createQuery(hql).list();
	}

	@Override
	public List<FeeTypeInfoTb> findInfoByzlType(Session sess, String zlType) {
		// TODO Auto-generated method stub
		String hql = " from FeeTypeInfoTb as ft where FIND_IN_SET("+zlType+",ft.feeRange) > 0";
		return sess.createQuery(hql).list();
	}

}
