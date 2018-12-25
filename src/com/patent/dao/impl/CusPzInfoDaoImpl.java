package com.patent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.patent.dao.CusPzInfoDao;
import com.patent.module.CusPzInfo;

public class CusPzInfoDaoImpl implements CusPzInfoDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<CusPzInfo> findInfoByOpt(Session sess, Integer backFeeId,
			Integer cusId) {
		// TODO Auto-generated method stub
		String hql = " from CusPzInfo as cp where 1 = 1";
		if(backFeeId > 0){
			hql += " and cp.cusBackFeeInfo.id = "+backFeeId;
		}
		if(cusId > 0){
			hql += " and cp.cusBackFeeInfo.cus.id = "+cusId;
		}
		return sess.createQuery(hql).list();
	}

	@Override
	public void save(Session sess, CusPzInfo pzInfo) {
		// TODO Auto-generated method stub
		sess.save(pzInfo);
	}
}
