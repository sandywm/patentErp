package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.FeeImportDealRecordInfoDao;
import com.patent.module.FeeImportDealRecordInfo;

@SuppressWarnings("unchecked")
public class FeeImportDealRecordInfoDaoImpl implements FeeImportDealRecordInfoDao{

	@Override
	public FeeImportDealRecordInfo get(Session sess, int id) {
		// TODO Auto-generated method stub
		String hql = " from FeeImportDealRecordInfo as fidr where fidr.id = "+id;
		List<FeeImportDealRecordInfo> fidrList = sess.createQuery(hql).list();
		if(fidrList.size() > 0){
			return fidrList.get(0);
		}
		return null;
	}

	@Override
	public void save(Session sess, FeeImportDealRecordInfo fidrInfo) {
		// TODO Auto-generated method stub
		sess.save(fidrInfo);
	}

	@Override
	public List<FeeImportDealRecordInfo> findInfoByFirId(Session sess,Integer firId) {
		// TODO Auto-generated method stub
		String hql = " from FeeImportDealRecordInfo as fidr where fidr.feeImportRecordInfo.id = "+firId;
		return sess.createQuery(hql).list();
	}

}
